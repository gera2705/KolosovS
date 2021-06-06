package com.kolosov.kolosovtinkofftest;

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
import com.kolosov.kolosovtinkofftest.models.DataModel;
import com.kolosov.kolosovtinkofftest.request.NetworkService;
import com.kolosov.kolosovtinkofftest.response.AllGifsResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LatestActivity extends AppCompatActivity {

    private ImageButton forwardButton;
    private ImageButton backButton;
    private Button rebootButton;

    private BottomNavigationView bottomNavigationView;


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

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);

        rebootButton.setVisibility(View.INVISIBLE);
        rebootButton.setEnabled(false);
        count = 1;

        loadMenu();

       // getAllGifsResponse("latest" , rnd(12923));

        getAllGifsResponse(rnd(2585));

        forwardButton.setOnClickListener(v -> {
            if(count == 5){
                count = 1;
                urls.clear();
                descriptions.clear();

               // int i = rnd(2585);

                getAllGifsResponse(rnd(2585));
               // Log.d("DATA2" , String.valueOf(i));
            }else {
                putGif(urls.get(count), descriptions.get(count));
                count++;
            }


            Log.d("count2" , String.valueOf(count));
        });



        backButton.setOnClickListener(v -> {
            if(count - 1 == 0){
                Toast.makeText(LatestActivity.this, "Гифки в КЭШе закончились :(", Toast.LENGTH_SHORT).show();
            }else {

                putGif(urls.get(count-2) , descriptions.get(count-2));
                count--;
            }
            Log.d("count2" , String.valueOf(count));


        });
    }

    private void getAllGifsResponse(int pageNumber){
        NetworkService.getInstance()
                .getDevelopersLifeApi()
                .getLatestGif("latest", pageNumber)
                .enqueue(new Callback<AllGifsResponse>() {
                    @Override
                    public void onResponse(Call<AllGifsResponse> call, Response<AllGifsResponse> response) {
                        totalCount = response.body().getTotalCount();
                        List<DataModel> dataModel = response.body().getDataModelList();

                        for (DataModel s: dataModel) {
                            urls.add(s.getGifURL().replaceFirst("http", "https"));
                            descriptions.add(s.getDescription());
                            Log.d("DATA" , s.getDescription());
                        }


                        try {
                            putGif(urls.get(count-1) , descriptions.get(count-1));
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(Call<AllGifsResponse> call, Throwable t) {

                    }
                });
    }

    private void putGif(String URL , String description){

        textView.setText(description);

        Glide.with(LatestActivity.this)
                .asGif()
                .load(URL)
                .placeholder(R.drawable.progress_bar)
                .error(R.drawable.ic_error_image)
                .fallback(R.drawable.ic_error_image)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView);
    }

    private static int rnd(int max)
    {
        return (int) (Math.random() * ++max);
    }

    void loadMenu(){

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){

                case R.id.random:
                    Intent intent = new Intent(LatestActivity.this , MainActivity.class);
                    startActivity(intent);
                    this.finish();
                    break;


                case R.id.top:
                    Intent intent1 = new Intent(LatestActivity.this , TopActivity.class);
                    startActivity(intent1);
                    this.finish();
                    break;

                case R.id.hot:
                    Intent intent2 = new Intent(LatestActivity.this , HotActivity.class);
                    startActivity(intent2);
                    this.finish();
                    break;
            }

            return false;
        });
    }
}