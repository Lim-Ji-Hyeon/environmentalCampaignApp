package com.example.environmentalcampaign.cp_info;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.set_up_page.SetUpCampaignPage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CampaignInformation extends FragmentActivity {

    TabLayout tabLayout;
    FrameLayout tabcontent;
    FragmentInfo fragmentInfo;
    FragmentWay fragmentWay;
    FragmentReview fragmentReview;
    ImageButton bt_back;

    Intent gIntent;
    ImageView iv_logo;
    TextView tv_cp_name, tv_frequency, tv_period, tv_participantsN, tv_reCampaignN;

    TextView tv_participation;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    public CampaignItem campaignItem;
    String datetime, uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_information);

        fragmentInfo = new FragmentInfo();
        fragmentWay = new FragmentWay();
        fragmentReview = new FragmentReview();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        uid = firebaseUser.getUid();

        // 뒤로가기 버튼 이벤트
        bt_back = (ImageButton)findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // datetime 받아오는 부분 만들어야 함.
        gIntent = getIntent();
        if(isRecyclerView()) {
            datetime = gIntent.getStringExtra("campaignCode");
//            getSetupInfo(datetime);
        }

        // setup의 intent면 내용 불러오기
        if(isSetup()) {
            datetime = gIntent.getStringExtra("datetime");
//            getSetupInfo(datetime);

            bt_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), SetUpCampaignPage.class);
                    startActivity(intent);
                }
            });
        }

        getSetupInfo(datetime);
        getSupportFragmentManager().beginTransaction().add(R.id.tabcontent, fragmentInfo).commit();

        tabLayout = findViewById(R.id.layout_tab);
//        tabLayout.addTab(tabLayout.newTab().setText("설명"));
//        tabLayout.addTab(tabLayout.newTab().setText("인증방법"));
//        tabLayout.addTab(tabLayout.newTab().setText("후기"));
        tabcontent = (FrameLayout)findViewById(R.id.tabcontent);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if(position == 0)
                    selected = fragmentInfo;
                else if(position == 1)
                    selected = fragmentWay;
                else if(position == 2)
                    selected = fragmentReview;
                getSupportFragmentManager().beginTransaction().replace(R.id.tabcontent, selected).commit();
//                fragmentSetup();
//                if(isSetup()) { fragmentSetup(); }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        iv_logo = (ImageView)findViewById(R.id.iv_logo);
        tv_cp_name = (TextView)findViewById(R.id.tv_cp_name);
        tv_frequency = (TextView)findViewById(R.id.tv_frequency);
        tv_period = (TextView)findViewById(R.id.tv_period);
        tv_participantsN = (TextView)findViewById(R.id.tv_participantsN);
        tv_reCampaignN = (TextView)findViewById(R.id.tv_reCampaignN);

