package com.example.environmentalcampaign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CertificationPage extends AppCompatActivity {

    ArrayList<CpData> cpDataList;

//    ListView listView = (ListView)findViewById(R.id.lv_certi_cp);
//    ListView listView2 = (ListView)findViewById(R.id.lv_complete_cp);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification_page);

//        this.InitializeCpData();
//
//        final MyAdapter myAdapter = new MyAdapter(this, cpDataList);
//
//        listView.setAdapter(myAdapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView parent, View v, int position, long id) {
//                Toast.makeText(getApplicationContext(),
//                        myAdapter.getItem(position).getName(),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
    }

//    public void InitializeCpData() {
//        cpDataList = new ArrayList<CpData>();
//
//        cpDataList.add(new CpData(100, 28, "버리스타", "주 2일", "00:00:00", "24:00:00", "03.01(월)", R.drawable.burista));
//    }
}