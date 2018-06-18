package com.example.paulina.a9bits.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.paulina.a9bits.PostDetailsActivity;
import com.example.paulina.a9bits.R;
import com.example.paulina.a9bits.model.Post;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder> {
    private List<Post> postList;
    private Context context;
    private View myView;

    public PostListAdapter(List<Post> postList, Context c) {
        this.postList = postList;
        this.context = c;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        myView = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostViewHolder holder, final int position) {
        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myView.getContext(), PostDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("post_id", postList.get(position).getId());
                myView.getContext().startActivity(intent);
            }
        });
        holder.setIsRecyclable(false);
        long time = Long.parseLong(postList.get(position).getTimestamp());
        String shortDescription = postList.get(position).getShort_description();

        if (shortDescription.length() > 100) {
            shortDescription = shortDescription.substring(0, 100) + "...";
        }
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000L);
        String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();

        holder.postTitleLabel.setText(postList.get(position).getTitle());
        holder.postDateLabel.setText(date);
        holder.postDescLabel.setText(shortDescription);

        Glide.with(context).load("https://cdn.pixabay.com/photo/2018/05/17/17/50/world-cup-2018-3409222_960_720.jpg").into(holder.postImageView);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView postImageView;
        TextView postTitleLabel;
        TextView postDescLabel;
        TextView postDateLabel;

        PostViewHolder(View itemView) {
            super(itemView);

            postDateLabel = itemView.findViewById(R.id.item_pin_date_label);
            postDescLabel = itemView.findViewById(R.id.item_post_description_label);
            postImageView = itemView.findViewById(R.id.item_post_image_view);
            postTitleLabel = itemView.findViewById(R.id.item_post_title_label);
        }
    }
}