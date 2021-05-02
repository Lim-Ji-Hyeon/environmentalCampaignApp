package com.example.environmentalcampaign.set_up_page;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.cp_info.CampaignInformation;

import java.io.ByteArrayOutputStream;

public class setup3 extends AppCompatActivity {

    ImageButton bt_back;
    TextView tv_pre, tv_next;

    byte[] logo, infoImage1, infoImage2, infoImage3, infoImage4, infoImage5;
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
        logo = preIntent.getByteArrayExtra("logo");
        cp_name = preIntent.getStringExtra("cp_name");
        frequency = preIntent.getStringExtra("frequency");
        period = preIntent.getStringExtra("period");
//        eDate = preIntent.getStringExtra("eDate");
        info = preIntent.getStringExtra("info");

        infoImage1 = preIntent.getByteArrayExtra("infoImage1");
        infoImage2 = preIntent.getByteArrayExtra("infoImage2");
        infoImage3 = preIntent.getByteArrayExtra("infoImage3");
        infoImage4 = preIntent.getByteArrayExtra("infoImage4");
        infoImage5 = preIntent.getByteArrayExtra("infoImage5");

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
        BitmapDrawable drawable1 = (BitmapDrawable)iv_rightPhoto.getDrawable();
        BitmapDrawable drawable2 = (BitmapDrawable)iv_rightPhoto2.getDrawable();
        BitmapDrawable drawable3 = (BitmapDrawable)iv_wrongPhoto.getDrawable();
        BitmapDrawable drawable4 = (BitmapDrawable)iv_wrongPhoto2.getDrawable();

        Bitmap bitmap1 = drawable1.getBitmap();
        Bitmap bitmap2 = drawable2.getBitmap();
        Bitmap bitmap3 = drawable3.getBitmap();
        Bitmap bitmap4 = drawable4.getBitmap();

        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
        ByteArrayOutputStream stream3 = new ByteArrayOutputStream();
        ByteArrayOutputStream stream4 = new ByteArrayOutputStream();

        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, stream1);
        bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, stream2);
        bitmap3.compress(Bitmap.CompressFormat.JPEG, 100, stream3);
        bitmap4.compress(Bitmap.CompressFormat.JPEG, 100, stream4);

        byte[] byteArray1 = stream1.toByteArray();
        byte[] byteArray2 = stream2.toByteArray();
        byte[] byteArray3 = stream3.toByteArray();
        byte[] byteArray4 = stream4.toByteArray();

        // 현재 페이지 내용 옮기기
        intent.putExtra("way", et_cp_way.getText().toString());
        intent.putExtra("rPhoto1", byteArray1);
        intent.putExtra("rPhoto2", byteArray2);
        intent.putExtra("rInfo", et_rightPhotoInfo.getText().toString());
        intent.putExtra("rInfo2", et_rightPhotoInfo2.getText().toString());
        intent.putExtra("wPhoto1", byteArray3);
        intent.putExtra("wPhoto2", byteArray4);
        intent.putExtra("wInfo", et_wrongPhotoInfo.getText().toString());
        intent.putExtra("rInfo2", et_wrongPhotoInfo2.getText().toString());

        // setup이라는 신호주기
        intent.putExtra("signal", "setup");

        startActivity(intent);
    }
}