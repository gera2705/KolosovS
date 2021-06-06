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
import com.kolosov.kolosovtinkofftest.response.AllGifsResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopActivity extends AppCompatActivity {

    private ImageButton forwardButton;
    private ImageButton backButton;
    private Button rebootButton;

    private BottomNavigationView bottomNavigationView;

    private ImageView gifImageView;
    private TextView descriptionTextView;

    private static int positionCount;
    private static int totalCount;

    private ArrayList<String> urls;
    private ArrayList<String> descriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        getAllGifsResponse(rnd(2368));

        loadMenu();

    }

    private void init() {
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
            if (positionCount == 5) {
                positionCount = 1;
                urls.clear();
                descriptions.clear();
                getAllGifsResponse(rnd(2368));
            } else {
                putGif(urls.get(positionCount), descriptions.get(positionCount));
                positionCount++;
            }

        });

        backButton.setOnClickListener(v -> {
            if (positionCount - 1 == 0) {
                Toast.makeText(TopActivity.this, "Гифки сзади закончились :(", Toast.LENGTH_SHORT).show();
            } else {

                putGif(urls.get(positionCount - 2), descriptions.get(positionCount - 2));
                positionCount--;
            }

        });

        rebootButton.setOnClickListener(v -> {
            getAllGifsResponse(rnd(2368));
            forwardButton.setEnabled(true);
            backButton.setEnabled(true);
            rebootButton.setVisibility(View.INVISIBLE);
            rebootButton.setEnabled(false);
        });
    }

    private void putGif(String URL, String description) {

        descriptionTextView.setText(description);

        Glide.with(TopActivity.this)
                .asGif()
                .load(URL)
                .placeholder(R.drawable.progress_bar)
                .error(R.drawable.ic_error_image)
                .fallback(R.drawable.ic_error_image)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(gifImageView);
    }

    private void getAllGifsResponse(int pageNumber) {
        NetworkService.getInstance()
                .getDevelopersLifeApi()
                .getLatestGif("top", pageNumber)
                .enqueue(new Callback<AllGifsResponse>() {
                    @Override
                    public void onResponse(Call<AllGifsResponse> call, Response<AllGifsResponse> response) {
                        try {
                            totalCount = response.body().getTotalCount();
                            List<DataModel> dataModel = response.body().getDataModelList();

                            for (DataModel s : dataModel) {
                                urls.add(s.getGifURL().replaceFirst("http", "https"));
                                descriptions.add(s.getDescription());
                            }


                            putGif(urls.get(positionCount - 1), descriptions.get(positionCount - 1));
                        } catch (Exception e) {
                            onFailure(call, new Throwable());
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(Call<AllGifsResponse> call, Throwable t) {

                        forwardButton.setEnabled(false);
                        backButton.setEnabled(false);
                        gifImageView.setImageResource(R.drawable.ic_error_image);
                        descriptionTextView.setText("Ошибка!");
                        rebootButton.setVisibility(View.VISIBLE);
                        rebootButton.setEnabled(true);
                    }
                });
    }

    private static int rnd(int max) {
        return (int) (Math.random() * ++max);
    }


    void loadMenu() {

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.latest:
                    Intent intent = new Intent(TopActivity.this, LatestActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    this.finish();
                    break;


                case R.id.random:
                    Intent intent1 = new Intent(TopActivity.this, MainActivity.class);
                    startActivity(intent1);
                    overridePendingTransition(0, 0);
                    this.finish();
                    break;

                case R.id.hot:
                    Intent intent2 = new Intent(TopActivity.this, HotActivity.class);
                    startActivity(intent2);
                    overridePendingTransition(0, 0);
                    this.finish();
                    break;
            }

            return false;
        });
    }
}