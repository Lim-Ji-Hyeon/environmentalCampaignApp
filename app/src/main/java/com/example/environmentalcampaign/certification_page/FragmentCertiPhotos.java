package com.example.environmentalcampaign.certification_page;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.feed.FeedAdapter;
import com.example.environmentalcampaign.feed.FeedItem;

import java.util.ArrayList;
import java.util.List;

public class FragmentCertiPhotos extends Fragment {

    public FragmentCertiPhotos() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_certi_photos, container, false);

        RecyclerView certiRecyclerView2 = (RecyclerView)rootView.findViewById(R.id.certiRecyclerView2);
        certiRecyclerView2.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

        // 이미지 넣음
        List<FeedItem> certiPhotos = new ArrayList<>();
        certiPhotos.add(new FeedItem(R.drawable.cp1));
        certiPhotos.add(new FeedItem(R.drawable.cp2));
        certiPhotos.add(new FeedItem(R.drawable.cp3));
        certiPhotos.add(new FeedItem(R.drawable.cp4));
        certiPhotos.add(new FeedItem(R.drawable.cp5));
        certiPhotos.add(new FeedItem(R.drawable.cp6));

        certiRecyclerView2.setAdapter(new FeedAdapter(certiPhotos));

        return rootView;
    }
}