package com.tes.coolschool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.tes.coolschool.util.IabHelper;
import com.tes.coolschool.util.IabResult;

public class MainActivityOne extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {

    Toolbar toolbar;
    public static final  String Video_ID = "yBKMztVpkBc";

    IabHelper iabHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_one);

        toolbar =(Toolbar) findViewById(R.id.toolbarOne);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
       // toolbar.setLogo(R.drawable.);

        iabHelper = new IabHelper(this, CommonKeys.Base64Publickey);
        iabHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    Log.d("Helper", "Problem setting up In-app Billing: " + result);
                }
                // Hooray, IAB is fully set up!
                Toast.makeText(MainActivityOne.this, "In app billing setup successfull!", Toast.LENGTH_SHORT).show();
            }
        });

        YouTubePlayerSupportFragment fragment =
                (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_fragment);
        fragment.initialize(CommonKeys.API_KEY,this);

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.viewList);
        recyclerView.setHasFixedSize(true);
        //to use RecycleView, you need a layout manager. default is LinearLayoutManager
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerAdapter adapter=new RecyclerAdapter(MainActivityOne.this);
        recyclerView.setAdapter(adapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id = item.getItemId();
        if(res_id==R.id.action_search){
            Toast.makeText(this, "Action search", Toast.LENGTH_SHORT).show();

        }
        else if(res_id == R.id.action_menu){
            Toast.makeText(this, "Action menu", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Nothing found", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.loadVideo(Video_ID);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (iabHelper != null) try {
            iabHelper.dispose();
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
        iabHelper = null;
    }
}
