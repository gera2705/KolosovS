package com.kolosov.kolosovtinkofftest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.kolosov.kolosovtinkofftest.request.NetworkService;
//import com.kolosov.kolosovtinkofftest.request.Service;
import com.kolosov.kolosovtinkofftest.response.Response;
import com.kolosov.kolosovtinkofftest.utils.DevelopersLifeApi;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    private Button addButton;
    private ImageView imageView;
    private TextView textView;
    private String URL;
    private String desk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        addButton = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

//        Log.d("TIME" , "1");
        getRetrofitResponse();
//        Log.d("TIME" , "2");
//        textView.setText(desk);
//////            GetRetrofitResponse();
//         Glide.with(this)
//                    .asGif()
//                    .load(URL)
//                    .placeholder(R.drawable.load_gif)
//                    .centerCrop()
//                    .into(imageView);
//        Log.d("TIME" , "3");
//
      //  String url = "https://static.devli.ru/public/images/gifs/201309/4f35e384-7831-4da1-a857-d89196bf93e4.gif";
     //   String url = "https://miro.medium.com/max/3840/1*K2efK5T13AtQz4bZqXbQgQ.gif";
//
//
        addButton.setOnClickListener(v -> {

            Log.d("TIME" , "1");
            getRetrofitResponse();
            Log.d("TIME" , "2");

        });
    }
    private void getRetrofitResponse() {

        NetworkService.getInstance()
                .getDevelopersLifeApi()
                .getGson()
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                       // Log.v("Tag" , "OK" + response.body().toString());
                        desk = response.body().getDescription();
                        try {
                        URL = response.body().getGifURL().replaceFirst("http", "https");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        Log.d("TIME" , "1.5");
                        Log.v("Tag" , desk + " " + URL);
                        textView.setText(desk);
//            GetRetrofitResponse();
                        Glide.with(MainActivity.this)
                                .asGif()
                                .load(URL)
                                .placeholder(R.drawable.load_gif)
                                .fitCenter()
                                .into(imageView);
                        Log.d("TIME" , "3");
//                        try {
//                            URL = response.body().getGifURL().replaceFirst("http", "https");
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                        desk = response.body().getDescription();
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {

                    }
                });

    }

//    private void GetRetrofitResponse() {
//
//        DevelopersLifeApi developersLifeApi = Service.getDevelopersLifeApi();
//
//        Call<Response> responseCall = developersLifeApi.getGson();
//
//        responseCall.enqueue(new Callback<Response>() {
//            @Override
//            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
//                if(response.code() == 200){
//                    Log.v("Tag" , "OK" + response.body().toString());
//                    String d = response.body().getDescription();
//                    String u = response.body().getGifURL();
//                    Log.v("Tag" , d + " " + u);
//                }else {
//
//                        Log.v("Tag" , "Error");
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Response> call, Throwable t) {
//
//            }
//        });
//    }
}