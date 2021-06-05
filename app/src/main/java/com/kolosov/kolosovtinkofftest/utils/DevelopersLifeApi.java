package com.kolosov.kolosovtinkofftest.utils;

import com.kolosov.kolosovtinkofftest.response.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DevelopersLifeApi {

    @GET
    Call<Response> response();

    @GET("https://developerslife.ru/random?json=true")
    Call<Response> getGson();
}
