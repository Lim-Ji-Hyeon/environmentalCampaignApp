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
import com.example.environmentalcampaign.cp_info.CampaignItem;
import com.example.environmentalcampaign.set_up_page.SetUpCampaignItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MySetUpCampaignAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<SetUpCampaignItem> sample;

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String uid;

    public  MySetUpCampaignAdapter(ArrayList<SetUpCampaignItem> sample, Context context) {
        this.sample = sample;
        this.context = context;
    }

    @Override
    public int getCount() { return sample.size(); }

    @Override
    public Object getItem(int position) { return sample.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "custom_listview_my_cp" layout을 inflate하여 convertView 참조 획득.
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_listview_my_cp, parent, false);
        }

        // 화면에 표시될 View(layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView tv_cp_name = (TextView)convertView.findViewById(R.id.tv_cp_name);
        TextView tv_frequency = (TextView)convertView.findViewById(R.id.tv_frequency);
        TextView tv_period = (TextView)convertView.findViewById(R.id.tv_period);
        ImageView iv_logo = (ImageView)convertView.findViewById(R.id.iv_logo);

        SetUpCampaignItem item = sample.get(position);

        // uid 가져오기
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("environmentalCampaign").child("Campaign").child(item.getCampaignCode()).child("campaign");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CampaignItem campaignItem = snapshot.getValue(CampaignItem.class);

                tv_cp_name.setText(campaignItem.getTitle());
                tv_frequency.setText(campaignItem.getFrequency());
                tv_period.setText(campaignItem.getPeriod());
                Glide.with(context).load(campaignItem.getLogo()).into(iv_logo);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Log.e("MySetUpCampaignAdapter", String.valueOf(error.toException())); //에러문 출력
            }
        });

        return convertView;
    }
}
