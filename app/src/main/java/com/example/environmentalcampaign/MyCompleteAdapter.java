package com.example.environmentalcampaign;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.environmentalcampaign.mypage.CompleteCampaignItem;
import com.example.environmentalcampaign.cp_info.CampaignItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyCompleteAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<CompleteCampaignItem> sample;

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String uid;

    public MyCompleteAdapter(ArrayList<CompleteCampaignItem> sample, Context context){
        this.sample = sample;
        this.context = context;
    }

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

        CompleteCampaignItem item = sample.get(position);

        // uid 가져오기
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("environmentalCampaign").child("Campaign").child(item.getCampaignCode()).child("campaign");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CampaignItem campaignItem = snapshot.getValue(CampaignItem.class);
                CpCompleteData cpCompleteData = new CpCompleteData();

                cpCompleteData.setName(campaignItem.getTitle());
                cpCompleteData.setRate((int)item.getAchievementAvg());
                cpCompleteData.setFrequency(campaignItem.getFrequency());
                cpCompleteData.setReCp(item.getReCount());
                cpCompleteData.setLogo(campaignItem.getLogo());

                // 아이템 내 각 위젯에 데이터 반영
                cpName.setText(cpCompleteData.getName());
                avrRate.setText("평균 달성률 " + cpCompleteData.getRate() + "%");
                frequency.setText(cpCompleteData.getFrequency());
                reCp.setText("누적 " + cpCompleteData.getReCp() + "회 참여");
                Glide.with(context).load(cpCompleteData.getLogo()).into(logo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Log.e("MyCompleteAdapter", String.valueOf(error.toException())); //에러문 출력
            }
        });

        return convertView;
    }

//    public void addItem(String name, int rate, String frequency, int reCp, Drawable logo) {
//        CpCompleteData item = new CpCompleteData();
//
//        item.setName(name);
//        item.setRate(rate);
//        item.setFrequency(frequency);
//        item.setReCp(reCp);
//        item.setLogo(logo);
//
//        sample.add(item);
//    }
}
