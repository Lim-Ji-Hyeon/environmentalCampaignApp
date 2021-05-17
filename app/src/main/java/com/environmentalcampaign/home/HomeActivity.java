package com.environmentalcampaign.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.environmentalcampaign.certification_page.CertificationPage;
import com.environmentalcampaign.mypage.MyPage;
import com.environmentalcampaign.R;
import com.environmentalcampaign.search_page.SearchPage;
import com.environmentalcampaign.set_up_page.SetUpCampaignPage;
import com.environmentalcampaign.bookmark.BookMark;
import com.environmentalcampaign.feed.FeedPage;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    TextView tv_search;
    ImageView bookmark;
    TextView tv_make, tv_certi, tv_feed, tv_mypage;

    ViewPager2 viewPager2;
    LinearLayout layoutIndicator;

   // TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPager2 = findViewById(R.id.viewPager2);
        layoutIndicator = findViewById(R.id.layoutIndicators);

        ArrayList<DataPage> list = new ArrayList<>();
        list.add(new DataPage(ContextCompat.getDrawable(this, R.drawable.tumbler_campaign)));
        list.add(new DataPage(ContextCompat.getDrawable(this, R.drawable.zero_waste)));
        list.add(new DataPage(ContextCompat.getDrawable(this, R.drawable.world_eco_day)));

        viewPager2.setAdapter(new ViewPagerAdapter(list));
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });
        setupIndicators(list.size());


        // 인기 캠페인, 신규 캠페인 recyclerview
        RecyclerView famousCampaign = findViewById(R.id.famousCampaign);
        List<String> ftitle = new ArrayList<>();
        List<Integer> fimage = new ArrayList<>();
        RecyclerViewAdapter fRecyclerViewAdapter = new RecyclerViewAdapter(this, ftitle, fimage);

        RecyclerView newCampaign = findViewById(R.id.newCampaign);
        List<String> ntitle = new ArrayList<>();
        List<Integer> nimage = new ArrayList<>();
        RecyclerViewAdapter nRecyclerViewAdapter = new RecyclerViewAdapter(this, ntitle, nimage);

        ftitle.add("버리스타 캠페인");
        ftitle.add("용기내 캠페인");
        fimage.add(R.drawable.burista_1);
        fimage.add(R.drawable.campaign_image);

        ntitle.add("페트라떼 캠페인");
        ntitle.add("플라스틱 프리 챌린지");

        nimage.add(R.drawable.new_campaign_1);
        nimage.add(R.drawable.new_campaign_2);

        GridLayoutManager fGridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        GridLayoutManager nGridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        famousCampaign.setLayoutManager(fGridLayoutManager);
        newCampaign.setLayoutManager(nGridLayoutManager);

        famousCampaign.setAdapter(fRecyclerViewAdapter);
        newCampaign.setAdapter(nRecyclerViewAdapter);


        Intent intent = getIntent();

        // Second_Certification_Page에서 보내온 신호를 가지고 있으면 수행한다.
        if (intent.hasExtra("sendData") && intent.hasExtra("image_path")){
            // 호출할 인텐트가 보내온 이미지와 메시지 얻어오기

            //byte[] arr = intent.getByteArrayExtra("image");
            Bitmap bitmap = BitmapFactory.decodeFile(intent.getStringExtra("image_path"));
            String msg = (String)intent.getExtras().get("sendData");

            // 전달 되어온 정보를 뷰에 넣기
            ImageView BigImage = (ImageView)findViewById(R.id.realtime_image);
            BigImage.setImageBitmap(bitmap);
            TextView txt = (TextView)findViewById(R.id.realtime_text);
            txt.setText(msg);
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

    // cardnews indicator
    private void setupIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 8, 16, 8);

        for(int i=0; i < indicators.length; i++) {
            indicators[i] = new ImageView(this);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.bg_indicator_inactive));
            indicators[i].setLayoutParams(params);
            layoutIndicator.addView(indicators[i]);
        }
        setCurrentIndicator(0);
    }

    private void setCurrentIndicator(int position) {
        int childCount = layoutIndicator.getChildCount();
        for(int i=0; i < childCount; i++) {
            ImageView imageView = (ImageView)layoutIndicator.getChildAt(i);
            if(i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.bg_indicator_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.bg_indicator_inactive));
            }
        }
    }
}