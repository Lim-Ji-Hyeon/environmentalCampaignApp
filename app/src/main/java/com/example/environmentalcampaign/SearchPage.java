package com.example.environmentalcampaign;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchPage extends AppCompatActivity {


    RecyclerView datalist;
    List<String> titles;
    List<Integer> images;
    RecyclerViewAdapter recyclerViewAdapter;

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
    }
}