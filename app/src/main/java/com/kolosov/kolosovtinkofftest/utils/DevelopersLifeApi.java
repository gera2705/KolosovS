package com.kolosov.kolosovtinkofftest.utils;

import com.kolosov.kolosovtinkofftest.response.Response;

import retrofit2.Call;
import retrofit2.http.GET;


public interface DevelopersLifeApi {

    @GET("https://developerslife.ru/random?json=true")
    Call<Response> getRandomGif();
}
