package com.example.environmentalcampaign.feed;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.environmentalcampaign.cp_info.CampaignInformation;
import com.example.environmentalcampaign.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder>{

    private ArrayList<FeedItem> feeditems;
    private Context context;

    public FeedAdapter(ArrayList<FeedItem> feeditems, Context context) {
        this.feeditems = feeditems;
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_recyclerview_item, parent, false);
        FeedViewHolder holder = new FeedViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        // 각 아이템을 연결해준다.
        Glide.with(holder.itemView)
                .load(feeditems.get(position).getImage())
                .into(holder.feedImageView);
        // holder.tv_id.setText(arraylist.get(position).getId()); - 텍스트 연결
        //holder.setFeedImageView(feeditems.get(position));
    }

    @Override
    public int getItemCount() { return (feeditems != null ? feeditems.size() : 0); }

    class FeedViewHolder extends RecyclerView.ViewHolder{

        ImageView feedImageView;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            feedImageView = itemView.findViewById(R.id.imagePost);
            feedImageView.setClipToOutline(true);


            // Feed에 있는 image를 클릭했을 때 피드 이미지 상세 페이지로 넘어간다.
            feedImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), FeedImageDetailPage.class);
                    view.getContext().startActivity(intent);
                }
            });
        }

//        void setFeedImageView(FeedItem feedItem) {
//
//            //
//            feedImageView.setImageResource(feedItem.getImage());
//        }
    }
}
