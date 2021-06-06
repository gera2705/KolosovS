package com.kolosov.kolosovtinkofftest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HotActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        loadMenu();
    }


    void loadMenu(){

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){

                case R.id.latest:
                    Intent intent = new Intent(HotActivity.this , LatestActivity.class);
                    startActivity(intent);
                    this.finish();
                    break;


                case R.id.top:
                    Intent intent1 = new Intent(HotActivity.this , TopActivity.class);
                    startActivity(intent1);
                    this.finish();
                    break;

                case R.id.random:
                    Intent intent2 = new Intent(HotActivity.this , MainActivity.class);
                    startActivity(intent2);
                    this.finish();
                    break;
            }

            return false;
        });
    }
}