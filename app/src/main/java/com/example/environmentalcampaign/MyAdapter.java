package com.example.environmentalcampaign;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        TextView reCp = (TextView)convertView.findViewById(R.id.tv_reCp);
        TextView cpName = (TextView)convertView.findViewById(R.id.tv_cp_name);
        TextView cpContext = (TextView)convertView.findViewById(R.id.tv_cp_content);
        ImageView logo = (ImageView)convertView.findViewById(R.id.iv_logo);
        TextView complete = (TextView)convertView.findViewById(R.id.tv_complete_logo);

        // Data set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        CpData listViewItem = sample.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        achievementRate.setText("현재 달성률 " + listViewItem.getRate() + "%");
        dDay.setText("D-" + countdday(listViewItem.getEdate()));
        reCp.setText(listViewItem.getReCp() + "번째 참여중");
        cpName.setText(listViewItem.getName());
        cpContext.setText(listViewItem.getFrequency() + " " + listViewItem.getStime() + "~" + listViewItem.getEtime() +
        "\n" + emonth(listViewItem.getEdate()) + "." + eday(listViewItem.getEdate()) + "(" + getDayOfWeek(listViewItem.getEdate()) + ") 종료");
        logo.setImageDrawable(listViewItem.getLogo());
        if(listViewItem.getComplete()) { complete.setVisibility(View.VISIBLE); }

        return convertView;
    }

    public void addItem(int rate, int reCp, String name, String frequency, String stime, String etime, int edate, Drawable logo) {
        CpData item = new CpData();

        item.setRate(rate);
        item.setReCp(reCp);
        item.setName(name);
        item.setFrequency(frequency);
        item.setStime(stime);
        item.setEtime(etime);
        item.setEdate(edate);
        item.setLogo(logo);

        sample.add(item);
    }

    // 이미지 위에 완료 표시 해주기 위해. 마지막에 true 넣어주면 됨.
    public void addItem(int rate, int reCp, String name, String frequency, String stime, String etime, int edate, Drawable logo, boolean complete) {
        CpData item = new CpData();

        item.setRate(rate);
        item.setReCp(reCp);
        item.setName(name);
        item.setFrequency(frequency);
        item.setStime(stime);
        item.setEtime(etime);
        item.setEdate(edate);
        item.setLogo(logo);
        item.setComplete(complete);

        sample.add(item);
    }

    public int eyear(int edate) { return Integer.parseInt(String.valueOf(edate).substring(0,4)); }
    public int emonth(int edate) { return Integer.parseInt(String.valueOf(edate).substring(4,6)); }
    public int eday(int edate) { return Integer.parseInt(String.valueOf(edate).substring(6,8)); }

    public int countdday(int edate) {
        try{
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            int myear = eyear(edate);
            int mmonth = emonth(edate);
            int mday = eday(edate);

            Calendar todayCal = Calendar.getInstance(); //오늘날짜 가져오기
            Calendar ddayCal = Calendar.getInstance(); //오늘날짜 가져와서 변경시킴

            mmonth -= 1; //받아온 날짜의 달에서 -1을 해줘야함
            ddayCal.set(myear, mmonth, mday); // 디데이 날짜 입력

            long today = todayCal.getTimeInMillis()/(24*60*60*1000); // 24시간 60분 60초 밀리초로 변환
            long dday = ddayCal.getTimeInMillis()/(24*60*60*1000);
            long count = dday - today; // 디데이 계산
            return (int)count;
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String getDayOfWeek(int idate) {
        String inputDate = String.valueOf(idate);
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = dateFormat.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        String dow = "";
        switch (day) {
            case Calendar.SUNDAY:
                dow = "일";
                break;
            case Calendar.MONDAY:
                dow = "월";
                break;
            case Calendar.TUESDAY:
                dow = "화";
                break;
            case Calendar.WEDNESDAY:
                dow = "수";
                break;
            case Calendar.THURSDAY:
                dow = "목";
                break;
            case Calendar.FRIDAY:
                dow = "금";
                break;
            case Calendar.SATURDAY:
                dow = "토";
                break;
        }
        return dow;
    }
}
