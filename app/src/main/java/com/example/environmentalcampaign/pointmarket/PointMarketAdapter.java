package com.example.environmentalcampaign.pointmarket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.environmentalcampaign.R;

import java.util.ArrayList;

public class PointMarketAdapter extends RecyclerView.Adapter<PointMarketAdapter.PointMarketViewHolder> {

    private ArrayList<PointMarketItem> pointMarketItems;
    private Context context;

    public PointMarketAdapter(ArrayList<PointMarketItem> pointMarketItems, Context context) {
        this.pointMarketItems = pointMarketItems;
        this.context = context;
    }

    @NonNull
    @Override
    public PointMarketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.point_market_recyclerview_item, parent, false);
        PointMarketViewHolder holder = new PointMarketViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PointMarketViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(pointMarketItems.get(position).getImage())
                .into(holder.pointImageView);
        String content = pointMarketItems.get(position).getBrand() + "\n" +
                pointMarketItems.get(position).getName() + "\n" +
                thousandsComma(pointMarketItems.get(position).getPoint()) + " p";
        holder.pmContent.setText(content);
    }

    @Override
    public int getItemCount() { return (pointMarketItems != null ? pointMarketItems.size() : 0); }

    public class PointMarketViewHolder extends RecyclerView.ViewHolder {

        LinearLayout lo_pm;
        ImageView pointImageView;
        TextView pmContent;

        public PointMarketViewHolder(@NonNull View itemView) {
            super(itemView);
            lo_pm = itemView.findViewById(R.id.lo_point_market);
            pointImageView = itemView.findViewById(R.id.iv_pm_image);
            pmContent = itemView.findViewById(R.id.tv_pm_content);

            // pointmarket 클릭 시 이벤트 구현(lo_pm) 해야 함.
        }
    }

    // 천 단위마다 , 붙여주는 메소드
    public String thousandsComma(int n) {
        String number = String.valueOf(n);
        String cost = "";
        int count = 0;
        for(int i = number.length()-1; i >= 0; i--) {
            if((count != 0) && ((count%3) == 0))
                cost = "," + cost;
            cost = number.charAt(i) + cost;
            count++;
        }
        return cost;
    }
}
