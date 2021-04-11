package com.example.environmentalcampaign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    TextView tv_search;
    ImageView bookmark;
    TextView tv_make, tv_certi, tv_feed, tv_mypage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
}