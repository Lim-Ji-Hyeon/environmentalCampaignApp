package com.example.environmentalcampaign.certification_page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.environmentalcampaign.CpData;
import com.example.environmentalcampaign.MyAdapter;
import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.ReviewPage;
import com.example.environmentalcampaign.cp_info.MyCampaignItem;
import com.example.environmentalcampaign.feed.FeedPage;
import com.example.environmentalcampaign.home.HomeActivity;
import com.example.environmentalcampaign.mypage.MyPage;
import com.example.environmentalcampaign.set_up_page.SetUpCampaignPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CertificationPage extends AppCompatActivity {

    TextView tv_home, tv_make, tv_certi, tv_feed, tv_mypage;

    private BaseAdapter adapter1, adapter2;
    private ArrayList<MyCampaignItem> arrayList1, arrayList2;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ListView listView1, listView2;

    TextView complete;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification_page);

//        ListView listView, listView2;
//        MyAdapter adapter, adapter2;
//
//        // Adapter 생성
//        adapter = new MyAdapter();
//        adapter2 = new MyAdapter();
//
//        // 리스트뷰 참조 및 Adapter 달기
//        listView = (ListView)findViewById(R.id.lv_certi_cp);
//        listView.setAdapter(adapter);
//        listView2 = (ListView)findViewById(R.id.lv_complete_cp);
//        listView2.setAdapter(adapter2);
//
//        // 첫 번째 아이템 추가
//        adapter.addItem(70, 2, "버리스타", "2주", "주 2일",  20210701, ContextCompat.getDrawable(this, R.drawable.burista));
//        adapter2.addItem(95, 1, "용기내", "3주", "주 5일",  20210830, ContextCompat.getDrawable(this, R.drawable.cp1), true);

        listView1 = findViewById(R.id.lv_certi_cp);
        listView2 = findViewById(R.id.lv_complete_cp);

        arrayList1 = new ArrayList<>(); // MyCampaignItem 객체를 담을 ArrayList(인증 리스트뷰)
        arrayList2 = new ArrayList<>(); // MyCampaignItem 객체를 담을 ArrayList(완료 리스트뷰)

        // uid 가져오기
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();

        complete = (TextView)findViewById(R.id.tv_complete);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("environmentalCampaign").child("MyCampaign").child(uid);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList1.clear();
                arrayList2.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MyCampaignItem myCampaignItem = snapshot.getValue(MyCampaignItem.class);

                    // 현재시간 가져오기
                    long now = System.currentTimeMillis();
                    Date mDate = new Date(now);
                    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMdd");
                    String today = simpleDate.format(mDate);

                    // 종료날짜 전이라면
                    if(today.compareTo(myCampaignItem.getEndDate()) <= 0) {
                        // 오늘 인증 했으면 arrayList2에 저장
                        if(myCampaignItem.isComplete()) { arrayList2.add(myCampaignItem); }
                        else { arrayList1.add(myCampaignItem); }
                    } else {
                        // 캠페인이 종료되고 리뷰를 작성하지 않았으면
                        if(!myCampaignItem.isReviewComplete()) { reviewDialog(myCampaignItem.getCampaignCode(), myCampaignItem.getTitle()); }
                    }
                }
                adapter1.notifyDataSetChanged();
                adapter2.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Log.e("CertificationPage", String.valueOf(error.toException())); //에러문 출력
            }
        });

        adapter1 = new MyAdapter(arrayList1, this);
        listView1.setAdapter(adapter1);
        adapter2 = new MyAdapter(arrayList2, this);
        listView2.setAdapter(adapter2);

        // 리스트뷰를 클릭하면 인증할 수 있는 페이지로 넘어간다.
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // certification campaign으로 넘어간다
                Intent intent = new Intent(getApplicationContext(), CertificationCampaign.class);

                MyCampaignItem item = (MyCampaignItem) adapter1.getItem(i);
                TextView tv_rate = view.findViewById(R.id.tv_achievement_rate);
                String s = tv_rate.getText().toString();
                int rate = Integer.parseInt(s.substring(7, s.length()-1));
                TextView tv_cp_name = view.findViewById(R.id.tv_cp_name);
                String title = tv_cp_name.getText().toString();

//                BitmapDrawable drawable = (BitmapDrawable)item.getLogo();
//                Bitmap bitmap = drawable.getBitmap();
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//                byte[] byteArray = stream.toByteArray();

                intent.putExtra("campaignCode", item.getCampaignCode());
                intent.putExtra("title", title);
                intent.putExtra("Dday", countdday(Integer.parseInt(item.getEndDate())) + "일 뒤 종료");
                intent.putExtra("certiCount", item.getCertiCount());
                intent.putExtra("certiRate", rate);
//                intent.putExtra("name", item.getName());
//                intent.putExtra("period", item.getPeriod());
//                intent.putExtra("frequency", item.getFrequency());
//                intent.putExtra("logo", byteArray);
//                intent.putExtra("rate", item.getRate());

                startActivity(intent);
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

    public int eyear(int edate) { return Integer.parseInt(String.valueOf(edate).substring(0,4)); }
    public int emonth(int edate) { return Integer.parseInt(String.valueOf(edate).substring(4,6)); }
    public int eday(int edate) { return Integer.parseInt(String.valueOf(edate).substring(6,8)); }

    public int countdday(int edate) {
        try{
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            int myear = eyear(edate);
            int mmonth = emonth(edate);
            int mday = eday(edate);

            Calendar todayCal = Calendar.getInstance(); //오늘날짜 가져오기
            Calendar ddayCal = Calendar.getInstance(); //오늘날짜 가져와서 변경시킴

            mmonth -= 1; //받아온 날짜의 달에서 -1을 해줘야함
            ddayCal.set(myear, mmonth, mday); // 디데이 날짜 입력

            long today = todayCal.getTimeInMillis()/(24*60*60*1000); // 24시간 60분 60초 밀리초로 변환
            long dday = ddayCal.getTimeInMillis()/(24*60*60*1000);
            long count = dday - today; // 디데이 계산
            return (int)count;
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    void reviewDialog(String campaignCode, String title) {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(CertificationPage.this)
                .setTitle("리뷰 작성하기")
                .setMessage(title + "의 기간이 종료되었습니다.\n캠페인 참여는 어떠셨나요?\n참여한 캠페인에 대한 리뷰를 남겨주세요!")
                .setPositiveButton("작성", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getApplicationContext(), ReviewPage.class);
                        intent.putExtra("campaignCode", campaignCode);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        database.getReference("environmentalCampaign").child("MyCampaign").child(uid).child(campaignCode).child("reviewComplete").setValue(true);
                    }
                });
        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }
}