package com.example.environmentalcampaign.certification_page;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.home.HomeActivity;

import java.io.ByteArrayOutputStream;

public class Second_Certification_Page extends AppCompatActivity {

    ImageView img1;
    Button btn1;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second__certification__page);

        img1 = (ImageView)findViewById(R.id.certi_image);
        btn1 = (Button)findViewById(R.id.certi_button);
        editText = (EditText)findViewById(R.id.certi_text);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Second_Certification_Page.this, HomeActivity.class);

                // 이미지를 Bitmap 객체로 만들기 + 압축하기
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cp1);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                intent.putExtra("sendData", editText.getText().toString());
                intent.putExtra("image", byteArray);
                startActivity(intent);
            }
        });

    }
}