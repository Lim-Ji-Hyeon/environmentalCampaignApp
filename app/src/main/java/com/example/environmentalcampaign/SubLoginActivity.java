package com.example.environmentalcampaign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.environmentalcampaign.home.HomeActivity;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class SubLoginActivity extends AppCompatActivity {

    private String strNick, strProfileImg, strEmail;
    Button btn_logout, btn_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_login);

        Intent intent = getIntent();
        strNick = intent.getStringExtra("name");
        strProfileImg = intent.getStringExtra("profileImg");
        strEmail = intent.getStringExtra("email");

        TextView kakaoEmail = (TextView) findViewById(R.id.kakaoEmail);
        TextView kakaoName = (TextView) findViewById(R.id.kakaoName);
        ImageView kakaoImg = (ImageView) findViewById(R.id.kakaoImg);

        // 이메일 set
        kakaoEmail.setText(strEmail);

        // 닉네임 set
        kakaoName.setText(strNick);

        //프로필 이미지 사진 set
        Glide.with(this).load(strProfileImg).into(kakaoImg);

        btn_logout = (Button)findViewById(R.id.btn_logout);
        btn_check = (Button)findViewById(R.id.btn_check);

        // 로그아웃 처리
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        // 로그아웃 성공시 수행한다,
                        finish(); // 현재 엑티비티를 종료시킨다.
                    }
                });
            }
        });

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubLoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });


    }
}