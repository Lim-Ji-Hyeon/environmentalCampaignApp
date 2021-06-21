package com.example.environmentalcampaign.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.environmentalcampaign.certification_page.Certi_Info;
import com.example.environmentalcampaign.certification_page.CertificationPage;
import com.example.environmentalcampaign.cp_info.CampaignItem;
import com.example.environmentalcampaign.feed.FeedAdapter;
import com.example.environmentalcampaign.feed.FeedImageDetailPage;
import com.example.environmentalcampaign.feed.FeedItem;
import com.example.environmentalcampaign.mypage.MyPage;
import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.search_page.SearchPage;
import com.example.environmentalcampaign.set_up_page.SetUpCampaignPage;
import com.example.environmentalcampaign.bookmark.BookMark;
import com.example.environmentalcampaign.feed.FeedPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {

    TextView tv_search;
    ImageView bookmark, realtime_image;
    TextView realtime_text, realtime_publisher, realtime_date;
    LinearLayout lo_home, lo_make, lo_certi, lo_feed, lo_mypage;

    ViewPager2 viewPager2;
    LinearLayout layoutIndicator;

    private RecyclerView.Adapter fAdapter, nAdapter;
    private  RecyclerView.LayoutManager fLayoutManager, nLayoutManager;
    private RecyclerView fRecyclerView, nRecyclerView;
    public ArrayList<RecyclerViewItem> arrayList, fArrayList, nArrayList;
    public ArrayList<FeedItem> realtimeArrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference, realtimeReference;
    
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500; // 작업 실행 전 지연 시간(밀리초)
    final long PERIOD_MS = 5000; // 연속 작업 실행 간격(밀리초)

//    ArrayList<String> campaignCodes;
//    ArrayList<CampaignItem> campaignItems;

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

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            @Override
            public void run() {
                if(currentPage == list.size()+1) { currentPage = 0; }
                viewPager2.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        // RecyclerView 연결

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("environmentalCampaign").child("HomeCampaign");

        arrayList = new ArrayList<>(); // RecyclerViewItem 객체를 담을 ArrayList

        fRecyclerView = findViewById(R.id.famousCampaign);
        fRecyclerView.setHasFixedSize(true); // 성능 향상시키기위함
        fLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        fRecyclerView.setLayoutManager(fLayoutManager);
        fArrayList = new ArrayList<>(); // 인기캠페인 RecyclerViewItem 객체를 담을 ArrayList

        nRecyclerView = findViewById(R.id.newCampaign);
        nRecyclerView.setHasFixedSize(true); // 성능 향상시키기위함
        nLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        nRecyclerView.setLayoutManager(nLayoutManager);
        nArrayList = new ArrayList<>(); // 신규캠페인 RecyclerViewItem 객체를 담을 ArrayList

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    RecyclerViewItem recyclerViewItem = snapshot.getValue(RecyclerViewItem.class);
                    arrayList.add(recyclerViewItem);
                }

                // 내림차순 정렬
                Collections.sort(arrayList, new Comparator<RecyclerViewItem>() {
                    @Override
                    public int compare(RecyclerViewItem recyclerViewItem, RecyclerViewItem t1) {
                        // reCampaignN을 기준으로 내림차순 정렬
                        if(recyclerViewItem.getReCampaignN() < t1.getReCampaignN()) {
                            return 1; // -1로 하면 오름차순
                        } else if(recyclerViewItem.getReCampaignN() == t1.getReCampaignN()) {
                            return 0;
                        } else {
                            return -1; // 1로 하면 오름차순
                        }
                    }
                });
                fArrayList.clear();
                // 인기캠페인 가져오기
                for(int i=0; i < 2; i++) {
                    RecyclerViewItem recyclerViewItem = arrayList.get(i);
                    fArrayList.add(recyclerViewItem);
                }
                fAdapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침

                // 내림차순 정렬
                Collections.sort(arrayList, new Comparator<RecyclerViewItem>() {
                    @Override
                    public int compare(RecyclerViewItem recyclerViewItem, RecyclerViewItem t1) {
                        // campaignCode를 기준으로 내림차순 정렬
                        return t1.getCampaignCode().compareTo(recyclerViewItem.getCampaignCode());
                    }
                });
                nArrayList.clear();
                // 신규캠페인 가져오기
                for(int i=0; i < 2; i++) {
                    RecyclerViewItem recyclerViewItem = arrayList.get(i);
                    nArrayList.add(recyclerViewItem);
                }
                nAdapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Log.e("HomeActivity3", String.valueOf(error.toException())); //에러문 출력
            }
        });

        fAdapter = new RecyclerViewAdapter(fArrayList, this);
        fRecyclerView.setAdapter(fAdapter);

        nAdapter = new RecyclerViewAdapter(nArrayList, this);
        nRecyclerView.setAdapter(nAdapter);

        // 실시간 인증글 가져오기
        realtime_image = findViewById(R.id.realtime_image);
        realtime_text = findViewById(R.id.realtime_text);
        realtime_date = findViewById(R.id.realtime_date);
        realtime_publisher = findViewById(R.id.realtime_publisher);
        realtimeArrayList = new ArrayList<>();

        realtimeReference = database.getReference("environmentalCampaign").child("Feed");

        realtimeReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                realtimeArrayList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FeedItem feedItem = snapshot.getValue(FeedItem.class);
                    realtimeArrayList.add(feedItem);
                }

                // 내림차순 정렬
                Collections.sort(realtimeArrayList, new Comparator<FeedItem>() {
                    @Override
                    public int compare(FeedItem feedItem, FeedItem t1) {
                        return t1.getDate().compareTo(feedItem.getDate());
                    }
                });

                // 신규 피드 가져오기
                for(int i=0; i < 1; i++) {
                    FeedItem feedItem = realtimeArrayList.get(i);
                    realtime_publisher.setText(feedItem.getPublisher().toString());
                    realtime_date.setText(feedItem.getDate().toString());
                    realtime_text.setText(feedItem.getContents().toString());
                    Glide.with(HomeActivity.this).load(feedItem.getImage()).into(realtime_image);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Failed to read value.", error.toException());
            }
        });

        // 이미지를 누르면 디테일 페이지로 넘어간다.
        realtime_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, FeedImageDetailPage.class);
                intent.putExtra("FeedDate", realtime_date.getText().toString());
                intent.putExtra("FeedPublisher", realtime_publisher.getText().toString());
                startActivity(intent);
            }
        });


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

        lo_home = (LinearLayout)findViewById(R.id.lo_home);
        lo_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        lo_make = (LinearLayout)findViewById(R.id.lo_make);
        lo_make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SetUpCampaignPage.class);
                startActivity(intent);
            }
        });

        lo_certi = (LinearLayout)findViewById(R.id.lo_certi);
        lo_certi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CertificationPage.class);
                startActivity(intent);
            }
        });

        lo_feed = (LinearLayout)findViewById(R.id.lo_feed);
        lo_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FeedPage.class);
                startActivity(intent);
            }
        });

        lo_mypage = (LinearLayout) findViewById(R.id.lo_mypage);
        lo_mypage.setOnClickListener(new View.OnClickListener() {
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