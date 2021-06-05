package com.kolosov.kolosovtinkofftest.request;

import com.kolosov.kolosovtinkofftest.utils.Credentials;
import com.kolosov.kolosovtinkofftest.utils.DevelopersLifeApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static NetworkService mInstance;
    private Retrofit mRetrofit;

    private NetworkService(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Credentials.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance(){
        if(mInstance == null){
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public DevelopersLifeApi getDevelopersLifeApi(){
        return mRetrofit.create(DevelopersLifeApi.class);
    }

}
