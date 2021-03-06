package com.tes.coolschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tes.coolschool.util.IabHelper;
import com.tes.coolschool.util.IabResult;
import com.tes.coolschool.util.Inventory;
import com.tes.coolschool.util.Purchase;

public class MainActivityOne extends AppCompatActivity  {


    IabHelper iabHelper;
    String SKU_ID ="android.test.purchased";
    static ProgressBar progressBar;
    static RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_one);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView=(RecyclerView)findViewById(R.id.viewList);

        showProgressBar();

        CommonKeys.purchasedVideoID.add(CommonKeys.VideoID[0]);
        iabHelper = new IabHelper(this, CommonKeys.Base64Publickey);
        iabHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    // there was a problem.
                //    Log.d("Helper", "Problem setting up In-app Billing: " + result);
                }else {
                    // IAB is fully set up!
                   // Toast.makeText(MainActivityOne.this, "In app billing setup successfull!", Toast.LENGTH_SHORT).show();
                   // consumeItem();
                }
            }
        });

        recyclerView.setHasFixedSize(true);
        //to use RecycleView, you need a layout manager. default is LinearLayoutManager
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerAdapter adapter=new RecyclerAdapter(MainActivityOne.this);

        recyclerView.setAdapter(adapter);

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

    public void stratPurchase(final String videoId, final String Video_SKU_ID) {


        CommonKeys.ID_Video = videoId;
       // SKU_ID = videoId;
        try {


            iabHelper.launchPurchaseFlow(MainActivityOne.this, Video_SKU_ID, 10001, mPurchaseFinishedListener, "aaa");

        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }


    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        @Override
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
                {
                    if (result.isFailure()) {

                      //  Toast.makeText(MainActivityOne.this, "OnIabPurchaseFinishedListener"+result, Toast.LENGTH_SHORT).show();

                        if(result.getResponse()==7){


                            startActivity(new Intent(MainActivityOne.this, PlayActivity.class));
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


                        }
                        // Handle error
                        return;
                    }
                    else if (purchase.getSku().equals(SKU_ID)) {
                        Toast.makeText(MainActivityOne.this, "Purchased!!", Toast.LENGTH_SHORT).show();
                        Log.d("Test", CommonKeys.ID_Video);
                        CommonKeys.purchasedVideoID.add(CommonKeys.ID_Video);

                       startActivity(new Intent(MainActivityOne.this, PlayActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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


    public void showProgressBar(){

        recyclerView.setVisibility(View.INVISIBLE);
       // progressBar.incrementProgressBy(10);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar(){

        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }


}
