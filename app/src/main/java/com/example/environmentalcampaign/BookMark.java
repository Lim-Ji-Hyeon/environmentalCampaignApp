package com.example.environmentalcampaign;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BookMark extends AppCompatActivity {

    RecyclerView datalist;
    List<String> titles;
    List<Integer> images;
    RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);
        datalist = findViewById(R.id.datalist);

        titles = new ArrayList<>();
        images = new ArrayList<>();

        titles.add("버리스타 캠페인");
        titles.add("페트라떼 캠페인");

        images.add(R.drawable.burista);
        images.add(R.drawable.new_campaign_1);

        recyclerViewAdapter = new RecyclerViewAdapter(this, titles, images);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        datalist.setLayoutManager(gridLayoutManager);
        datalist.setAdapter(recyclerViewAdapter);
    }
}