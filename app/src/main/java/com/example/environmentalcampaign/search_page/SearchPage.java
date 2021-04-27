package com.example.environmentalcampaign.search_page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.certification_page.CertificationPage;
import com.example.environmentalcampaign.feed.FeedPage;
import com.example.environmentalcampaign.home.HomeActivity;
import com.example.environmentalcampaign.home.RecyclerViewAdapter;
import com.example.environmentalcampaign.mypage.MyPage;
import com.example.environmentalcampaign.set_up_page.SetUpCampaignPage;

import java.util.List;

public class SearchPage extends AppCompatActivity {


    RecyclerView datalist;
    List<String> titles;
    List<Integer> images;
    RecyclerViewAdapter recyclerViewAdapter;

    ImageButton bt_back;
    TextView tv_home, tv_make, tv_certi, tv_feed, tv_mypage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
//        datalist = findViewById(R.id.datalist);
//
//        titles = new ArrayList<>();
//        images = new ArrayList<>();
//
//        titles.add("멸종위기 보호 캠페인");
//        titles.add("토양 정화 캠페인");
//        titles.add("플라스틱 프리 챌린지");
//        titles.add("용기내 캠페인");
//
//        images.add(R.drawable.dolphin);
//        images.add(R.drawable.soil);
//        images.add(R.drawable.new_campaign_2);
//        images.add(R.drawable.campaign_image);
//
//        adapter = new Adapter(this, titles, images);
//
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
//        datalist.setLayoutManager(gridLayoutManager);
//        datalist.setAdapter(adapter);

        // 뒤로 가기 버튼 페이지 연동(홈화면과 연동)
        bt_back = (ImageButton)findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        // 하단 메뉴바 페이지 연동

        tv_home = (TextView)findViewById(R.id.tv_home);
        tv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

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