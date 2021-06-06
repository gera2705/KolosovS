package com.kolosov.kolosovtinkofftest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kolosov.kolosovtinkofftest.models.DataModel;
import com.kolosov.kolosovtinkofftest.request.NetworkService;
//import com.kolosov.kolosovtinkofftest.request.Service;
import com.kolosov.kolosovtinkofftest.response.AllGifsResponse;
import com.kolosov.kolosovtinkofftest.response.RandomGifResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ImageButton forwardButton;
    private ImageButton backButton;
    private Button rebootButton;

    private RadioGroup radioGroup;
    private RadioButton randomRadioButton;
    private RadioButton latestRadioButton;
    private RadioButton hotRadioButton;
    private RadioButton topRadioButton;

    private ImageView imageView;
    private TextView textView;

    private String URL;
    private String description;

    private static int count;
    private static int totalCount;

    private ArrayList<String> urls = new ArrayList<>();
    private ArrayList<String> descriptions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        forwardButton = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.descriptionTextView);
        backButton = findViewById(R.id.button3);
        rebootButton = findViewById(R.id.reboot);

        radioGroup = findViewById(R.id.radioGroup);

        randomRadioButton = findViewById(R.id.random_radio_button);
        latestRadioButton = findViewById(R.id.latest_radio_button);
        hotRadioButton = findViewById(R.id.hot_radio_button);
        topRadioButton = findViewById(R.id.top_radio_button);


        randomRadioButton.setChecked(true);
        rebootButton.setVisibility(View.INVISIBLE);
        rebootButton.setEnabled(false);
        count = 1;



        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.random_radio_button: {
                        Toast.makeText(getApplicationContext(), "Рандом", Toast.LENGTH_SHORT)
                                .show();

                        break;
                    }
                    case R.id.latest_radio_button: {
                        //2585 стр
                        count = 1;

                        int pageNumber = rnd(2585);
                        getAllGifsResponse("latest" , pageNumber);


                        Log.d("total" , String.valueOf(pageNumber));
//                        Toast.makeText(getApplicationContext(), totalCount , Toast.LENGTH_SHORT)
//                                .show();

                        break;
                    }
                    case R.id.top_radio_button: {
                        getAllGifsResponse("top" , 0);
                        Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT)
                                .show();

                        break;
                    }
                    case R.id.hot_radio_button: {
                        Toast.makeText(getApplicationContext(), "Четв элемент нажат", Toast.LENGTH_SHORT)
                                .show();
                        getAllGifsResponse("hot" , 0);
                        break;
                    }
                }
            }
        });

       // getAllGifsResponse();

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


        rebootButton.setOnClickListener(v -> {
            getRetrofitResponse();
            forwardButton.setEnabled(true);
            backButton.setEnabled(true);
            rebootButton.setVisibility(View.INVISIBLE);
            rebootButton.setEnabled(false);
        });

    }

    private void getAllGifsResponse(String category , int pageNumber){
        NetworkService.getInstance()
             .getDevelopersLifeApi()
                .getLatestGif(category , pageNumber)
              .enqueue(new Callback<AllGifsResponse>() {
                  @Override
                  public void onResponse(Call<AllGifsResponse> call, Response<AllGifsResponse> response) {
                      totalCount = response.body().getTotalCount();
                     List<DataModel> dataModel = response.body().getDataModelList();

                      for (DataModel s: dataModel) {
                          urls.add(s.getUrl());
                          descriptions.add(s.getDescription());
                          Log.d("DATA" , s.getDescription());
                      }

                  }

                  @Override
                  public void onFailure(Call<AllGifsResponse> call, Throwable t) {

                  }
              });
    }

    private void getRetrofitResponse() {


            NetworkService.getInstance()
                    .getDevelopersLifeApi()
                    .getRandomGif()
                    .enqueue(new Callback<RandomGifResponse>() {
                        @Override
                        public void onResponse(Call<RandomGifResponse> call, retrofit2.Response<RandomGifResponse> response) {


                            try {
                                DataModel dataModel = response.body().getDataModel();

                                URL = dataModel.getUrl().replaceFirst("http", "https");
                                description = dataModel.getDescription();
//                                description = response.body().getDescription();
//                                URL = response.body().getGifURL().replaceFirst("http", "https");
                                urls.add(URL);
                                descriptions.add(description);

                            } catch (Exception e) {
                                e.printStackTrace();
                               // textView.setText("Ошибка при загрузке изображения :(");
                            }

                            putGif(URL, description);

                            Log.d("TIME", "3");

                        }

                        @Override
                        public void onFailure(Call<RandomGifResponse> call, Throwable t) {
                            forwardButton.setEnabled(false);
                            backButton.setEnabled(false);
                            Log.d("ERROR" , "error");
                            imageView.setImageResource(R.drawable.ic_error_image);
                            textView.setText("Нет подключения к интернету!");
                            rebootButton.setVisibility(View.VISIBLE);
                            rebootButton.setEnabled(true);

                        }
                    });


    }

    private void putGif(String URL , String description){

        textView.setText(description);

        Glide.with(MainActivity.this)
                .asGif()
                .load(URL)
                .placeholder(R.drawable.progress_bar)
                .error(R.drawable.ic_error_image)
                .fallback(R.drawable.ic_error_image)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView);
    }

    public static int rnd(int max)
    {
        return (int) (Math.random() * ++max);
    }
}