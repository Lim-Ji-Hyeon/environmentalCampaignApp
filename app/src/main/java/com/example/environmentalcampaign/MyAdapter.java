package com.example.environmentalcampaign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<CpData> sample;

    public MyAdapter(Context context, ArrayList<CpData> data) {
        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return sample.size();
    }

    @Override
    public CpData getItem(int position) {
        return sample.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.custom_listview, null);

        TextView achievementRate = (TextView)view.findViewById(R.id.tv_achievement_rate);
        TextView dDay = (TextView)view.findViewById(R.id.tv_Dday);
        TextView cpName = (TextView)view.findViewById(R.id.tv_cp_name);
        TextView cpContext = (TextView)view.findViewById(R.id.tv_cp_content);
        ImageView logo = (ImageView)view.findViewById(R.id.iv_logo);

        achievementRate.setText("예상 달성률 " + sample.get(position).getRate() + "%");
        dDay.setText("D+" + sample.get(position).getdDay());
        cpName.setText(sample.get(position).getName());
        cpContext.setText(sample.get(position).getFrequency() + " " + sample.get(position).getStime() + "~" + sample.get(position).getEtime() +
        "\n" + sample.get(position).getSdate() + " 시작");
        logo.setImageResource(sample.get(position).getLogo());

        return view;
    }
}
