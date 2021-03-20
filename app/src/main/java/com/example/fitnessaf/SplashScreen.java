package com.example.fitnessaf;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent= new Intent(SplashScreen.this, Intro.class);
                startActivity(intent);

                finish();
            }

        }, 1000);

    }
}