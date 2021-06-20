package com.example.environmentalcampaign.feed;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.environmentalcampaign.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FeedCommentAdapter extends RecyclerView.Adapter<FeedCommentAdapter.FeedCommentViewHolder>{

    private ArrayList<FeedCommentItem> feedCommentItems;
    private Context context;

    public FeedCommentAdapter(ArrayList<FeedCommentItem> feedCommentItems, Context context) {
        this.feedCommentItems = feedCommentItems;
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public FeedCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_comment_recyclerview_item, parent, false);
        FeedCommentViewHolder holder = new FeedCommentViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedCommentViewHolder holder, int position) {
        // 각 아이템을 연결해준다.
        Glide.with(holder.itemView)
                .load(feedCommentItems.get(position).getUserImage())
                .into(holder.userImage);

        holder.nickname.setText(feedCommentItems.get(position).getNickname());
        holder.comment_text.setText(feedCommentItems.get(position).getComment());
        holder.comment_text_date.setText(feedCommentItems.get(position).getDate());

    }

    @Override
    public int getItemCount() { return (feedCommentItems != null ? feedCommentItems.size() : 0); }

    class FeedCommentViewHolder extends RecyclerView.ViewHolder {

//        public Boolean heartButtonPush;
//        int heartN;

        ImageView userImage;
        TextView nickname, comment_text, comment_text_date;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public FeedCommentViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.userImage);
            userImage.setBackground(new ShapeDrawable(new OvalShape()));
            userImage.setClipToOutline(true);

            nickname = itemView.findViewById(R.id.nickname);
            comment_text = itemView.findViewById(R.id.comment_text);
            comment_text_date = itemView.findViewById(R.id.comment_text_date);

        }
    }
}
