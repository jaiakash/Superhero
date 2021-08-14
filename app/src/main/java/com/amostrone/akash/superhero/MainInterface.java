package com.amostrone.akash.superhero;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface MainInterface {
    @GET("api/all.json")
    Call<String> STRING_CALL(
            @Query("page") int page,
            @Query("limit") int limit
    );
}
