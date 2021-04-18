package com.example.environmentalcampaign;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyCompleteAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<CpCompleteData> sample = new ArrayList<CpCompleteData>();

    public MyCompleteAdapter(){}

    @Override
    public int getCount() { return sample.size(); }

    @Override
    public Object getItem(int position) {
        return sample.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_listview_complete_cp, parent, false);
        }

        TextView cpName = (TextView)convertView.findViewById(R.id.tv_cp_name);
        TextView avrRate = (TextView)convertView.findViewById(R.id.tv_avr_rate);
        TextView frequency = (TextView)convertView.findViewById(R.id.tv_frequency);
        TextView reCp = (TextView)convertView.findViewById(R.id.tv_reCp);
        ImageView logo = (ImageView)convertView.findViewById(R.id.iv_logo);

        CpCompleteData item = sample.get(position);

        cpName.setText(item.getName());
        avrRate.setText("평균 달성률 " + item.getRate() + "%");
        frequency.setText(item.getFrequency());
        reCp.setText("누적 " + item.getReCp() + "회 참여");
        logo.setImageDrawable(item.getLogo());

        return convertView;
    }

    public void addItem(String name, int rate, String frequency, int reCp, Drawable logo) {
        CpCompleteData item = new CpCompleteData();

        item.setName(name);
        item.setRate(rate);
        item.setFrequency(frequency);
        item.setReCp(reCp);
        item.setLogo(logo);

        sample.add(item);
    }
}
