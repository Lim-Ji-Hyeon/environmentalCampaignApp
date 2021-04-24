package com.example.environmentalcampaign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    TextView tv_search;
    ImageView bookmark;
    TextView tv_make, tv_certi, tv_feed, tv_mypage;

    ViewPager2 viewPager2;
    LinearLayout layoutIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPager2 = findViewById(R.id.viewPager2);
        layoutIndicator = findViewById(R.id.layoutIndicators);

        ArrayList<DataPage> list = new ArrayList<>();
        list.add(new DataPage(ContextCompat.getDrawable(this, R.drawable.tumbler_campaign)));
        list.add(new DataPage(ContextCompat.getDrawable(this, R.drawable.zero_waste)));
        list.add(new DataPage(ContextCompat.getDrawable(this, R.drawable.world_eco_day)));

        viewPager2.setAdapter(new ViewPagerAdapter(list));
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });
        setupIndicators(list.size());

        RecyclerView datalist1;
        List<String> titles1;
        List<Integer> images1;
        RecyclerViewAdapter recyclerViewAdapter1;

        RecyclerView datalist2;
        List<String> titles2;
        List<Integer> images2;
        RecyclerViewAdapter recyclerViewAdapter2;

//        datalist1 = findViewById(R.id.datalist1);
//        //datalist2 = findViewById(R.id.datalist2);
//
//
//        titles1 = new ArrayList<>();
//        images1 = new ArrayList<>();
////        titles2 = new ArrayList<>();
////        images2 = new ArrayList<>();
//
//        titles1.add("버리스타 캠페인");
//        titles1.add("용기내 캠페인");
////        titles2.add("멸종위기 보호 캠페인");
////        titles2.add("토양 정화 캠페인");
//
//        images1.add(R.drawable.burista_1);
//        images1.add(R.drawable.campaign_image);
////        images2.add(R.drawable.dolphin);
////        images2.add(R.drawable.soil);
//
//        adapter1 = new Adapter(this, titles1, images1);
//     //   adapter2 = new Adapter(this, titles2, images2);
//
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
//        datalist1.setLayoutManager(gridLayoutManager);
//        datalist1.setAdapter(adapter1);
////        datalist2.setLayoutManager(gridLayoutManager);
////        datalist2.setAdapter(adapter2);

        // 검색 페이지 연동
        tv_search = (TextView) findViewById(R.id.tv_search);
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchPage.class);
                startActivity(intent);
            }
        });

        // 북마크 페이지 연동

        bookmark = (ImageView)findViewById(R.id.bookmark);
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BookMark.class);
                startActivity(intent);
            }
        });

        // 하단 메뉴바 페이지 연동

        tv_make = (TextView)findViewById(R.id.tv_make);
        tv_make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SetUpCampaignPage.class);
                startActivity(intent);
            }
        });

        tv_certi = (TextView)findViewById(R.id.tv_certi);
        tv_certi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CertificationPage.class);
                startActivity(intent);
            }
        });

        tv_feed = (TextView)findViewById(R.id.tv_feed);
        tv_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FeedPage.class);
                startActivity(intent);
            }
        });

        tv_mypage = (TextView)findViewById(R.id.tv_mypage);
        tv_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPage.class);
                startActivity(intent);
            }
        });
    }

    // cardnews indicator
    private void setupIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 8, 16, 8);

        for(int i=0; i < indicators.length; i++) {
            indicators[i] = new ImageView(this);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.bg_indicator_inactive));
            indicators[i].setLayoutParams(params);
            layoutIndicator.addView(indicators[i]);
        }
        setCurrentIndicator(0);
    }

    private void setCurrentIndicator(int position) {
        int childCount = layoutIndicator.getChildCount();
        for(int i=0; i < childCount; i++) {
            ImageView imageView = (ImageView)layoutIndicator.getChildAt(i);
            if(i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.bg_indicator_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.bg_indicator_inactive));
            }
        }
    }
}