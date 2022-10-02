package nl.bertriksikken.umeter.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface IMeteringPointApi {

    @GET("/api/MP/LastDayRequests")
    public Call<String> getLastDayRequests(@Header("Authorization") String authToken, @Query("eanId") String ean,
            @Query("number") int number);

}
