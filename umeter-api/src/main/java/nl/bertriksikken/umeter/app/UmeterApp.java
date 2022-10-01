package nl.bertriksikken.umeter.app;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import nl.bertriksikken.oauth2.AuthApi;
import nl.bertriksikken.umeter.api.CustomerData;
import nl.bertriksikken.umeter.api.UmeterApi;
import nl.bertriksikken.umeter.auth.AuthService;

public final class UmeterApp {

    private static final Logger LOG = LoggerFactory.getLogger(UmeterApp.class);

    public static void main(String[] args) throws IOException {
        PropertyConfigurator.configure("log4j.properties");
        LOG.info("Starting UmeterApp");
        UmeterApp app = new UmeterApp();
        app.run("umeter.yaml");
    }

    private void run(String configFileName) throws IOException {
        // read config file
        UmeterAppConfig config = getConfig(configFileName);

        // get auth token
        AuthApi authApi = AuthApi.create(config.authConfig);
        AuthService authService = new AuthService(authApi);
        String authToken = authService.getToken(config.user, config.pass);

        UmeterApi umeterApi = UmeterApi.create(config.umeterApiConfig);
        CustomerData customerData = umeterApi.getCustomer(authToken);
        LOG.info("EAN={}", customerData.addresses.get(0).eEan.ean);
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
