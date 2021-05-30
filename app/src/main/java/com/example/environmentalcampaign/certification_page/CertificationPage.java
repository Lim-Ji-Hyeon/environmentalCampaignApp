package com.example.environmentalcampaign.certification_page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.environmentalcampaign.CpData;
import com.example.environmentalcampaign.MyAdapter;
import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.feed.FeedPage;
import com.example.environmentalcampaign.home.HomeActivity;
import com.example.environmentalcampaign.mypage.MyPage;
import com.example.environmentalcampaign.set_up_page.SetUpCampaignPage;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class CertificationPage extends AppCompatActivity {

    TextView tv_home, tv_make, tv_certi, tv_feed, tv_mypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification_page);

        ListView listView, listView2;
        MyAdapter adapter, adapter2;

        // Adapter 생성
        adapter = new MyAdapter();
        adapter2 = new MyAdapter();

        // 리스트뷰 참조 및 Adapter 달기
        listView = (ListView)findViewById(R.id.lv_certi_cp);
        listView.setAdapter(adapter);
        listView2 = (ListView)findViewById(R.id.lv_complete_cp);
        listView2.setAdapter(adapter2);

        // 첫 번째 아이템 추가
        adapter.addItem(70, 2, "버리스타", "2주", "주 2일",  20210701, ContextCompat.getDrawable(this, R.drawable.burista));
        adapter2.addItem(95, 1, "용기내", "3주", "주 5일",  20210830, ContextCompat.getDrawable(this, R.drawable.cp1), true);


        // 리스트뷰를 클릭하면 인증할 수 있는 페이지로 넘어간다.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // certification campaign으로 넘어간다
                Intent intent = new Intent(getApplicationContext(), CertificationCampaign.class);

                CpData item = (CpData)adapter.getItem(i);
                BitmapDrawable drawable = (BitmapDrawable)item.getLogo();
                Bitmap bitmap = drawable.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                intent.putExtra("name", item.getName());
                intent.putExtra("Dday", countdday(item.getEdate()) + "일 뒤 종료");
                intent.putExtra("period", item.getPeriod());
                intent.putExtra("frequency", item.getFrequency());
                intent.putExtra("logo", byteArray);
                intent.putExtra("rate", item.getRate());

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

}