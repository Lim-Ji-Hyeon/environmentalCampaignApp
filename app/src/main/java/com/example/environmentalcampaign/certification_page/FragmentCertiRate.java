package com.example.environmentalcampaign.certification_page;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.feed.FeedAdapter;
import com.example.environmentalcampaign.feed.FeedItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentCertiRate extends Fragment {

    TextView tv_rateInfo, tv_currentRate, tv_certiSuccess, tv_certiFailure, tv_certiLeft;
    ProgressBar progressBar;
    LinearLayout lo_currentRate, lo_noRate;
    RecyclerView certiRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    int maxRate, rate;
    int success, fail, rest;
    int count; // 총 인증해야 하는 개수
    String campaignCode, uid;

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    ArrayList<FeedItem> photoUrl;

    public FragmentCertiRate() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_certi_rate, container, false);
        tv_rateInfo = (TextView)rootView.findViewById(R.id.tv_rateInfo);
        tv_currentRate = (TextView)rootView.findViewById(R.id.tv_currentRate);
        tv_certiSuccess = (TextView)rootView.findViewById(R.id.tv_certiSuccess);
        tv_certiFailure = (TextView)rootView.findViewById(R.id.tv_certiFailure);
        tv_certiLeft = (TextView)rootView.findViewById(R.id.tv_certiLeft);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
        lo_currentRate = (LinearLayout)rootView.findViewById(R.id.lo_currentRate);
        lo_noRate = (LinearLayout)rootView.findViewById(R.id.lo_noRate);

        Bundle bundle = getArguments();
        if(bundle != null) {
            rate = bundle.getInt("rate");
            count = bundle.getInt("count");
            campaignCode = bundle.getString("campaignCode");
        }

        maxRate = 100; // 나중에 구현해야 함.
        tv_rateInfo.setText(String.valueOf(maxRate));
        tv_currentRate.setText(rate + "%");

        // uid 가져오기
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();

        // progressbar 수정
        progressBar.setProgress(rate);
        progressBar.setSecondaryProgress(maxRate);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)lo_currentRate.getLayoutParams();
        params.weight = rate;
        lo_currentRate.setLayoutParams(params);
        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams)lo_noRate.getLayoutParams();
        params2.weight = 100 - rate;
        lo_noRate.setLayoutParams(params2);
        if(lo_currentRate.getWidth() < 100) {
            lo_currentRate.setGravity(Gravity.LEFT);
            params.width = 100;
            lo_currentRate.setLayoutParams(params);
        }

        // recyclerView 삽입
        certiRecyclerView = (RecyclerView)rootView.findViewById(R.id.certiRecyclerView);
        certiRecyclerView.setHasFixedSize(true); // 성능 향상시키기위함
        layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL){
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                // 이미지 크기 변경
                lp.width = (getWidth() / getSpanCount())-20;
                lp.height = lp.width;
                return true;
            }
        };
        certiRecyclerView.setLayoutManager(layoutManager);

        success = certiSuccess();
        tv_certiFailure.setText("0"); // 나중에 구현햐야 함.

        return rootView;
    }

    int certiSuccess() {
        ArrayList<Certi_Info> arrayList = new ArrayList<>();
        photoUrl = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("environmentalCampaign").child("Certification").child(uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                photoUrl.clear();
                if(dataSnapshot.hasChild(campaignCode)) {
                    for(DataSnapshot snapshot : dataSnapshot.child(campaignCode).getChildren()) {
                        Certi_Info certi_info = snapshot.getValue(Certi_Info.class);
                        arrayList.add(certi_info);
//                        database.getReference("environmentalCampaign").child("Feed").child(certi_info.getCerti_date()).addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                FeedItem feedItem = snapshot.getValue(FeedItem.class);
//                                photoUrl.add(feedItem);
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });
                        FeedItem feedItem = new FeedItem();
                        feedItem.setDate(certi_info.getCerti_date());
                        feedItem.setContents(certi_info.getContents());
                        feedItem.setPublisher(certi_info.getPublisher());
                        feedItem.setImage(certi_info.getPhotoUrl());
                        photoUrl.add(feedItem);
                    }
                    tv_certiSuccess.setText(String.valueOf(arrayList.size()));
                    rest = count - Integer.parseInt(tv_certiSuccess.getText().toString());
                    tv_certiLeft.setText(String.valueOf(rest));

//                    // no_image 붙이기
//                    FeedItem photo = new FeedItem();
//                    photo.setImage("https://firebasestorage.googleapis.com/v0/b/environmental-campaign.appspot.com/o/no_image.jpg?alt=media&token=f0929e4a-f161-4732-9473-fc008f436b0a");
//                    for(int i = 0; i < count-arrayList.size(); i++) {
//                        photoUrl.add(photo);
//                    }

                } else {
                    tv_certiSuccess.setText("0");
                    rest = count - Integer.parseInt(tv_certiSuccess.getText().toString());
                    tv_certiLeft.setText(String.valueOf(rest));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter = new FeedAdapter(photoUrl, getContext());
        certiRecyclerView.setAdapter(adapter);

        return Integer.parseInt(tv_certiSuccess.getText().toString());
    }
}