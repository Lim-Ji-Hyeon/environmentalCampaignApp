package com.example.environmentalcampaign.mypage;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.environmentalcampaign.MyCompleteAdapter;
import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.cp_info.MyCampaignItem;
import com.example.environmentalcampaign.set_up_page.SetUpCampaignItem;
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

public class FragmentCpMakeList2 extends Fragment {

    TextView tv_cp_number, tv_avr_rate;
    ListView listView;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<CompleteCampaignItem> arrayList;
    private BaseAdapter adapter;

    ArrayList<String> campaignCodes;
    String uid;
    double sum = 0, avg;

    public FragmentCpMakeList2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_cp_make_list2, container, false);
        tv_cp_number = (TextView)rootView.findViewById(R.id.tv_cp_number);
        tv_avr_rate = (TextView)rootView.findViewById(R.id.tv_avr_rate);
        listView = (ListView)rootView.findViewById(R.id.lv_cp_situation);

        // uid 가져오기
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();

        campaignCodes = new ArrayList<>();
        arrayList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("environmentalCampaign").child("CompleteCampaign");

        database.getReference("environmentalCampaign").child("SetUpCampaign").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                campaignCodes.clear();
                if(dataSnapshot.hasChild(uid)) {
                    // 내가 개설한 캠페인의 campaignCode들 가져오기
                    for(DataSnapshot snapshot : dataSnapshot.child(uid).getChildren()) {
                        SetUpCampaignItem setUpCampaignItem = snapshot.getValue(SetUpCampaignItem.class);
                        campaignCodes.add(setUpCampaignItem.getCampaignCode());
                    }

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            arrayList.clear();
                            if(dataSnapshot.hasChild(uid)) {
                                for(DataSnapshot snapshot : dataSnapshot.child(uid).getChildren()) {
                                    CompleteCampaignItem completeCampaignItem = snapshot.getValue(CompleteCampaignItem.class);

                                    // 종료날짜 전이고, 내가 개설한 캠페인이라면
                                    if(campaignCodes.contains(completeCampaignItem.getCampaignCode())) {
                                        arrayList.add(completeCampaignItem);
                                    }
                                }
                            }
                            tv_cp_number.setText(arrayList.size() + "개");
                            for(int i = 0; i < arrayList.size(); i++) {
                                double rate = arrayList.get(i).getAchievementAvg();
                                sum += (double)rate;
                            }

                            if(arrayList.size() == 0) { avg = 0.0; }
                            else { avg = Double.parseDouble(String.format("%.1f", sum / arrayList.size())); }
                            tv_avr_rate.setText(avg + "%");
                            adapter.notifyDataSetChanged();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter = new MyCompleteAdapter(arrayList, getContext());
        listView.setAdapter(adapter);
        return rootView;
    }
}