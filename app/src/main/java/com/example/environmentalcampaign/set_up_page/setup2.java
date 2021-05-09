package com.example.environmentalcampaign.set_up_page;

import androidx.appcompat.app.AppCompatActivity;

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

import java.io.ByteArrayOutputStream;

public class setup2 extends AppCompatActivity {

    ImageButton bt_back;
    TextView tv_pre, tv_next;

    EditText et_cp_info;
    ImageView iv_cp_info1, iv_cp_info2, iv_cp_info3, iv_cp_info4, iv_cp_info5, checkImage;

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
        checkImage = (ImageView)findViewById(R.id.checkImage);

        iv_cp_info1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // 전 페이지 내용들 불러오기
        Intent preIntent = getIntent();
        byte[] arr = preIntent.getByteArrayExtra("logo");
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
                if(checkEditText(et_cp_info)) {
                    Toast.makeText(setup2.this, "캠페인 설명을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), setup3.class);

                    // 전 페이지 내용 그대로 옮겨주기
                    intent.putExtra("logo", arr);
                    intent.putExtra("cp_name", cp_name);
                    intent.putExtra("frequency", frequency);
                    intent.putExtra("period", period);
//                intent.putExtra("eDate", eDate);

                    // 이미지 Bitmap 변환
                    byte[] byteArray1 = bitmapToByteArray(iv_cp_info1);
                    byte[] byteArray2 = bitmapToByteArray(iv_cp_info2);
                    byte[] byteArray3 = bitmapToByteArray(iv_cp_info3);
                    byte[] byteArray4 = bitmapToByteArray(iv_cp_info4);
                    byte[] byteArray5 = bitmapToByteArray(iv_cp_info5);
                    byte[] checkbyte = bitmapToByteArray(checkImage);

                    // 현재 페이지 내용 옮기기
                    intent.putExtra("info", et_cp_info.getText().toString());
                    intent.putExtra("infoImage1", byteArray1);
                    intent.putExtra("infoImage2", byteArray2);
                    intent.putExtra("infoImage3", byteArray3);
                    intent.putExtra("infoImage4", byteArray4);
                    intent.putExtra("infoImage5", byteArray5);
                    intent.putExtra("checkImage", checkbyte);

                    startActivity(intent);
                }
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

    // edittext를 입력했는지 확인
    boolean checkEditText(EditText editText) {
        return editText.getText().toString().equals("") || editText.getText().toString()==null;
    }

    // imageView에서 bitmap을 byte[]로 변환
    public byte[] bitmapToByteArray(ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable)imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}