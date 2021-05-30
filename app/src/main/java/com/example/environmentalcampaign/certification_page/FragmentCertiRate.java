package com.example.environmentalcampaign.certification_page;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.feed.FeedAdapter;
import com.example.environmentalcampaign.feed.FeedItem;

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
    int count; // 총 인증해야 하는 개수

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
        }

        maxRate = 80; // 나중에 구현해야 함.
        tv_rateInfo.setText(String.valueOf(maxRate));
        tv_currentRate.setText(rate + "%");

        // progressbar 수정
        progressBar.setProgress(rate);
        progressBar.setSecondaryProgress(maxRate);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)lo_currentRate.getLayoutParams();
        params.weight = rate;
        lo_currentRate.setLayoutParams(params);
        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams)lo_noRate.getLayoutParams();
        params2.weight = 100 - rate;
        lo_noRate.setLayoutParams(params2);

        tv_certiSuccess.setText("4"); // 나중에 구현해야 함.
        tv_certiFailure.setText("0"); // 나중에 구현햐야 함.
        tv_certiLeft.setText(String.valueOf(count - Integer.parseInt(tv_certiSuccess.getText().toString())));

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

//        // 리스트 초기화
//        List<FeedItem> photos = new ArrayList<>();
//        for(int i = 0; i < count; i++) {
//            photos.add(new FeedItem(R.drawable.no_image));
//        }
//
//        certiRecyclerView.setAdapter(new FeedAdapter(photos));

        ArrayList<FeedItem> photos = new ArrayList<>();
        // no_image 붙이기
        FeedItem photo = new FeedItem();
        photo.setImage("https://firebasestorage.googleapis.com/v0/b/environmental-campaign.appspot.com/o/no_image.jpg?alt=media&token=f0929e4a-f161-4732-9473-fc008f436b0a");
        for(int i = 0; i < count; i++) {
            photos.add(photo);
        }
        adapter = new FeedAdapter(photos, getContext());
        certiRecyclerView.setAdapter(adapter);

        return rootView;
    }
}