//        if(이미 참가중인 캠페인이라면) {
//            tv_participation.setText("참가완료");
//        }

        // 참가인원의 몇명인지 누르면 참가자 리스트 보여주기
        tv_participantsN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(campaignItem.getParticipantsN() == 0) {
                    Toast.makeText(CampaignInformation.this, "현재 캠페인에 참가 중인 사람이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(), ParticipantList.class);
                intent.putExtra("datetime", datetime);
                startActivity(intent);
            }
        });

        // 참가하기 버튼 이벤트
        tv_participation = (TextView)findViewById(R.id.tv_participation);
        tv_participation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tv_participation.getText().equals("참가하기 (오늘 바로 시작)")) {
                    showDialog();
                } else {
                    Toast.makeText(CampaignInformation.this, "이미 참가한 캠페인입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // signal이 recyclerView인지 확인
    boolean isRecyclerView() {
        return gIntent.hasExtra("signal") && gIntent.getStringExtra("signal").equals("recyclerView");
    }

    // signal이 setup인지 확인
    boolean isSetup() {
        return gIntent.hasExtra("signal") && gIntent.getStringExtra("signal").equals("setup");
    }

    // setup 내용 불러오기
    void getSetupInfo(String datetime) {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("environmentalCampaign").child("Campaign").child(datetime);
        databaseReference.child("campaign").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                campaignItem = snapshot.getValue(CampaignItem.class);

                // 내용 붙이기
                iv_logo.setImageDrawable(byteArrayToDrawable(binaryStringToByteArray(campaignItem.getLogo())));
                tv_cp_name.setText(campaignItem.getTitle());
                tv_frequency.setText(campaignItem.getFrequency());
                tv_period.setText(campaignItem.getPeriod());
                tv_participantsN.setText(campaignItem.getParticipantsN() + "명");
                tv_reCampaignN.setText(campaignItem.getReCampaignN() + "회");

                fragmentSetup();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Log.e("CampaignInformation", String.valueOf(error.toException())); //에러문 출력
            }
        });
    }

    // fragment에 setup 정보 입력하기
    void fragmentSetup() {
//        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.tabcontent);
//        if(tabLayout.getSelectedTabPosition() == 0) {
            String info = campaignItem.getCpInfo();

            String infoImage1 = campaignItem.getInfoImage1();
            String infoImage2 = campaignItem.getInfoImage2();
            String infoImage3 = campaignItem.getInfoImage3();
            String infoImage4 = campaignItem.getInfoImage4();
            String infoImage5 = campaignItem.getInfoImage5();

            Bundle bundle1 = new Bundle(); // 괄호 안에 전달하려는 값의 갯수 넣을 수 있음
            bundle1.putString("info", info);
            bundle1.putString("infoImage1", infoImage1);
            bundle1.putString("infoImage2", infoImage2);
            bundle1.putString("infoImage3", infoImage3);
            bundle1.putString("infoImage4", infoImage4);
            bundle1.putString("infoImage5", infoImage5);

            fragmentInfo.setArguments(bundle1);
//        }
//        else if(tabLayout.getSelectedTabPosition() == 1) {
            String way = campaignItem.getWayInfo();
            String rPhoto1 = campaignItem.getRightPhoto1();
            String rPhoto2 = campaignItem.getRightPhoto2();
            String wPhoto1 = campaignItem.getWrongPhoto1();
            String wPhoto2 = campaignItem.getWrongPhoto2();
            String rInfo = campaignItem.getRightPhotoInfo1();
            String rInfo2 = campaignItem.getRightPhotoInfo2();
            String wInfo = campaignItem.getWrongPhotoInfo1();
            String wInfo2 = campaignItem.getWrongPhotoInfo2();
            String frequency = campaignItem.getFrequency();
            String period = campaignItem.getPeriod();

            Bundle bundle2 = new Bundle();
            bundle2.putString("way", way);
            bundle2.putString("rPhoto1", rPhoto1);
            bundle2.putString("rPhoto2", rPhoto2);
            bundle2.putString("rInfo", rInfo);
            bundle2.putString("rInfo2", rInfo2);
            bundle2.putString("wPhoto1", wPhoto1);
            bundle2.putString("wPhoto2", wPhoto2);
            bundle2.putString("wInfo", wInfo);
            bundle2.putString("wInfo2", wInfo2);
            bundle2.putString("frequency", frequency);
            bundle2.putString("period", period);

            fragmentWay.setArguments(bundle2);
//        }
//        else if(tabLayout.getSelectedTabPosition() == 2) {
            Bundle bundle3 = new Bundle();
            bundle3.putString("datetime", datetime);
            fragmentReview.setArguments(bundle3);
//        }
    }

    // String을 byte[]로 변환
    public static byte[] binaryStringToByteArray(String s) {
        int count = s.length() / 8;
        byte[] b = new byte[count];
        for(int i = 1; i < count; ++i) {
            String t = s.substring((i-1)*8, i*8);
            b[i-1] = binaryStringToByte(t);
        }
        return b;
    }

    // String을 byte로 변환
    public static byte binaryStringToByte(String s) {
        byte ret = 0, total = 0;
        for(int i = 0; i < 8; ++i) {
            ret = (s.charAt(7 - i) == '1') ? (byte)(1 << i) : 0;
            total = (byte) (ret | total);
        }
        return total;
    }

    // byte[]를 Drawable로 변환
    public Drawable byteArrayToDrawable(byte[] b) {
        ByteArrayInputStream is = new ByteArrayInputStream(b);
        Drawable drawable = Drawable.createFromStream(is, "drawable");
        return drawable;
    }

    // 참가하기 버튼 alertdialog
    void showDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(CampaignInformation.this)
                .setTitle("캠페인 참가하기")
                .setMessage("\n관련 사항을 모두 숙지하셨습니까?\n\n※한 번 참가하면 취소가 불가합니다.")
                .setPositiveButton("참가", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 내가 참가한 캠페인에 넣을 방법 구현해야함.
                        String sDate = getTimeMilli();
                        DatabaseReference myCampaignRef = database.getReference("environmentalCampaign").child("MyCampaign").child(uid);
                        myCampaignRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                // 참여한 적이 있다면
                                if(snapshot.hasChild(datetime)) {
                                    // 이전 내용 가져오기
                                    MyCampaignItem myCampaignItem = snapshot.child(datetime).child("myCampaign").getValue(MyCampaignItem.class);
                                    myCampaignItem.setStartDate(sDate);
                                    myCampaignItem.setEndDate(getEndDate(sDate));
                                    int reCount = myCampaignItem.getReCount();
                                    myCampaignItem.setReCount(reCount + 1);

                                    // 다시 올리기
                                    myCampaignRef.child(datetime).child("myCampaign").setValue(myCampaignItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // 버튼 '참가완료'로 바꾸기
                                            tv_participation.setText("참가완료");
                                            // 평균 참여 횟수 수정하기
                                            double r = reCampaignAvg();
                                            tv_reCampaignN.setText(r + "회");
                                            databaseReference.child("campaign").child("reCampaignN").setValue(r);
                                            database.getReference("environmentalCampaign").child("HomeCampaign").child(datetime).child("reCampaignN").setValue(r);
                                        }
                                    });
                                }
                                // 참여한 적이 없다면
                                else {
                                    // MyCampaignItem 객체 생성해서 데이터베이스에 삽입
                                    MyCampaignItem myCampaignItem = new MyCampaignItem();
                                    myCampaignItem.setCampaignCode(datetime);
                                    myCampaignItem.setCertiCount(getCertiCount());
                                    myCampaignItem.setStartDate(sDate);
                                    myCampaignItem.setEndDate(getEndDate(sDate));
                                    myCampaignItem.setReCount(1);
                                    myCampaignRef.child(datetime).child("myCampaign").setValue(myCampaignItem);
//                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void aVoid) {
//                                        }
//                                    });

                                    // 참가자 리스트에 추가하기
                                    databaseReference.child("participants").child(uid).child("uid").setValue(uid).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // 참가 인원 수정하기
                                            String tv = tv_participantsN.getText().toString();
                                            int n = Integer.parseInt(tv.substring(0, tv.length()-1)) + 1;
                                            tv_participantsN.setText( n + "명");
                                            databaseReference.child("campaign").child("participantsN").setValue(n);
                                        }
                                    });
                                    // 버튼 '참가완료'로 바꾸기
                                    tv_participation.setText("참가완료");
                                    // 평균 참여 횟수 수정하기
                                    double r = reCampaignAvg();
                                    tv_reCampaignN.setText(r + "회");
                                    databaseReference.child("campaign").child("reCampaignN").setValue(r);
                                    database.getReference("environmentalCampaign").child("HomeCampaign").child(datetime).child("reCampaignN").setValue(r);
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // DB를 가져오던 중 에러 발생 시
                                Log.e("checkMyCampaign", String.valueOf(error.toException())); //에러문 출력
                            }
                        });

                        Toast.makeText(CampaignInformation.this, "캠페인 참가가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("취소", null);
        AlertDialog alertDlg = alertBuilder.create();
        alertDlg.show();
    }

    // 생성날짜 구하기
    public String getTimeMilli() {
        String result = "";
        String month_str, day_str, hour_str, minute_str, second_str, milliSecond_str;
        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        int milliSecond = c.get(Calendar.MILLISECOND);

        if(month < 10) { month_str = "0" + month; } else { month_str = "" + month; }
        if(day < 10) { day_str = "0" + day; } else { day_str = "" + day; }
        if(hour < 10) { hour_str = "0" + hour; } else { hour_str = "" + hour; }
        if(minute < 10) { minute_str = "0" + minute; } else { minute_str = "" + minute; }
        if(second < 10) { second_str = "0" + second; } else { second_str = "" + second; }
        if(milliSecond < 10) {
            milliSecond_str = "00" + milliSecond;
        } else {
            if(milliSecond < 100) {
                milliSecond_str = "0" + milliSecond;
            } else {
                milliSecond_str = "" + milliSecond;
            }
        }

        result = year + month_str + day_str + hour_str + minute_str + second_str + milliSecond_str;

        return result;
    }

    // 인증 마지막 날짜 구하기
    String getEndDate(String sDate) {
        int year = Integer.parseInt(sDate.substring(0, 4));
        int month = Integer.parseInt(sDate.substring(4, 6));
        int day = Integer.parseInt(sDate.substring(6, 8));

        String sPeriod = tv_period.getText().toString();
        int period = Integer.parseInt(sPeriod.substring(0, sPeriod.length()-1));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = new GregorianCalendar(year, month-1, day);
        cal.add(Calendar.DAY_OF_MONTH, period*7);

        return sdf.format(cal.getTime());
    }

    // 인증해야하는 횟수 구하기
    int getCertiCount() {
        String sFrequency = tv_frequency.getText().toString();
        String sPeriod = tv_period.getText().toString();

        int frequency = Integer.parseInt(sFrequency.substring(2, sFrequency.length()-1));
        int period = Integer.parseInt(sPeriod.substring(0, sPeriod.length()-1));

        return frequency * period;
    }

    // 평균 참여 횟수 구하기
    double reCampaignAvg() {
        ArrayList<ParticipantItem> participants = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                participants.clear();
                if(dataSnapshot.hasChild("participants")) {
                    for(DataSnapshot snapshot : dataSnapshot.child("participants").getChildren()) {
                        ParticipantItem participantItem = snapshot.getValue(ParticipantItem.class);
                        participants.add(participantItem);
                    }
                } else {
                    databaseReference.child("participants").child(uid).child("uid").setValue(uid);
                    ParticipantItem participantItem = new ParticipantItem();
                    participantItem.setUid(uid);
                    participants.add(participantItem);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Log.e("participantsAdd", String.valueOf(error.toException())); //에러문 출력
            }
        });

        ArrayList<MyCampaignItem> myCampaigns = new ArrayList<>();
        myCampaigns.clear();
        for(int i = 0; i < participants.size(); i++) {
            String participantUid = participants.get(i).getUid();
            database.getReference("environmentalCampaign").child("MyCampaign").child(participantUid).child(datetime).child("myCampaign").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    MyCampaignItem myCampaignItem = snapshot.getValue(MyCampaignItem.class);
                    myCampaigns.add(myCampaignItem);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // DB를 가져오던 중 에러 발생 시
                    Log.e("myCampaignsAdd", String.valueOf(error.toException())); //에러문 출력
                }
            });
        }

        double sum = 0;
        for(int i = 0; i < myCampaigns.size(); i++) {
            sum += (double)myCampaigns.get(i).getReCount();
        }
        return Double.parseDouble(String.format("%.2f", sum / myCampaigns.size()));
    }
}