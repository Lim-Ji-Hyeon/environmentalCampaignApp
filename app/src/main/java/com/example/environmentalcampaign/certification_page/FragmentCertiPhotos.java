package com.example.environmentalcampaign.certification_page;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.cp_info.ParticipantItem;
import com.example.environmentalcampaign.feed.FeedAdapter;
import com.example.environmentalcampaign.feed.FeedItem;
import com.example.environmentalcampaign.home.RecyclerViewItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentCertiPhotos extends Fragment {

    String campaignCode;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    private RecyclerView.Adapter adapter;
    private ArrayList<FeedItem> feedItems;

    public FragmentCertiPhotos() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_certi_photos, container, false);

        Bundle bundle = getArguments();
        if(bundle != null) {
            campaignCode = bundle.getString("campaignCode");
        }

        RecyclerView certiRecyclerView2 = (RecyclerView)rootView.findViewById(R.id.certiRecyclerView2);
        certiRecyclerView2.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL){
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                // 이미지 크기 변경
                lp.width = (getWidth() / getSpanCount())-20;
                lp.height = lp.width;
                return true;
            }
        };
        certiRecyclerView2.setLayoutManager(layoutManager);

        feedItems = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("environmentalCampaign").child("Campaign").child(campaignCode).child("certifications");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                feedItems.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FeedItem feedItem = snapshot.getValue(FeedItem.class);
                    feedItems.add(feedItem);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter = new FeedAdapter(feedItems, getContext());
        certiRecyclerView2.setAdapter(adapter);

//        // 이미지 넣음
//        List<FeedItem> certiPhotos = new ArrayList<>();
//        certiPhotos.add(new FeedItem(R.drawable.cp1));
//        certiPhotos.add(new FeedItem(R.drawable.cp2));
//        certiPhotos.add(new FeedItem(R.drawable.cp3));
//        certiPhotos.add(new FeedItem(R.drawable.cp4));
//        certiPhotos.add(new FeedItem(R.drawable.cp5));
//        certiPhotos.add(new FeedItem(R.drawable.cp6));
//
//        certiRecyclerView2.setAdapter(new FeedAdapter(certiPhotos));

        return rootView;
    }
}