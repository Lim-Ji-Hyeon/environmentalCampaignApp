package com.example.environmentalcampaign.certification_page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.environmentalcampaign.MyAdapter;
import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.cp_info.MyCampaignItem;
import com.example.environmentalcampaign.feed.FeedPage;
import com.example.environmentalcampaign.home.HomeActivity;
import com.example.environmentalcampaign.mypage.CompleteCampaignItem;
import com.example.environmentalcampaign.mypage.MyPage;
import com.example.environmentalcampaign.mypage.PointItem;
import com.example.environmentalcampaign.set_up_page.SetUpCampaignPage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CertificationPage extends AppCompatActivity {

    LinearLayout lo_home, lo_make, lo_certi, lo_feed, lo_mypage;

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
                    if((today.compareTo(myCampaignItem.getEndDate()) <= 0)&&(myCampaignItem.getCertiCompleteCount()<=myCampaignItem.getCertiCount())) {
                        // 오늘 인증 했으면 arrayList2에 저장
                        if(myCampaignItem.isComplete()) { arrayList2.add(myCampaignItem); }
                        else { arrayList1.add(myCampaignItem); }
                    } else if(today.compareTo(myCampaignItem.getEndDate()) > 0) {
                        // 캠페인이 종료되고 리뷰를 작성하지 않았으면
                        if(!myCampaignItem.isReviewComplete()) { reviewDialog(myCampaignItem.getCampaignCode(), myCampaignItem.getTitle()); }
                        database.getReference("environmentalCampaign").child("CompleteCampaign").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                                String campaignCode = myCampaignItem.getCampaignCode();
                                double avg = Double.parseDouble(String.format("%.1f", (double)myCampaignItem.getCertiCompleteCount()*100/myCampaignItem.getCertiCount()));

                                // 캠페인이 이미 완료캠페인에 존재할 경우
                                if(snapshot1.hasChild(uid)&&snapshot1.child(uid).hasChild(campaignCode)) {
                                    CompleteCampaignItem completeCampaignItem = snapshot1.child(uid).child(campaignCode).getValue(CompleteCampaignItem.class);
                                    // 완료캠페인에 갱신되지 않았을 경우
                                    if(completeCampaignItem.getReCount() != myCampaignItem.getReCount()) {
                                        double avgRate = completeCampaignItem.getAchievementAvg();
                                        int c = completeCampaignItem.getReCount();
                                        completeCampaignItem.setAchievementAvg(Double.parseDouble(String.format("%.1f", (avgRate*c+avg)/(c+1))));
                                        completeCampaignItem.setReCount(myCampaignItem.getReCount());
                                        database.getReference("environmentalCampaign").child("CompleteCampaign").child(uid).child(campaignCode).setValue(completeCampaignItem);

                                        database.getReference("environmentalCampaign").child("Point").child(uid).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                            @Override
                                            public void onSuccess(DataSnapshot dataSnapshot) {
                                                PointItem pointItem = dataSnapshot.getValue(PointItem.class);
                                                int currentPoint = pointItem.getPoint();
                                                currentPoint += totalPoint(myCampaignItem);
                                                pointItem.setPoint(currentPoint);
                                                database.getReference("environmentalCampaign").child("Point").child(uid).setValue(pointItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(getApplicationContext(), totalPoint(myCampaignItem)+"p가 적립되었습니다.", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        });
                                    }
                                }
                                // 해당 캠페인을 처음 완료했을 경우
                                else {
                                    CompleteCampaignItem completeCampaignItem = new CompleteCampaignItem();
                                    completeCampaignItem.setCampaignCode(campaignCode);
                                    completeCampaignItem.setAchievementAvg(avg);
                                    completeCampaignItem.setReCount(myCampaignItem.getReCount());
                                    database.getReference("environmentalCampaign").child("CompleteCampaign").child(uid).child(campaignCode).setValue(completeCampaignItem);

                                    // 캠페인 완료가 처음일 경우
                                    if(!snapshot1.hasChild(uid)) {
                                        PointItem pointItem = new PointItem();
                                        pointItem.setUid(uid);
                                        pointItem.setPoint(totalPoint(myCampaignItem));
                                        database.getReference("environmentalCampaign").child("Point").child(uid).setValue(pointItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getApplicationContext(), totalPoint(myCampaignItem)+"p가 적립되었습니다.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        database.getReference("environmentalCampaign").child("Point").child(uid).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                            @Override
                                            public void onSuccess(DataSnapshot dataSnapshot) {
                                                PointItem pointItem = dataSnapshot.getValue(PointItem.class);
                                                int currentPoint = pointItem.getPoint();
                                                currentPoint += totalPoint(myCampaignItem);
                                                pointItem.setPoint(currentPoint);
                                                database.getReference("environmentalCampaign").child("Point").child(uid).setValue(pointItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(getApplicationContext(), totalPoint(myCampaignItem)+"p가 적립되었습니다.", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
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
//                TextView tv_rate = view.findViewById(R.id.tv_achievement_rate);
//                String s = tv_rate.getText().toString();
//                int rate = Integer.parseInt(s.substring(7, s.length()-1));
//                TextView tv_cp_name = view.findViewById(R.id.tv_cp_name);
//                String title = tv_cp_name.getText().toString();

                intent.putExtra("campaignCode", item.getCampaignCode());
                intent.putExtra("title", item.getTitle());
                intent.putExtra("Dday", countdday(Integer.parseInt(item.getEndDate())) + "일 뒤 종료");
                intent.putExtra("certiCount", item.getCertiCount());
                intent.putExtra("certiRate", item.getCertiCompleteCount()*100/item.getCertiCount());

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

    int totalPoint(MyCampaignItem myCampaignItem) {
        int point = 0;
        // 총 인증률이 80%가 넘어야 포인트 적립
        if(myCampaignItem.getCertiCompleteCount()*100/myCampaignItem.getCertiCount() >= 80) {
            point = myCampaignItem.getCertiCompleteCount() * 50; // 인증횟수마다 50p씩 적립
        }
        return point;
    }
}