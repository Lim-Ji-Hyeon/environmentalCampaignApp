package com.example.environmentalcampaign.mypage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.environmentalcampaign.home.LoginActivity;
import com.example.environmentalcampaign.certification_page.CertificationPage;
import com.example.environmentalcampaign.CpMakelist;
import com.example.environmentalcampaign.pointmarket.PointMarket;
import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.feed.FeedPage;
import com.example.environmentalcampaign.home.HomeActivity;
import com.example.environmentalcampaign.set_up_page.SetUpCampaignPage;
import com.google.firebase.auth.FirebaseAuth;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class MyPage extends AppCompatActivity {

    LinearLayout lo_point, lo_cp_ing, lo_cp_complete, lo_cp_make;
    TextView tv_home, tv_make, tv_certi, tv_feed, tv_mypage;
    Button btn_logout;

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        mFirebaseAuth = FirebaseAuth.getInstance();

        // 캠페인 현황 페이지 연결
        lo_cp_ing = (LinearLayout)findViewById(R.id.lo_cp_ing);
        lo_cp_ing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CampaignSituation.class);
                intent.putExtra("intent_number", 1);
                startActivity(intent);
            }
        });

        lo_cp_complete = (LinearLayout)findViewById(R.id.lo_cp_complete);
        lo_cp_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CampaignSituation.class);
                intent.putExtra("intent_number", 2);
                startActivity(intent);
            }
        });

        lo_cp_make = (LinearLayout)findViewById(R.id.lo_cp_make);
        lo_cp_make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CpMakelist.class);
                startActivity(intent);
            }
        });

        // 보유 포인트를 눌렀을 때 포인트 마켓으로 이동한다.
        lo_point = (LinearLayout)findViewById(R.id.lo_point);
        lo_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PointMarket.class);
                startActivity(intent);
            }
        });

        btn_logout = (Button)findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        // 로그아웃 성공시 수행한다,
                        mFirebaseAuth.signOut();
                        Toast.makeText(MyPage.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MyPage.this, LoginActivity.class);
                        startActivity(intent);
                        finish(); // 현재 엑티비티를 종료시킨다.
                    }
                });
            }
        });

        // 하단 메뉴바 페이지 연동

        tv_home = (TextView)findViewById(R.id.tv_home);
        tv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        tv_make = (TextView)findViewById(R.id.tv_make);
        tv_make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SetUpCampaignPage.class);
                startActivity(intent);
            }
        });

        tv_certi = (TextView)findViewById(R.id.tv_certi);
        tv_certi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CertificationPage.class);
                startActivity(intent);
            }
        });

        tv_feed = (TextView)findViewById(R.id.tv_feed);
        tv_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FeedPage.class);
                startActivity(intent);
            }
        });

        tv_mypage = (TextView)findViewById(R.id.tv_mypage);
        tv_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPage.class);
                startActivity(intent);
            }
        });
    }
}