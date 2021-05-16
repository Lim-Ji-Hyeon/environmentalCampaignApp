package com.example.environmentalcampaign.feed;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.environmentalcampaign.cp_info.CampaignInformation;
import com.example.environmentalcampaign.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder>{

    private List<FeedItem> feeditems;

    public FeedAdapter(List<FeedItem> feeditems) {
        this.feeditems = feeditems;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FeedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_recyclerview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        holder.setFeedImageView(feeditems.get(position));
    }

    @Override
    public int getItemCount() { return feeditems.size(); }

    class FeedViewHolder extends RecyclerView.ViewHolder{

        RoundedImageView feedImageView;

        FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            feedImageView = itemView.findViewById(R.id.imagePost);

            // Feed에 있는 image를 클릭했을 때 피드 이미지 상세 페이지로 넘어간다.
            feedImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), FeedImageDetailPage.class);
                    view.getContext().startActivity(intent);
                }
            });
        }

        void setFeedImageView(FeedItem feedItem) {

            //
            feedImageView.setImageResource(feedItem.getImage());
        }
    }
}
