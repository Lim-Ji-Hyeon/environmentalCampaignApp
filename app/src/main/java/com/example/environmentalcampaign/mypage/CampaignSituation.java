package com.example.environmentalcampaign.mypage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.environmentalcampaign.MyAdapter;
import com.example.environmentalcampaign.MyCompleteAdapter;
import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.cp_info.MyCampaignItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CampaignSituation extends AppCompatActivity {
    TextView tv_cp_situation, tv_cp_number, tv_avr_rate;
    ImageButton bt_back;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<MyCampaignItem> arrayList;
    private BaseAdapter adapter;
    private ListView listView;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_situation);

        bt_back = (ImageButton)findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        tv_cp_situation = (TextView)findViewById(R.id.tv_cp_situation);
        tv_cp_number = (TextView)findViewById(R.id.tv_cp_number);
        tv_avr_rate = (TextView)findViewById(R.id.tv_avr_rate);
        listView = (ListView)findViewById(R.id.lv_cp_situation);
        arrayList = new ArrayList<>();

        Intent intent = getIntent();
        int intent_number = intent.getIntExtra("intent_number", 0);

        // uid 가져오기
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("environmentalCampaign").child("MyCampaign").child(uid);

        switch(intent_number) {
            case 1:
                tv_cp_situation.setText("진행중 캠페인");

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        arrayList.clear();
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            MyCampaignItem myCampaignItem = snapshot.getValue(MyCampaignItem.class);

                            // 현재시간 가져오기
                            long now = System.currentTimeMillis();
                            Date mDate = new Date(now);
                            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMdd");
                            String today = simpleDate.format(mDate);

                            // 종료날짜 전이라면
                            if(today.compareTo(myCampaignItem.getEndDate()) <= 0) {
                                arrayList.add(myCampaignItem);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                adapter = new MyAdapter(arrayList, this);
                listView.setAdapter(adapter);
//                ListView list_ing_cp;
//                MyAdapter adapter1;
//
//                // Adapter 생성
//                adapter1 = new MyAdapter();
//
//                // 리스트뷰 참조 및 Adapter 달기
//                list_ing_cp = (ListView)findViewById(R.id.lv_cp_situation);
//                list_ing_cp.setAdapter(adapter1);
//
//                adapter1.addItem(100, 2, "버리스타", "2주", "주 2일",  20210501, ContextCompat.getDrawable(this, R.drawable.burista));
                break;
            case 2:
                tv_cp_situation.setText("완료한 캠페인");

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        arrayList.clear();
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            MyCampaignItem myCampaignItem = snapshot.getValue(MyCampaignItem.class);

                            // 현재시간 가져오기
                            long now = System.currentTimeMillis();
                            Date mDate = new Date(now);
                            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMdd");
                            String today = simpleDate.format(mDate);

                            // 종료날짜 후라면
                            if(today.compareTo(myCampaignItem.getEndDate()) > 0) {
                                arrayList.add(myCampaignItem);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                adapter = new MyAdapter(arrayList, this);
                listView.setAdapter(adapter);

//                ListView list_complete_cp;
//                MyCompleteAdapter adapter2;
//
//                adapter2 = new MyCompleteAdapter();
//
//                list_complete_cp = (ListView)findViewById(R.id.lv_cp_situation);
//                list_complete_cp.setAdapter(adapter2);
//
//                adapter2.addItem("버리스타", 100, "주 2일", 2, ContextCompat.getDrawable(this, R.drawable.burista));
                break;
        }

        int sum = 0;
        for(int i = 0; i < arrayList.size(); i++) {
            TextView tv_complete_logo = listView.getChildAt(i).findViewById(R.id.tv_complete_logo);
            tv_complete_logo.setVisibility(View.INVISIBLE);

            TextView tv_achievement_rate = listView.getChildAt(i).findViewById(R.id.tv_achievement_rate);
            String s = tv_achievement_rate.getText().toString();
            int rate = Integer.parseInt(s.substring(7, s.length()-1));
            sum += rate;
        }

        tv_cp_number.setText(arrayList.size() + "개");
        double avg;
        if(arrayList.size() == 0) { avg = 0.0; }
        else { avg = Double.parseDouble(String.format("%.1f", sum / arrayList.size())); }
        tv_avr_rate.setText(avg + "%");

    }
}