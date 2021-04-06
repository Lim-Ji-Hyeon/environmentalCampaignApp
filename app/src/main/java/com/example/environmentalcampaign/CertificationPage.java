package com.example.environmentalcampaign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CertificationPage extends AppCompatActivity {

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

    }
}