package com.techeasesolutions.coolschoolreading;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                  //  startActivity(new Intent(SplashActivity.this, MainActivityOne.class));
                    startActivity(new Intent(SplashActivity.this, IntroductionActiviy.class));
                    finish();
                }
            }
        };
        timer.start();
    }
}
