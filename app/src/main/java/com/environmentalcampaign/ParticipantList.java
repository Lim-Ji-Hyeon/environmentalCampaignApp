package com.environmentalcampaign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class ParticipantList extends AppCompatActivity {

    ImageButton bt_back;
    TextView tv_participantsN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_list);

        ListView lv_participantList;
        ParticipantAdapter adapter;

        // Adapter 생성
        adapter = new ParticipantAdapter();

        // 리스트뷰 참조 및 Adapter 달기
        lv_participantList = (ListView)findViewById(R.id.lv_participantList);
        lv_participantList.setAdapter(adapter);

        // 아이템 추가
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.profile), "녹색아줌마");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.profile), "지구지킴이");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.profile), "으쌰으쌰");

        tv_participantsN = (TextView)findViewById(R.id.tv_participantsN);
        tv_participantsN.setText(String.valueOf(adapter.getCount()));

        // 뒤로가기 버튼 이벤트
        bt_back = (ImageButton)findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}