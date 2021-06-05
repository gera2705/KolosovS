package com.kolosov.kolosovtinkofftest;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kolosov.kolosovtinkofftest.request.NetworkService;
//import com.kolosov.kolosovtinkofftest.request.Service;
import com.kolosov.kolosovtinkofftest.response.Response;
import com.kolosov.kolosovtinkofftest.utils.DevelopersLifeApi;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    private ImageButton addButton;
    private ImageView imageView;
    private TextView textView;
    private String URL;
    private String description;


    private ImageButton button3;
    private static int count;

    private ArrayList<String> urls = new ArrayList<>();
    private ArrayList<String> descriptions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        addButton = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);


        button3 = findViewById(R.id.button3);
        count = 1;

//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("array" , String.valueOf(urls.size()));
//            }
//        });

        getRetrofitResponse();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count == urls.size() ) {
                    getRetrofitResponse();
                    //count++;
                 }else {
                    putGif(urls.get(count) , descriptions.get(count));
                }
                count++;
                //getRetrofitResponse();
                Log.d("count2" , String.valueOf(count));


            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count - 1 == 0){
                    Toast.makeText(MainActivity.this, "Нету", Toast.LENGTH_SHORT).show();
                }else {

                    putGif(urls.get(count-2) , descriptions.get(count-2));
                    count--;
                }
                Log.d("count2" , String.valueOf(count));


            }
        });



    }
    private void getRetrofitResponse() {

        NetworkService.getInstance()
                .getDevelopersLifeApi()
                .getGson()
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                        description = response.body().getDescription();
                        try {
                             URL = response.body().getGifURL().replaceFirst("http", "https");
                             urls.add(URL);
                             descriptions.add(description);

//
                        }catch (Exception e){
                            e.printStackTrace();
                        }
//
                        //textView.setText(description);

                        putGif(URL , description);

                        Log.d("TIME" , "3");

                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {

                    }
                });

    }

    private void putGif(String URL , String description){

        textView.setText(description);
        Glide.with(MainActivity.this)
                .asGif()
                .load(URL)
                .placeholder(R.drawable.load_gif)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView);
    }

}