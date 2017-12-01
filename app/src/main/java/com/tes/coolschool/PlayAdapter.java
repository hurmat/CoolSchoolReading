package com.tes.coolschool;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

/**
 * Created by hurmat on 04/11/2017.
 */

public class PlayAdapter extends RecyclerView.Adapter<PlayAdapter.VideoInfoHolder> {

   // String[] VideoID = {"l6Qt7M8uGQQ","lEnQ-nKqkkk","PBi-Cbu02Mw"};

    Context ctx;


    public PlayAdapter(Context context) {
        this.ctx = context;
    }

    @Override
    public PlayAdapter.VideoInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.play_item, parent, false);
        return new PlayAdapter.VideoInfoHolder(itemView);
    }


    @Override
    public void onBindViewHolder(PlayAdapter.VideoInfoHolder holder, final int position) {

        holder.youTubeThumbnailView.initialize(CommonKeys.API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {

                youTubeThumbnailLoader.setVideo(CommonKeys.purchasedVideoID.get(position));
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {

                        youTubeThumbnailLoader.release();
                    }

                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                    }
                });


            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                //write something for failure
            }
        });

    }

    @Override
    public int getItemCount() {
        return CommonKeys.purchasedVideoID.size();
    }


    public class VideoInfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        YouTubeThumbnailView youTubeThumbnailView;


        public VideoInfoHolder(View itemView) {
            super(itemView);
            youTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.yt_thumbnail);
            youTubeThumbnailView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(ctx instanceof PlayActivity){
                ((PlayActivity)ctx).youtubePlayer.loadVideo(CommonKeys.purchasedVideoID.get(getLayoutPosition()));           }
        }
    }

}
