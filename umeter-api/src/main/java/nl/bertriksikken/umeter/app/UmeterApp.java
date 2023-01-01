package nl.bertriksikken.umeter.app;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import nl.bertriksikken.oauth2.AuthClient;
import nl.bertriksikken.oauth2.AuthException;
import nl.bertriksikken.umeter.api.CustomerData;
import nl.bertriksikken.umeter.api.MeteringPointClient;
import nl.bertriksikken.umeter.api.P4Data;
import nl.bertriksikken.umeter.api.UmeterClient;
import nl.bertriksikken.umeter.auth.AuthService;

public final class UmeterApp {

    private static final Logger LOG = LoggerFactory.getLogger(UmeterApp.class);

    public static void main(String[] args) throws IOException {
        PropertyConfigurator.configure("log4j.properties");
        LOG.info("Starting UmeterApp");
        UmeterApp app = new UmeterApp();
        try {
            app.run("umeter.yaml");
        } catch (AuthException e) {
            LOG.warn("Authentication error", e);
        }
    }

    private void run(String configFileName) throws IOException, AuthException {
        // read config file
        UmeterAppConfig config = getConfig(configFileName);

        // create auth service
        AuthClient authClient = AuthClient.create(config.authConfig);
        AuthService authService = new AuthService(authClient, config.user, config.pass);

        // get customer EAN, this steps initiates authentication
        UmeterClient umeterClient = UmeterClient.create(config.umeterApiConfig, authService);
        CustomerData customerData = umeterClient.getCustomer();
        String ean = customerData.addresses.get(0).eEan.ean;
        LOG.info("EAN={}", ean);

        // get status of data requests
        MeteringPointClient meteringPointClient = MeteringPointClient.create(config.meteringPointConfig);
        String mpResponse = meteringPointClient.getLastDayRequests(authService.getBearerToken(), ean, 2);
        LOG.info("Metering point: {}", mpResponse);

        // get meter readings from past month
        ZonedDateTime today = ZonedDateTime.now(ZoneId.systemDefault()).truncatedTo(ChronoUnit.DAYS);
        ZonedDateTime monthStart = today.minusMonths(1).withDayOfMonth(1);
        ZonedDateTime monthEnd = monthStart.plusMonths(1);
        P4Data p4data = umeterClient.getP4Data(ean, monthStart, monthEnd);
        LOG.info("R181={}, R182={}", p4data.beginReadings.r181, p4data.beginReadings.r182);
    }

    private UmeterAppConfig getConfig(String fileName) throws IOException {
        YAMLMapper mapper = new YAMLMapper();
        File configFile = new File(fileName);
        if (configFile.exists()) {
            return mapper.readValue(new File(fileName), UmeterAppConfig.class);
        } else {
            UmeterAppConfig config = new UmeterAppConfig();
            mapper.writeValue(configFile, config);
            return config;
        }
    }

}
