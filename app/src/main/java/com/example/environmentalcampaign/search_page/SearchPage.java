package com.example.environmentalcampaign.search_page;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.certification_page.CertificationPage;
import com.example.environmentalcampaign.cp_info.CampaignItem;
import com.example.environmentalcampaign.feed.FeedAdapter;
import com.example.environmentalcampaign.feed.FeedItem;
import com.example.environmentalcampaign.feed.FeedPage;
import com.example.environmentalcampaign.home.HomeActivity;
import com.example.environmentalcampaign.home.RecyclerViewAdapter;
import com.example.environmentalcampaign.home.RecyclerViewItem;
import com.example.environmentalcampaign.mypage.MyPage;
import com.example.environmentalcampaign.set_up_page.SetUpCampaignPage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchPage extends AppCompatActivity implements TextWatcher{

    ImageButton bt_back;
    LinearLayout lo_home, lo_make, lo_certi, lo_feed, lo_mypage;

    private RecyclerView searchCampaign;          // 검색을 보여줄 리스트변수
    private EditText et_search; // 검색어를 입력할 Input 창
    private SearchAdapter adapter;      // 리스트뷰에 연결할 아답터
    private ArrayList<SearchViewItem> arraylist, filteredList;// 데이터를 넣은 리스트변수
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        et_search = findViewById(R.id.et_search);
        searchCampaign = findViewById(R.id.searchCampaign);
        searchCampaign.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(SearchPage.this,1,GridLayoutManager.VERTICAL, false);
        searchCampaign.setLayoutManager(layoutManager);

        // 리스트를 생성한다.
        arraylist = new ArrayList<>();
        filteredList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("environmentalCampaign").child("Search");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arraylist.clear(); // 기존 배열 리스트가 존재하지 않게 초기화
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){ // 반복문으로 데이터 리스트를 추출해냄.
                    SearchViewItem searchViewItem = snapshot.getValue(SearchViewItem.class); //만들어둔 FeedItem 객체를 담는다.
                    arraylist.add(searchViewItem); // 담은 데이터들을 배열 리스트에 넣고 recyclerview에 보낼 준비를 한다.
                }
                adapter.notifyDataSetChanged(); //리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Log.e("SearchPage", String.valueOf(error.toException())); //에러문 출력
            }
        });

        adapter = new SearchAdapter(arraylist, this);
        searchCampaign.setAdapter(adapter); //리사이클러뷰에 어댑터 연결

        et_search.addTextChangedListener(this);


        // 뒤로 가기 버튼 페이지 연동(홈화면과 연동)
        bt_back = (ImageButton)findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        adapter.getFilter().filter(charSequence);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}