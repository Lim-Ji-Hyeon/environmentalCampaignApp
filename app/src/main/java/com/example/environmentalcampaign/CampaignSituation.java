package com.example.environmentalcampaign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class CampaignSituation extends AppCompatActivity {
    TextView tv_cp_situation;
    ImageButton bt_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_situation);

        bt_back = (ImageButton)findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tv_cp_situation = (TextView)findViewById(R.id.tv_cp_situation);

        Intent intent = getIntent();

        int intent_number = intent.getIntExtra("intent_number", 0);

        switch(intent_number) {
            case 1:
                tv_cp_situation.setText("진행중 캠페인");
                ListView list_ing_cp;
                MyAdapter adapter1;

                // Adapter 생성
                adapter1 = new MyAdapter();

                // 리스트뷰 참조 및 Adapter 달기
                list_ing_cp = (ListView)findViewById(R.id.lv_cp_situation);
                list_ing_cp.setAdapter(adapter1);

                adapter1.addItem(100, 2, "버리스타", "주 2일", "00:00:00", "24:00:00", 20210501, ContextCompat.getDrawable(this, R.drawable.burista));
                break;
            case 2:
                tv_cp_situation.setText("완료한 캠페인");
                ListView list_complete_cp;
                MyCompleteAdapter adapter2;

                adapter2 = new MyCompleteAdapter();

                list_complete_cp = (ListView)findViewById(R.id.lv_cp_situation);
                list_complete_cp.setAdapter(adapter2);

                adapter2.addItem("버리스타", 100, "주 2일", 2, ContextCompat.getDrawable(this, R.drawable.burista));
                break;
        }

    }
}