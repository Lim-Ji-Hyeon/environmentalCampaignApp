package com.example.environmentalcampaign;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView datalist1;
    List<String> titles1;
    List<Integer> images1;
    RecyclerViewAdapter recyclerViewAdapter1;

    RecyclerView datalist2;
    List<String> titles2;
    List<Integer> images2;
    RecyclerViewAdapter recyclerViewAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
    }
}