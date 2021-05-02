package com.example.environmentalcampaign.set_up_page;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.environmentalcampaign.R;

public class setup2 extends AppCompatActivity {

    ImageButton bt_back;
    TextView tv_pre, tv_next;

    EditText et_cp_info;
    ImageView iv_cp_info1, iv_cp_info2, iv_cp_info3, iv_cp_info4, iv_cp_info5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);

        et_cp_info = (EditText)findViewById(R.id.et_cp_info);
        iv_cp_info1 = (ImageView)findViewById(R.id.iv_cp_info1);
        iv_cp_info2 = (ImageView)findViewById(R.id.iv_cp_info2);
        iv_cp_info3 = (ImageView)findViewById(R.id.iv_cp_info3);
        iv_cp_info4 = (ImageView)findViewById(R.id.iv_cp_info4);
        iv_cp_info5 = (ImageView)findViewById(R.id.iv_cp_info5);

        // 전 페이지 내용들 불러오기
        Intent preIntent = getIntent();
//        Bitmap logo = (Bitmap)preIntent.getParcelableExtra("logo");
        Bitmap logo = (Bitmap)preIntent.getExtras().get("logo");
//        byte[] arr = preIntent.getByteArrayExtra("logo");
//        Bitmap logo = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        String cp_name = preIntent.getStringExtra("cp_name");
        String frequency = preIntent.getStringExtra("frequency");
        String period = preIntent.getStringExtra("period");
//        String eDate = preIntent.getStringExtra("eDate");

        // 이전 페이지
        tv_pre = (TextView)findViewById(R.id.tv_pre);
        tv_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { onBackPressed(); }
        });

        // 다음 페이지
        tv_next = (TextView)findViewById(R.id.tv_next);
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), setup3.class);

                // 전 페이지 내용 그대로 옮겨주기
                intent.putExtra("logo", logo);
                intent.putExtra("cp_name", cp_name);
                intent.putExtra("frequency", frequency);
                intent.putExtra("period", period);
//                intent.putExtra("eDate", eDate);

                // 이미지 Bitmap 변환
                Bitmap infoImage1 = BitmapFactory.decodeResource(getResources(), iv_cp_info1.getId());
                Bitmap infoImage2 = BitmapFactory.decodeResource(getResources(), iv_cp_info2.getId());
                Bitmap infoImage3 = BitmapFactory.decodeResource(getResources(), iv_cp_info3.getId());
                Bitmap infoImage4 = BitmapFactory.decodeResource(getResources(), iv_cp_info4.getId());
                Bitmap infoImage5 = BitmapFactory.decodeResource(getResources(), iv_cp_info5.getId());

                // 현재 페이지 내용 옮기기
                intent.putExtra("info", et_cp_info.getText().toString());
                intent.putExtra("infoImage1", infoImage1);
                intent.putExtra("infoImage2", infoImage2);
                intent.putExtra("infoImage3", infoImage3);
                intent.putExtra("infoImage4", infoImage4);
                intent.putExtra("infoImage5", infoImage5);

                startActivity(intent);
            }
        });

        // 뒤로가기 버튼 이벤트
        bt_back = (ImageButton)findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SetUpCampaignPage.class);
                startActivity(intent);
            }
        });
    }
}