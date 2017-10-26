package com.tes.coolschool;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by hurmat on 26/10/2017.
 */

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.MyViewHolder> {

    Context ctx;
    LayoutInflater inflater;

    public StoriesAdapter(Context context) {
        this.ctx = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = inflater.inflate(R.layout.story_item, parent, false);

        return new MyViewHolder(rootView);

    }

    @Override
    public void onBindViewHolder(StoriesAdapter.MyViewHolder holder, int position) {
        holder.storyImage.setVisibility(View.VISIBLE);
        holder.price.setText("1.99");

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView storyImage ;
        Button price;

        public MyViewHolder(View itemView) {
            super(itemView);
            storyImage = (ImageView)  itemView.findViewById(R.id.storyImage);
            price=(Button)itemView.findViewById(R.id.btnPrice);
        }
    }
}
