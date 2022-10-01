package nl.bertriksikken.umeter.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IUmeterApi {

    @GET("/api/customer")
    public Call<CustomerData> getCustomer(@Header("Authorization") String authorization);

    @GET("/api/P4/{ean}/{from}/{to}")
    public Call<P4Data> getP4Data(@Header("Authorization") String authorization, @Path("ean") String ean,
            @Path("from") String from, @Path("to") String to, @Query("explain") boolean explain);

}
