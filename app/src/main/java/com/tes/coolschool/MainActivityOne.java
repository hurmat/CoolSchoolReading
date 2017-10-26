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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.tes.coolschool.util.IabHelper;
import com.tes.coolschool.util.IabResult;
import com.tes.coolschool.util.Inventory;
import com.tes.coolschool.util.Purchase;

public class MainActivityOne extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {

    Toolbar toolbar;
    public static final  String Video_ID = "yBKMztVpkBc";

    IabHelper iabHelper;

    Button testBtn;
    TextView testText;
    String SKU_ID ="android.test.purchased";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_one);

        toolbar =(Toolbar) findViewById(R.id.toolbarOne);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
       // toolbar.setLogo(R.drawable.);

        iabHelper = new IabHelper(this, CommonKeys.Base64Publickey);


        YouTubePlayerSupportFragment fragment =
                (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_fragment);
        fragment.initialize(CommonKeys.API_KEY,this);

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


        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.viewList);
        recyclerView.setHasFixedSize(true);
        //to use RecycleView, you need a layout manager. default is LinearLayoutManager
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        StoriesAdapter storiesAdapter = new StoriesAdapter(MainActivityOne.this);
        recyclerView.setAdapter(storiesAdapter);

        testBtn = (Button) findViewById(R.id.btnBuy);
        testBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
                @Override
                public void onIabPurchaseFinished(IabResult result, Purchase purchase) {

                    {
                        if (result.isFailure()) {

                            // Handle error
                            return;
                        }
                        else if (purchase.getSku().equals(SKU_ID)) {
                          //  Toast.makeText(MainActivityOne.this, "Purchased!!", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            };

            try {

                iabHelper.launchPurchaseFlow(MainActivityOne.this,SKU_ID, 10001,mPurchaseFinishedListener, "aaa");
            } catch (IabHelper.IabAsyncInProgressException e) {
                e.printStackTrace();
            }


            IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
                @Override
                public void onQueryInventoryFinished(IabResult result, Inventory inv) {


                    if (result.isFailure()) {
                        // handle error here
                    }
                    else {

                        if (inv.hasPurchase(SKU_ID)) {

                            try {
                                iabHelper.consumeAsync(inv.getPurchase(SKU_ID), null);
                            } catch (IabHelper.IabAsyncInProgressException e) {
                                e.printStackTrace();
                            }
                        }
                        // does the user have the premium upgrade?
                        //mIsPremium = inv.hasPurchase(SKU_PREMIUM);
                        // update UI accordingly
                    }

                }
            };
            try {
                iabHelper.queryInventoryAsync(mGotInventoryListener);
            } catch (IabHelper.IabAsyncInProgressException e) {
                e.printStackTrace();
            }



        }
    });

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
