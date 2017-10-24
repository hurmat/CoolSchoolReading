package com.tes.coolschool;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailView;

public class MainActivity extends YouTubeBaseActivity {

     YouTubePlayerView youtubePlayerView;
     YouTubePlayer.OnInitializedListener onInitializeListener;


    YouTubeThumbnailView youTubeThumbnailView;

    public static final  String Video_ID = "yBKMztVpkBc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        youtubePlayerView =(YouTubePlayerView) findViewById(R.id.youtubeView);
        onInitializeListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                youTubePlayer.loadVideo(Video_ID);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        } ;

        youtubePlayerView.initialize(CommonKeys.API_KEY,onInitializeListener);

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.viewList);
        recyclerView.setHasFixedSize(true);
        //to use RecycleView, you need a layout manager. default is LinearLayoutManager
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerAdapter adapter=new RecyclerAdapter(MainActivity.this);
        recyclerView.setAdapter(adapter);


    }
}
