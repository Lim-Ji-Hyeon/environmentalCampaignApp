package com.example.environmentalcampaign;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    TextView tv_search;
    ImageView bookmark;
    TextView tv_make, tv_certi, tv_feed, tv_mypage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RecyclerView datalist1;
        List<String> titles1;
        List<Integer> images1;
        RecyclerViewAdapter recyclerViewAdapter1;

        RecyclerView datalist2;
        List<String> titles2;
        List<Integer> images2;
        RecyclerViewAdapter recyclerViewAdapter2;


        // 인증하기 버튼을 누른 후 실시간 게시글에 띄워주기
        Intent intent = getIntent();

        if(intent.getAction().equals("kr.co.hta.MyAction")){
            // 호출할 인텐트가 보내온 이미지와 메시지 얻어오기
            //Bitmap bitmap = (Bitmap)intent.getExtras().get("img");
            String msg = (String)intent.getExtras().get("msg");
            byte[] arr = getIntent().getByteArrayExtra("image");
            Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);




            // 전달 되어온 정보를 뷰에 넣기
            TextView txt = (TextView)findViewById(R.id.realtime_text);
            txt.setText(msg);
            //ImageView img = (ImageView)findViewById(R.id.realtime_image);
            //img.setImageBitmap(bitmap);
            ImageView BigImage = (ImageView)findViewById(R.id.realtime_image);
            BigImage.setImageBitmap(image);
        }


        // 검색 페이지 연동
        tv_search = (TextView) findViewById(R.id.tv_search);
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchPage.class);
                startActivity(intent);
            }
        });

        // 북마크 페이지 연동

        bookmark = (ImageView)findViewById(R.id.bookmark);
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BookMark.class);
                startActivity(intent);
            }
        });

        // 하단 메뉴바 페이지 연동

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