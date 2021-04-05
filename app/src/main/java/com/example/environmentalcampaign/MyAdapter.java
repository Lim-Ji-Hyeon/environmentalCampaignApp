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

public class MyAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<CpData> sample = new ArrayList<CpData>();

    public MyAdapter(){}

    @Override
    public int getCount() {
        return sample.size();
    }

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

        // "custom_listview" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_listview, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView achievementRate = (TextView)convertView.findViewById(R.id.tv_achievement_rate);
        TextView dDay = (TextView)convertView.findViewById(R.id.tv_Dday);
        TextView cpName = (TextView)convertView.findViewById(R.id.tv_cp_name);
        TextView cpContext = (TextView)convertView.findViewById(R.id.tv_cp_content);
        ImageView logo = (ImageView)convertView.findViewById(R.id.iv_logo);

        // Data set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        CpData listViewItem = sample.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        achievementRate.setText("예상 달성률 " + listViewItem.getRate() + "%");
        dDay.setText("D+" + listViewItem.getdDay());
        cpName.setText(listViewItem.getName());
        cpContext.setText(listViewItem.getFrequency() + " " + listViewItem.getStime() + "~" + listViewItem.getEtime() +
        "\n" + listViewItem.getSdate() + " 시작");
        logo.setImageDrawable(listViewItem.getLogo());

        return convertView;
    }

    public void addItem(int rate, int dDay, String name, String frequency, String stime, String etime, String sdate, Drawable logo) {
        CpData item = new CpData();

        item.setRate(rate);
        item.setdDay(dDay);
        item.setName(name);
        item.setFrequency(frequency);
        item.setStime(stime);
        item.setEtime(etime);
        item.setSdate(sdate);
        item.setLogo(logo);

        sample.add(item);
    }
}
