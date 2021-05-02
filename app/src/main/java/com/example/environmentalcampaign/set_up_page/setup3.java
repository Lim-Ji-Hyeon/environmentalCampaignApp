package com.example.environmentalcampaign.set_up_page;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.example.environmentalcampaign.cp_info.CampaignInformation;

public class setup3 extends AppCompatActivity {

    ImageButton bt_back;
    TextView tv_pre, tv_next;

    Bitmap logo, infoImage1, infoImage2, infoImage3, infoImage4, infoImage5;
    String cp_name, frequency, period, eDate, info;

    EditText et_cp_way, et_rightPhotoInfo, et_rightPhotoInfo2, et_wrongPhotoInfo, et_wrongPhotoInfo2;
    ImageView iv_rightPhoto, iv_rightPhoto2, iv_wrongPhoto, iv_wrongPhoto2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);

        et_cp_way = (EditText)findViewById(R.id.et_cp_way);
        iv_rightPhoto = (ImageView)findViewById(R.id.iv_rightPhoto);
        iv_rightPhoto2 = (ImageView)findViewById(R.id.iv_rightPhoto2);
        et_rightPhotoInfo = (EditText)findViewById(R.id.et_rightPhotoInfo);
        et_rightPhotoInfo2 = (EditText)findViewById(R.id.et_rightPhotoInfo2);
        iv_wrongPhoto = (ImageView)findViewById(R.id.iv_wrongPhoto);
        iv_wrongPhoto2 = (ImageView)findViewById(R.id.iv_wrongPhoto2);
        et_wrongPhotoInfo = (EditText)findViewById(R.id.et_wrongPhotoInfo);
        et_wrongPhotoInfo2 = (EditText)findViewById(R.id.et_wrongPhotoInfo2);

        // 전 페이지 내용들 불러오기
        Intent preIntent = getIntent();
        logo = (Bitmap)preIntent.getExtras().get("logo");
        cp_name = preIntent.getStringExtra("cp_name");
        frequency = preIntent.getStringExtra("frequency");
        period = preIntent.getStringExtra("period");
//        eDate = preIntent.getStringExtra("eDate");
        info = preIntent.getStringExtra("info");
        infoImage1 = (Bitmap)preIntent.getExtras().get("infoImage1");
        infoImage2 = (Bitmap)preIntent.getExtras().get("infoImage2");
        infoImage3 = (Bitmap)preIntent.getExtras().get("infoImage3");
        infoImage4 = (Bitmap)preIntent.getExtras().get("infoImage4");
        infoImage5 = (Bitmap)preIntent.getExtras().get("infoImage5");

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
                setup_alert();
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

    // 마지막 확인 팝업
    void setup_alert() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(setup3.this)
                .setTitle("캠페인 개설")
                .setMessage("\n작성한 내용대로 캠페인을 개설하시겠습니까?")
                .setPositiveButton("개설", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setup();
                        Toast.makeText(setup3.this, "캠페인이 개설되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("취소", null);
        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    // 캠페인 정보 페이지로 intent
    void setup() {
        Intent intent = new Intent(getApplicationContext(), CampaignInformation.class);

        // 전 페이지 내용 그대로 옮겨주기
        intent.putExtra("logo", logo);
        intent.putExtra("cp_name", cp_name);
        intent.putExtra("frequency", frequency);
        intent.putExtra("period", period);
//        intent.putExtra("eDate", eDate);
        intent.putExtra("info", info);
        intent.putExtra("infoImage1", infoImage1);
        intent.putExtra("infoImage2", infoImage2);
        intent.putExtra("infoImage3", infoImage3);
        intent.putExtra("infoImage4", infoImage4);
        intent.putExtra("infoImage5", infoImage5);

        // 이미지 Bitmap 변환
        Bitmap rPhoto1 = BitmapFactory.decodeResource(getResources(), iv_rightPhoto.getId());
        Bitmap rPhoto2 = BitmapFactory.decodeResource(getResources(), iv_rightPhoto2.getId());
        Bitmap wPhoto1 = BitmapFactory.decodeResource(getResources(), iv_wrongPhoto.getId());
        Bitmap wPhoto2 = BitmapFactory.decodeResource(getResources(), iv_wrongPhoto2.getId());

        // 현재 페이지 내용 옮기기
        intent.putExtra("way", et_cp_way.getText().toString());
        intent.putExtra("rPhoto1", rPhoto1);
        intent.putExtra("rPhoto2", rPhoto2);
        intent.putExtra("rInfo", et_rightPhotoInfo.getText().toString());
        intent.putExtra("rInfo2", et_rightPhotoInfo2.getText().toString());
        intent.putExtra("wPhoto1", wPhoto1);
        intent.putExtra("wPhoto2", wPhoto2);
        intent.putExtra("wInfo", et_wrongPhotoInfo.getText().toString());
        intent.putExtra("rInfo2", et_wrongPhotoInfo2.getText().toString());

        // setup이라는 신호주기
        intent.putExtra("signal", "setup");

        startActivity(intent);
    }
}