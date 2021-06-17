package com.example.environmentalcampaign.bookmark;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.environmentalcampaign.certification_page.CertificationPage;
import com.example.environmentalcampaign.feed.FeedAdapter;
import com.example.environmentalcampaign.feed.FeedItem;
import com.example.environmentalcampaign.feed.FeedPage;
import com.example.environmentalcampaign.home.HomeActivity;
import com.example.environmentalcampaign.home.RecyclerViewItem;
import com.example.environmentalcampaign.mypage.MyPage;
import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.home.RecyclerViewAdapter;
import com.example.environmentalcampaign.set_up_page.SetUpCampaignPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BookMark extends AppCompatActivity {

    private RecyclerView stored_campaign;
    ImageButton bt_back;
    TextView tv_home, tv_make, tv_certi, tv_feed, tv_mypage;

    private RecyclerView.Adapter bookmarkrecyclerViewAdapter;
    private  RecyclerView.LayoutManager bLayoutManager;
    public ArrayList<RecyclerViewItem> bookmarkArrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference, realtimeReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        uid = firebaseUser.getUid();

        bookmarkArrayList = new ArrayList<>();

        stored_campaign = findViewById(R.id.stored_campaign);
        stored_campaign.setHasFixedSize(true); // 성능 향상시키기위함
        bLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        stored_campaign.setLayoutManager(bLayoutManager);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("environmentalCampaign").child("BookMark").child(uid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bookmarkArrayList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    RecyclerViewItem recyclerViewItem = snapshot.getValue(RecyclerViewItem.class);
                    bookmarkArrayList.add(recyclerViewItem);
                }
                bookmarkrecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Failed to read value.", error.toException());
            }
        });

        bookmarkrecyclerViewAdapter = new RecyclerViewAdapter(bookmarkArrayList, this);
        stored_campaign.setAdapter(bookmarkrecyclerViewAdapter); //리사이클러뷰에 어댑터 연결


        // 뒤로 가기 버튼 페이지 연동
        bt_back = (ImageButton)findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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