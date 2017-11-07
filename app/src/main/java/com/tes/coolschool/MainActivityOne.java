package com.tes.coolschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.tes.coolschool.util.IabHelper;
import com.tes.coolschool.util.IabResult;
import com.tes.coolschool.util.Inventory;
import com.tes.coolschool.util.Purchase;

public class MainActivityOne extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {


    public static final  String Video_ID = "yBKMztVpkBc";
    String PaidVideo;
    IabHelper iabHelper;
    String SKU_ID ="android.test.purchased";
    FloatingActionButton searchBtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_one);

        iabHelper = new IabHelper(this, CommonKeys.Base64Publickey);
        /*YouTubePlayerSupportFragment fragment =
                (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_fragment);
        fragment.initialize(CommonKeys.API_KEY,this);*/

        iabHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    Log.d("Helper", "Problem setting up In-app Billing: " + result);
                }else {
                    // Hooray, IAB is fully set up!
                    Toast.makeText(MainActivityOne.this, "In app billing setup successfull!", Toast.LENGTH_SHORT).show();
                    consumeItem();
                }
            }
        });

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.viewList);
        recyclerView.setHasFixedSize(true);
        //to use RecycleView, you need a layout manager. default is LinearLayoutManager
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerAdapter adapter=new RecyclerAdapter(MainActivityOne.this);
        recyclerView.setAdapter(adapter);

       /* StoriesAdapter storiesAdapter = new StoriesAdapter(MainActivityOne.this);
        recyclerView.setAdapter(storiesAdapter);*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("testPurchase", "onActivityResult(" + requestCode + "," + resultCode + "," + data);

        // Pass on the activity result to the helper for handling
        if (!iabHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
            Log.d("testPurchase", "onActivityResult handled by IABUtil.");
        }
    }

    public void stratPurchase(final String videoId) {


        CommonKeys.ID_Video = videoId;
        try {

            iabHelper.launchPurchaseFlow(MainActivityOne.this, SKU_ID, 10001, mPurchaseFinishedListener, "aaa");
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }


    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        @Override
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
                {
                    if (result.isFailure()) {

                        Toast.makeText(MainActivityOne.this, "OnIabPurchaseFinishedListener"+result, Toast.LENGTH_SHORT).show();
                        // Handle error
                        return;
                    }
                    else if (purchase.getSku().equals(SKU_ID)) {
                        Toast.makeText(MainActivityOne.this, "Purchased!!", Toast.LENGTH_SHORT).show();
                       /* Intent intent = YouTubeStandalonePlayer.createVideoIntent(MainActivityOne.this, CommonKeys.API_KEY, PaidVideo);
                        startActivity(intent);*/
                       startActivity(new Intent(MainActivityOne.this, PlayActivity.class));
                       // finish();
                       // consumeItem();


                    }

                }
            }
        };

    public void consumeItem(){

        try {
            iabHelper.queryInventoryAsync(mGotInventoryListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        @Override
        public void onQueryInventoryFinished(IabResult result, Inventory inv) {

            Log.d("test Purchase:", "Query inventory finished");
            if (result.isFailure()) {
                    // handle error here

                Toast.makeText(MainActivityOne.this, "Failed to query inventory :error "+result, Toast.LENGTH_SHORT).show();
                return;
            }
            Log.d("test Purchase:", "Query inventory was successfull");
            if (inv.hasPurchase(SKU_ID)) {
                Log.d("test Purchase:", "already purchased: "+ SKU_ID);
                        try {
                            iabHelper.consumeAsync(inv.getPurchase(SKU_ID), mConsumeFinishedListener);
                        } catch (IabHelper.IabAsyncInProgressException e) {
                            e.printStackTrace();
                        }
            }


            }
    };
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        @Override
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            if(result.isSuccess()){
                Toast.makeText(MainActivityOne.this, "consumed", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(MainActivityOne.this, "Not consumed", Toast.LENGTH_SHORT).show();
            }
        }
    };


   /* @Override
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
    }*/

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
