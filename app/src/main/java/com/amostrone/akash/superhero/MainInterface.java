package com.amostrone.akash.superhero;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface MainInterface {
    @Headers("x-api-key: 546ece19-7a66-4951-afe7-8da3695e19ff")
    @GET("v1/breeds")
    Call<String> STRING_CALL(
            @Query("page") int page,
            @Query("limit") int limit
    );
}
