package com.tes.coolschool;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

public class PlayActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener  {

    ImageButton back, next;
    RecyclerView recyclerView;
    private static boolean isFullscreen=true ;
    FrameLayout frame ;
    private Dialog mDialog;
    View v;
    LinearLayout ll ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        frame =(FrameLayout) findViewById(R.id.youtube_frame);
       ll = (LinearLayout) findViewById(R.id.ll);

        YouTubePlayerSupportFragment fragment =
                (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtubePlayFrag);
        fragment.initialize(CommonKeys.API_KEY,this);



        recyclerView =(RecyclerView)findViewById(R.id.List);
        back =(ImageButton)findViewById(R.id.btnBack);
        next =(ImageButton)findViewById(R.id.btnNext);
        recyclerView.setHasFixedSize(true);
        //to use RecycleView, you need a layout manager. default is LinearLayoutManager
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        PlayAdapter adapter=new PlayAdapter(PlayActivity.this);
        recyclerView.setAdapter(adapter);




        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

               // startActivity(new Intent(PlayActivity.this,MainActivityOne.class));
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PlayActivity.this, "Play Next!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setVisibility() {

        recyclerView.setVisibility(View.GONE);
        back.setVisibility(View.GONE);
        next.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_righ);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, final boolean b) {

        setVisibility();
        youTubePlayer.loadVideo(CommonKeys.ID_Video);
        youTubePlayer.setFullscreen(true);

        youTubePlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
            @Override
            public void onFullscreen(boolean b) {

                Toast.makeText(PlayActivity.this, "fulscreen"+b, Toast.LENGTH_SHORT).show();
                PlayActivity.this.mDialog = new Dialog(PlayActivity.this, R.style.AppTheme_NoActionBar_Fullscreen);
                PlayActivity.this.mDialog.setContentView(R.layout.layout_overlay);

                if(b)
                {

                    PlayActivity.this.mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    PlayActivity.this.mDialog.show();
                    

                }
                else
                    {


                        PlayActivity.this.mDialog.getWindow().setGravity(Gravity.TOP);
                        PlayActivity.this.mDialog.getWindow().setLayout(600,400);
                        PlayActivity.this.mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        PlayActivity.this.mDialog.show();
                       /* params =  ll.getLayoutParams();
                        params.width=ll.getWidth();
                        params.height=ll.getHeight();*/

                        /*params= v.getLayoutParams();
                        params.width=400;
                        params.height=200;*/
                    }

                v = PlayActivity.this.mDialog.findViewById(R.id.container);


                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(isFullscreen){
                            isFullscreen=false;
                            youTubePlayer.setFullscreen(false);
                            recyclerView.setVisibility(View.VISIBLE);

                            back.setVisibility(View.VISIBLE);

                            next.setVisibility(View.VISIBLE);


                        }else {
                            isFullscreen=true;
                            youTubePlayer.setFullscreen(true);
                            setVisibility();
                           // v.setLayoutParams();

                        }
                    }
                });


            }
        });


        /*setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFullscreen){
                    youTubePlayer.setFullscreen(false);
                    isFullscreen=false;
                }else if(!isFullscreen){
                    youTubePlayer.setFullscreen(true);
                }
            }
        });*/
       // youTubePlayer.setShowFullscreenButton(false);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }




}
