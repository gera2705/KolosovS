package com.kolosov.kolosovtinkofftest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kolosov.kolosovtinkofftest.request.NetworkService;
//import com.kolosov.kolosovtinkofftest.request.Service;
import com.kolosov.kolosovtinkofftest.response.Response;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    private ImageButton forwardButton;
    private ImageButton backButton;

    private ImageView imageView;
    private TextView textView;

    private String URL;
    private String description;

    private static int count;

    private ArrayList<String> urls = new ArrayList<>();
    private ArrayList<String> descriptions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        forwardButton = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        backButton = findViewById(R.id.button3);
        count = 1;

        getRetrofitResponse();

        forwardButton.setOnClickListener(v -> {
            if(count == urls.size() ) {
                getRetrofitResponse();
             }else {
                putGif(urls.get(count) , descriptions.get(count));
            }
            count++;
            Log.d("count2" , String.valueOf(count));


        });

        backButton.setOnClickListener(v -> {
            if(count - 1 == 0){
                Toast.makeText(MainActivity.this, "Гифки в КЭШе закончились :(", Toast.LENGTH_SHORT).show();
            }else {

                putGif(urls.get(count-2) , descriptions.get(count-2));
                count--;
            }
            Log.d("count2" , String.valueOf(count));


        });


    }
    private void getRetrofitResponse() {

        NetworkService.getInstance()
                .getDevelopersLifeApi()
                .getRandomGif()
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                        description = response.body().getDescription();
                        try {
                             URL = response.body().getGifURL().replaceFirst("http", "https");
                             urls.add(URL);
                             descriptions.add(description);

                        }catch (Exception e){
                            e.printStackTrace();
                        }

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
                .placeholder(R.drawable.progress_bar)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView);
    }

}