package com.example.environmentalcampaign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CertificationPage extends AppCompatActivity {

    TextView tv_home, tv_make, tv_certi, tv_feed, tv_mypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification_page);

        ListView listView, listView2;
        MyAdapter adapter, adapter2;

        // Adapter 생성
        adapter = new MyAdapter();
        adapter2 = new MyAdapter();

        // 리스트뷰 참조 및 Adapter 달기
        listView = (ListView)findViewById(R.id.lv_certi_cp);
        listView.setAdapter(adapter);
        listView2 = (ListView)findViewById(R.id.lv_complete_cp);
        listView2.setAdapter(adapter2);

        // 첫 번째 아이템 추가
        adapter.addItem(100, 2, "버리스타", "주 2일", "00:00:00", "24:00:00", 20210501, ContextCompat.getDrawable(this, R.drawable.burista));
        adapter2.addItem(95, 1, "용기내", "주 5일", "09:00:00", "18:00:00", 20210430, ContextCompat.getDrawable(this, R.drawable.cp1));


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