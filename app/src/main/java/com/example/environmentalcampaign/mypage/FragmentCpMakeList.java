package com.example.environmentalcampaign.mypage;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.environmentalcampaign.MyAdapter;
import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.cp_info.CampaignInformation;
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

public class FragmentCpMakeList extends Fragment {

    TextView tv_cp_number, tv_avr_rate;
    ListView listView;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<MyCampaignItem> arrayList;
    private BaseAdapter adapter;

    ArrayList<String> campaignCodes;
    String uid;
    double sum = 0, avg;

    public FragmentCpMakeList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_cp_make_list, container, false);
        tv_cp_number = (TextView)rootView.findViewById(R.id.tv_cp_number);
        tv_avr_rate = (TextView)rootView.findViewById(R.id.tv_avr_rate);
        listView = (ListView)rootView.findViewById(R.id.lv_cp_situation);

        // uid 가져오기
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();

        campaignCodes = new ArrayList<>();
        arrayList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("environmentalCampaign").child("MyCampaign");

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
                                    MyCampaignItem myCampaignItem = snapshot.getValue(MyCampaignItem.class);

                                    // 현재시간 가져오기
                                    long now = System.currentTimeMillis();
                                    Date mDate = new Date(now);
                                    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMdd");
                                    String today = simpleDate.format(mDate);

                                    // 종료날짜 전이고, 내가 개설한 캠페인이라면
                                    if((today.compareTo(myCampaignItem.getEndDate()) <= 0)&&(campaignCodes.contains(myCampaignItem.getCampaignCode()))) {
                                        arrayList.add(myCampaignItem);
                                    }
                                }
                            }
                            tv_cp_number.setText(arrayList.size() + "개");
                            for(int i = 0; i < arrayList.size(); i++) {
                                int rate = arrayList.get(i).getCertiCompleteCount()*100/arrayList.get(i).getCertiCount();
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

        adapter = new MyAdapter(arrayList, getContext(), true);
        listView.setAdapter(adapter);

        // 리스트뷰를 클릭하면 캠페인 정보 페이지로 넘어간다.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // campaign information으로 넘어간다.
                Intent intent = new Intent(getContext(), CampaignInformation.class);
                MyCampaignItem item = (MyCampaignItem)adapter.getItem(i);
                intent.putExtra("campaignCode", item.getCampaignCode());
                intent.putExtra("signal", "mypage");
                startActivity(intent);
            }
        });

        return rootView;
    }
}