package com.environmentalcampaign.certification_page;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.environmentalcampaign.R;

public class PopupActivity extends AppCompatActivity {

    ImageView iv_popup;
    TextView tv_popupInfo;
    Button btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 상태바 제거 (전체화면 모드)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_popup);

        iv_popup = (ImageView)findViewById(R.id.iv_popup);
        tv_popupInfo = (TextView)findViewById(R.id.tv_popupInfo);

        // 내용 불러오기
        Intent gIntent = getIntent();
        byte[] arr = gIntent.getByteArrayExtra("image");
        Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        String info = gIntent.getStringExtra("info");

        // 붙이기
        iv_popup.setImageBitmap(image);
        tv_popupInfo.setText(info);

        btn_ok = (Button)findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 바깥레이어 클릭 시 안닫히게
        if(event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

//    @Override
//    public void onBackPressed() {
//        // 안드로이드 백버튼 막기
//        return;
//    }
}