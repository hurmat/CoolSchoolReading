package com.tes.coolschool;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

/**
 * Created by hurmat on 20/10/2017.
 */

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.VideoInfoHolder> {

    //these ids are the unique id for each video
  // String[] VideoID = {"YKrwe8i7XNM", "kKIj7B1uxeo", "I8TzcfBjSoc","UZUgEEM0bQ4&t","8ueYlFT0G0c","UeutjHRg0wY","Z_67dG5guow&t","0vN4jT4o3MQ" };
    String[] VideoID = {"l6Qt7M8uGQQ","lEnQ-nKqkkk","PBi-Cbu02Mw"};
    String[] VideoTitle ={ "Cinderella","Christmas","Snow White"};
    Context ctx;

    public RecyclerAdapter(Context context) {
        this.ctx = context;
    }

    @Override
    public VideoInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new VideoInfoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VideoInfoHolder holder, final int position) {


       final YouTubeThumbnailLoader.OnThumbnailLoadedListener  onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener(){
            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

            }

            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                youTubeThumbnailView.setVisibility(View.VISIBLE);

             //   holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
                holder.title.setText(VideoTitle[position]);
                holder.title.setVisibility(View.VISIBLE);
                MainActivityOne.progressBar.setVisibility(View.GONE);
                MainActivityOne.recyclerView.setVisibility(View.VISIBLE);
            }
        };

        holder.youTubeThumbnailView.initialize(CommonKeys.API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {


                //youTubeThumbnailView.setVisibility(View.VISIBLE);
                youTubeThumbnailLoader.setVideo(VideoID[position]);




                youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
                // youTubeThumbnailLoader.release();
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
        protected ImageView buyButton;
        TextView title;

        public VideoInfoHolder(View itemView) {
            super(itemView);
          //  relativeLayoutOverYouTubeThumbnailView = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);

            youTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.yt_thumbnail);
            title =(TextView)itemView.findViewById(R.id.txtTitle);
            buyButton =(ImageView) itemView.findViewById(R.id.imgBuy);
            buyButton.setOnClickListener(this);
           // youTubeThumbnailView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if(ctx instanceof MainActivityOne){
               ((MainActivityOne)ctx).stratPurchase(VideoID[getLayoutPosition()].toString());
               // ((MainActivityOne)((MainActivityOne) ctx).stratPurchase(PlaylistID[getLayoutPosition()].toString());)
            }

           /*

       Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) ctx, CommonKeys.API_KEY, VideoID[getLayoutPosition()]);
            ctx.startActivity(intent);*/
        }
    }
}
