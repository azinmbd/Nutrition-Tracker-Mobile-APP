package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface NutritionixApi {
    @GET("v2/search/instant/")
    Call<NutritionResponse> getNutritionInfo(
            @Header("x-app-id") String appId,
            @Header("x-app-key") String appKey,
            @Query("query") String query
    );
}

