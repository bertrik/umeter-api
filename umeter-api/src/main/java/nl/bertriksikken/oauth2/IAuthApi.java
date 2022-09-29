package nl.bertriksikken.oauth2;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IAuthApi {

    @FormUrlEncoded
    @POST("/token")
    public Call<AuthResponse> getToken(@FieldMap Map<String, String> fields);

}
