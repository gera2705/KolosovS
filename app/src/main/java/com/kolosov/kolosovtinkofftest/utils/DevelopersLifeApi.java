package com.kolosov.kolosovtinkofftest.utils;

import com.kolosov.kolosovtinkofftest.response.AllGifsResponse;
import com.kolosov.kolosovtinkofftest.response.RandomGifResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface DevelopersLifeApi {

    @GET("https://developerslife.ru/random?json=true")
    Call<RandomGifResponse> getRandomGif();

    @GET("/{category}/{pageNumber}?json=true")
    Call<AllGifsResponse> getLatestGif(@Path("category") String category , @Path("pageNumber") int pageNumber);
}
