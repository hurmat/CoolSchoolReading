package com.tes.coolschool;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

/**
 * Created by hurmat on 04/11/2017.
 */

public class PlayAdapter extends RecyclerView.Adapter<PlayAdapter.VideoInfoHolder> {

    String[] VideoID = {"w42RKov-Fpc", "IjxsOxgNV1I", "I8TzcfBjSoc","UZUgEEM0bQ4&t","8ueYlFT0G0c","UeutjHRg0wY","Z_67dG5guow&t","0vN4jT4o3MQ" };
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

        final YouTubeThumbnailLoader.OnThumbnailLoadedListener  onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener(){
            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

            }

            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                youTubeThumbnailView.setVisibility(View.VISIBLE);
                // holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
            }
        };

        holder.youTubeThumbnailView.initialize(CommonKeys.API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {


                youTubeThumbnailLoader.setVideo(VideoID[position]);
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);

            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                //write something for failure
            }
        });

    }

    @Override
    public int getItemCount() {
        return VideoID.length;
    }


    public class VideoInfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //  protected RelativeLayout relativeLayoutOverYouTubeThumbnailView;
        YouTubeThumbnailView youTubeThumbnailView;
       // protected ImageView buyButton;

        public VideoInfoHolder(View itemView) {
            super(itemView);
            // playButton=(ImageView)itemView.findViewById(R.id.btnYoutube_player);
            //  playButton.setOnClickListener(this);
            // relativeLayoutOverYouTubeThumbnailView = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
            youTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.yt_thumbnail);
            youTubeThumbnailView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            Toast.makeText(ctx, "clicked", Toast.LENGTH_SHORT).show();

            if(ctx instanceof PlayActivity){
                ((PlayActivity)ctx).youtubePlayer.loadVideo(VideoID[getLayoutPosition()]);           }



       /*Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) ctx, CommonKeys.API_KEY, VideoID[getLayoutPosition()]);
            ctx.startActivity(intent);*/
        }
    }
}
