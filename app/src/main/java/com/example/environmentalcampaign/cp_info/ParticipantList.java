package com.example.environmentalcampaign.cp_info;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.environmentalcampaign.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ParticipantList extends AppCompatActivity {

    ImageButton bt_back;
    TextView tv_participantsN;

    private BaseAdapter adapter;
    private ArrayList<ParticipantItem> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ListView participantListView;

    String campaignCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_list);

        participantListView = findViewById(R.id.lv_participantList);
        tv_participantsN = (TextView)findViewById(R.id.tv_participantsN);

        arrayList = new ArrayList<>(); // ParticipantItem 객체를 담을 ArrayList

        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동

        Intent gIntent = getIntent();
        campaignCode = gIntent.getStringExtra("datetime"); // 캠페인의 생성날짜로 불러올것임.
        databaseReference = database.getReference("environmentalCampaign").child("Campaign").child(campaignCode).child("participants"); // DB 테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear(); // 기존 배열 리스트가 존재하지 않게 초기화
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){ // 반복문으로 데이터 리스트를 추출해냄.
                    ParticipantItem participantItem = snapshot.getValue(ParticipantItem.class); //만들어둔 ParticipantItem 객체를 담는다.
                    arrayList.add(participantItem); // 담은 데이터들을 배열 리스트에 넣고 recyclerview에 보낼 준비를 한다.
                }
                tv_participantsN.setText(String.valueOf(arrayList.size()));
                adapter.notifyDataSetChanged(); //리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Log.e("ParticipantListActivity", String.valueOf(error.toException())); //에러문 출력
            }
        });

        adapter = new ParticipantAdapter(arrayList, this);
        participantListView.setAdapter(adapter); // 리스트뷰에 어댑터 연결

//       ListView lv_participantList;
//        ParticipantAdapter adapter;
//
//        // Adapter 생성
//        adapter = new ParticipantAdapter();
//
//        // 리스트뷰 참조 및 Adapter 달기
//        lv_participantList = (ListView)findViewById(R.id.lv_participantList);
//        lv_participantList.setAdapter(adapter);
//
//        // 아이템 추가
//        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.profile), "녹색아줌마");
//        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.profile), "지구지킴이");
//        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.profile), "으쌰으쌰");

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