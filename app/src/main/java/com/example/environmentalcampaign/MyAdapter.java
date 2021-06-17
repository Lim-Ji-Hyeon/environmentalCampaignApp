package com.example.environmentalcampaign;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.environmentalcampaign.certification_page.Certi_Info;
import com.example.environmentalcampaign.cp_info.CampaignItem;
import com.example.environmentalcampaign.cp_info.MyCampaignItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<MyCampaignItem> sample;

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String uid;

    TextView achievementRate, complete;

    public MyAdapter(ArrayList<MyCampaignItem> sample, Context context){
        this.sample = sample;
        this.context = context;
    }

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
        achievementRate = (TextView)convertView.findViewById(R.id.tv_achievement_rate);
        TextView dDay = (TextView)convertView.findViewById(R.id.tv_Dday);
        TextView reCp = (TextView)convertView.findViewById(R.id.tv_reCp);
        TextView cpName = (TextView)convertView.findViewById(R.id.tv_cp_name);
        TextView certiLastDate = (TextView)convertView.findViewById(R.id.tv_certiLastDate);
        TextView certiFrequency = (TextView)convertView.findViewById(R.id.tv_certiFrequency);
        ImageView logo = (ImageView)convertView.findViewById(R.id.iv_logo);
        complete = (TextView)convertView.findViewById(R.id.tv_complete_logo);

        // Data set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        MyCampaignItem listViewItem = sample.get(position);

        // uid 가져오기
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("environmentalCampaign").child("Campaign").child(listViewItem.getCampaignCode()).child("campaign");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CampaignItem campaignItem = snapshot.getValue(CampaignItem.class);
                CpData cpData = new CpData();

                cpData.setRate(getAchievementRate(listViewItem.getCampaignCode(), listViewItem.getCertiCount()));
                cpData.setEdate(listViewItem.getEndDate());
                cpData.setReCp(listViewItem.getReCount());
                cpData.setName(campaignItem.getTitle());
                cpData.setPeriod(campaignItem.getPeriod());
                cpData.setFrequency(campaignItem.getFrequency());
                cpData.setLogo(campaignItem.getLogo());
                cpData.setComplete(todayCerti(listViewItem.getCampaignCode()));

                // 아이템 내 각 위젯에 데이터 반영
                int edate = Integer.parseInt(cpData.getEdate());
//                achievementRate.setText("현재 달성률 " + listViewItem.getRate() + "%");
                dDay.setText("D-" + countdday(edate));
                reCp.setText(cpData.getReCp() + "번째 참여중");
                cpName.setText(cpData.getName());
                cpName.setVisibility(View.VISIBLE);
                certiLastDate.setText(emonth(edate) + "." + eday(edate) + "(" + getDayOfWeek(edate) + ") 종료");
                certiLastDate.setVisibility(View.VISIBLE);
                certiFrequency.setText(cpData.getPeriod() + ", " + cpData.getFrequency());
                certiFrequency.setVisibility(View.VISIBLE);
//                logo.setImageDrawable(listViewItem.getLogo());
                Glide.with(context).load(cpData.getLogo()).into(logo);
                logo.setVisibility(View.VISIBLE);
//                if(cpData.getComplete()) { complete.setVisibility(View.VISIBLE); }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Log.e("MyAdapterActivity", String.valueOf(error.toException())); //에러문 출력
            }
        });

        return convertView;
    }

//    public void addItem(int rate, int reCp, String name, String period, String frequency, int edate, Drawable logo) {
//        CpData item = new CpData();
//
//        item.setRate(rate);
//        item.setReCp(reCp);
//        item.setName(name);
//        item.setPeriod(period);
//        item.setFrequency(frequency);
//        item.setEdate(edate);
//        item.setLogo(logo);
//        item.setComplete(false);
//
//        sample.add(item);
//    }
//
//    // 이미지 위에 완료 표시 해주기 위해. 마지막에 true 넣어주면 됨.
//    public void addItem(int rate, int reCp, String name, String period, String frequency, int edate, Drawable logo, boolean complete) {
//        CpData item = new CpData();
//
//        item.setRate(rate);
//        item.setReCp(reCp);
//        item.setName(name);
//        item.setPeriod(period);
//        item.setFrequency(frequency);
//        item.setEdate(edate);
//        item.setLogo(logo);
//        item.setComplete(complete);
//
//        sample.add(item);
//    }

    // 현재 달성률 구하기
    public int getAchievementRate(String campaignCode, int certiCount) {
        ArrayList<Certi_Info> certi_infos = new ArrayList<>();
        database.getReference("environmentalCampaign").child("Certification").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                certi_infos.clear();
                if((dataSnapshot.hasChild(uid))&&(dataSnapshot.child(uid).hasChild(campaignCode))) {
                    for(DataSnapshot snapshot : dataSnapshot.child(uid).child(campaignCode).getChildren()) {
                        Certi_Info certi_info = snapshot.getValue(Certi_Info.class);
                        certi_infos.add(certi_info);
                    }
                    int certiN = certi_infos.size();
                    achievementRate.setText("현재 달성률 " + certiN*100/certiCount + "%");
                } else {
                    int certiN = 0;
                    achievementRate.setText("현재 달성률 " + certiN*100/certiCount + "%");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Log.e("getAchievementRate", String.valueOf(error.toException())); //에러문 출력
            }
        });
        String s = achievementRate.getText().toString();
        return Integer.parseInt(s.substring(7, s.length()-1));
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

    // 인증 완료 확인
    boolean todayCerti(String campaignCode) {
        // 현재시간 가져오기
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        String getTime = simpleDate.format(mDate);

        database.getReference("environmentalCampaign").child("Certification").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if((dataSnapshot.hasChild(uid))&&(dataSnapshot.child(uid).hasChild(campaignCode))) {
                    for(DataSnapshot snapshot : dataSnapshot.child(uid).child(campaignCode).getChildren()) {
                        Certi_Info certi_info = snapshot.getValue(Certi_Info.class);
                        String certiDate = certi_info.getCerti_date().substring(0, 10);
                        if(getTime.equals(certiDate)) {
                            database.getReference("environmentalCampaign").child("MyCampaign").child(uid).child(campaignCode).child("complete").setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    complete.setVisibility(View.VISIBLE);
                                }
                            });
                        } else {
                            database.getReference("environmentalCampaign").child("MyCampaign").child(uid).child(campaignCode).child("complete").setValue(false).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    complete.setVisibility(View.INVISIBLE);
                                }
                            });
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Log.e("todayCerti", String.valueOf(error.toException())); //에러문 출력
            }
        });

        if(complete.getVisibility() == View.VISIBLE) { return true; }
        else { return false; }
    }
}
