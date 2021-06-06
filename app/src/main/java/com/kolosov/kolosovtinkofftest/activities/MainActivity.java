package com.kolosov.kolosovtinkofftest.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kolosov.kolosovtinkofftest.R;
import com.kolosov.kolosovtinkofftest.models.DataModel;
import com.kolosov.kolosovtinkofftest.request.NetworkService;
import com.kolosov.kolosovtinkofftest.response.RandomGifResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    private ImageButton forwardButton;
    private ImageButton backButton;
    private Button rebootButton;

    private BottomNavigationView bottomNavigationView;

    private ImageView gifImageView;
    private TextView descriptionTextView;

    private String URL;
    private String description;

    private static int positionCount;

    private ArrayList<String> urls ;
    private ArrayList<String> descriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        loadMenu();
        getRandomGifResponse();

    }

    private void init(){

        forwardButton = findViewById(R.id.button);
        gifImageView = findViewById(R.id.imageView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        backButton = findViewById(R.id.button3);
        rebootButton = findViewById(R.id.reboot);

        bottomNavigationView = findViewById(R.id.navigation);

        rebootButton.setVisibility(View.INVISIBLE);
        rebootButton.setEnabled(false);

        urls = new ArrayList<>();
        descriptions = new ArrayList<>();
        positionCount = 1;

        forwardButton.setOnClickListener(v -> {
            if(positionCount == urls.size() ) {
                getRandomGifResponse();
            }else {
                putGif(urls.get(positionCount) , descriptions.get(positionCount));
            }
            positionCount++;

        });

        backButton.setOnClickListener(v -> {
            if(positionCount - 1 == 0){
                Toast.makeText(MainActivity.this, "Гифки сзади закончились :(", Toast.LENGTH_SHORT).show();
            }else {
                putGif(urls.get(positionCount -2) , descriptions.get(positionCount -2));
                positionCount--;
            }

        });

        rebootButton.setOnClickListener(v -> {
            getRandomGifResponse();
            forwardButton.setEnabled(true);
            backButton.setEnabled(true);
            rebootButton.setVisibility(View.INVISIBLE);
            rebootButton.setEnabled(false);
        });
    }


    private void getRandomGifResponse() {

            NetworkService.getInstance()
                    .getDevelopersLifeApi()
                    .getRandomGif()
                    .enqueue(new Callback<RandomGifResponse>() {
                        @Override
                        public void onResponse(Call<RandomGifResponse> call, retrofit2.Response<RandomGifResponse> response) {

                            try {
                                DataModel dataModel = response.body().getDataModel();
                                URL = dataModel.getGifURL().replaceFirst("http", "https");
                                description = dataModel.getDescription();
                                urls.add(URL);
                                descriptions.add(description);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            putGif(URL, description);

                        }

                        @Override
                        public void onFailure(Call<RandomGifResponse> call, Throwable t) {
                            forwardButton.setEnabled(false);
                            backButton.setEnabled(false);
                            gifImageView.setImageResource(R.drawable.ic_error_image);
                            descriptionTextView.setText("Нет подключения к интернету!");
                            rebootButton.setVisibility(View.VISIBLE);
                            rebootButton.setEnabled(true);

                        }
                    });


    }

    private void putGif(String URL , String description){

        descriptionTextView.setText(description);

        Glide.with(MainActivity.this)
                .asGif()
                .load(URL)
                .placeholder(R.drawable.progress_bar)
                .error(R.drawable.ic_error_image)
                .fallback(R.drawable.ic_error_image)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(gifImageView);
    }


    void loadMenu(){

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){

                case R.id.latest:
                    Intent intent = new Intent(MainActivity.this , LatestActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0 , 0);
                    this.finish();
                    break;


                case R.id.top:
                    Intent intent1 = new Intent(MainActivity.this , TopActivity.class);
                    startActivity(intent1);
                    overridePendingTransition(0 , 0);
                    this.finish();
                    break;

                case R.id.hot:
                    Intent intent2 = new Intent(MainActivity.this , HotActivity.class);
                    startActivity(intent2);
                    overridePendingTransition(0 , 0);
                    this.finish();
                    break;
            }

            return false;
        });
    }
}