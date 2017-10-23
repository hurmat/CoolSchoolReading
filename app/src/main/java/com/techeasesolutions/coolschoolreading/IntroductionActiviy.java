package com.techeasesolutions.coolschoolreading;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;

public class IntroductionActiviy extends AppCompatActivity {

    Toolbar toolbarIntro;
    LinearLayout llBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction_activiy);

        llBack =(LinearLayout)findViewById(R.id.llBack);
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroductionActiviy.this, MainActivityOne.class));

            }
        });
        toolbarIntro =(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbarIntro);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // toolbar.setLogo(R.drawable.);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;

    }
}
