package com.example.environmentalcampaign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class Second_Certification_Page extends AppCompatActivity {

    EditText edt1;
    ImageView img1;
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second__certification__page);

        edt1 = (EditText)findViewById(R.id.certi_text);
        img1 = (ImageView)findViewById(R.id.certi_image);
        btn1 = (Button)findViewById(R.id.certi_button);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // EditText에서 글자 가져와서 저장한다.
                String str = edt1.getText().toString();

                // 이미지를 Bitmap 객체로 만들기 + 압축하기
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cp1);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                //다른 액티비티를 호출하기 위한 인텐트 생성하기
                Intent intent = new Intent(Second_Certification_Page.this, HomeActivity.class);

                // 액션이름으로 호출하기(암시적 인텐트)
                intent.setAction("kr.co.hta.MyAction");

                //호출되는 액션에 보낼 부가정보 담기
                intent.putExtra("msg", str);
                intent.putExtra("image",byteArray);

                // 글 작성 후 인증하기 누르면 홈 화면으로 간다.
                startActivity(intent);
            }
        });

    }
}