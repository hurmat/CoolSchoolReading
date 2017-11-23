package com.tes.coolschool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

public class PlayActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener  {

    ImageButton back, next,previous;
    RecyclerView recyclerView;
    private static boolean isFullscreen=true ;
    YouTubePlayer youtubePlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        YouTubePlayerSupportFragment fragment =
                (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtubePlayFrag);
        fragment.initialize(CommonKeys.API_KEY,this);

        recyclerView =(RecyclerView)findViewById(R.id.List);
        back =(ImageButton)findViewById(R.id.btnBack);
        next =(ImageButton)findViewById(R.id.btnNext);
        previous=(ImageButton)findViewById(R.id.btnPrevious);

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



    @Override
    public void onBackPressed() {

        if(isFullscreen){
            youtubePlayer.setFullscreen(false);

        }else {
            super.onBackPressed();
         overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_righ);
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, final boolean b) {

        setVisibility();
        this.youtubePlayer = youTubePlayer;
        youTubePlayer.loadVideo(CommonKeys.ID_Video);
        youTubePlayer.setFullscreen(true);

        youTubePlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
            @Override
            public void onFullscreen(boolean b) {

                Toast.makeText(PlayActivity.this, "fulscreen"+b, Toast.LENGTH_SHORT).show();

                if(!b)
                {
                    isFullscreen=false;
                    resetVisibility();

                }
                else

                    {
                        isFullscreen=true;
                        setVisibility();
                    }



            }
        });
    }

    private void resetVisibility() {

        recyclerView.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);
        previous.setVisibility(View.VISIBLE);

    }

    private void setVisibility() {

        recyclerView.setVisibility(View.GONE);
        back.setVisibility(View.GONE);
        next.setVisibility(View.GONE);
        previous.setVisibility(View.GONE);

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }




}
