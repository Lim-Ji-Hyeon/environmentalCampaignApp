package com.example.environmentalcampaign;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderPage extends RecyclerView.ViewHolder {

    private ImageView iv_cardnews;

    DataPage data;

    public ViewHolderPage(@NonNull View itemView) {
        super(itemView);
        iv_cardnews = itemView.findViewById(R.id.iv_cardnews);
    }

    public void onBind(DataPage data) {
        this.data = data;

        iv_cardnews.setImageDrawable(data.getImage());
    }
}
