package com.example.environmentalcampaign.search_page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.certification_page.CertificationPage;
import com.example.environmentalcampaign.feed.FeedPage;
import com.example.environmentalcampaign.home.HomeActivity;
import com.example.environmentalcampaign.home.RecyclerViewAdapter;
import com.example.environmentalcampaign.mypage.MyPage;
import com.example.environmentalcampaign.set_up_page.SetUpCampaignPage;

import java.util.ArrayList;
import java.util.List;

public class SearchPage extends AppCompatActivity {

    ImageButton bt_back;
    LinearLayout lo_home, lo_make, lo_certi, lo_feed, lo_mypage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

//        // recyclerview
//        RecyclerView searchCampaign = findViewById(R.id.searchCampaign);
//        List<String> stitle = new ArrayList<>();
//        List<Integer> simage = new ArrayList<>();
//        RecyclerViewAdapter sRecyclerViewAdapter = new RecyclerViewAdapter(this, stitle, simage);
//
//        stitle.add("멸종위기 보호 캠페인");
//        stitle.add("토양 정화 캠페인");
//        stitle.add("플라스틱 프리 챌린지");
//        stitle.add("용기내 캠페인");
//
//        simage.add(R.drawable.dolphin);
//        simage.add(R.drawable.soil);
//        simage.add(R.drawable.new_campaign_2);
//        simage.add(R.drawable.campaign_image);
//
//        GridLayoutManager sGridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
//        searchCampaign.setLayoutManager(sGridLayoutManager);
//        searchCampaign.setAdapter(sRecyclerViewAdapter);

        // 뒤로 가기 버튼 페이지 연동(홈화면과 연동)
        bt_back = (ImageButton)findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // 하단 메뉴바 페이지 연동

        lo_home = (LinearLayout)findViewById(R.id.lo_home);
        lo_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        lo_make = (LinearLayout)findViewById(R.id.lo_make);
        lo_make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SetUpCampaignPage.class);
                startActivity(intent);
            }
        });

        lo_certi = (LinearLayout)findViewById(R.id.lo_certi);
        lo_certi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CertificationPage.class);
                startActivity(intent);
            }
        });

        lo_feed = (LinearLayout)findViewById(R.id.lo_feed);
        lo_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FeedPage.class);
                startActivity(intent);
            }
        });

        lo_mypage = (LinearLayout) findViewById(R.id.lo_mypage);
        lo_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPage.class);
                startActivity(intent);
            }
        });
    }
}