package com.environmentalcampaign.cp_info;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.environmentalcampaign.ParticipantList;
import com.environmentalcampaign.R;
import com.environmentalcampaign.set_up_page.SetUpCampaignPage;
import com.google.android.material.tabs.TabLayout;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_information);

        fragmentInfo = new FragmentInfo();
        fragmentWay = new FragmentWay();
        fragmentReview = new FragmentReview();

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
                if(isSetup()) { fragmentSetup(); }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // 뒤로가기 버튼 이벤트
        bt_back = (ImageButton)findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        iv_logo = (ImageView)findViewById(R.id.iv_logo);
        tv_cp_name = (TextView)findViewById(R.id.tv_cp_name);
        tv_frequency = (TextView)findViewById(R.id.tv_frequency);
        tv_period = (TextView)findViewById(R.id.tv_period);
        tv_participantsN = (TextView)findViewById(R.id.tv_participantsN);
        tv_reCampaignN = (TextView)findViewById(R.id.tv_reCampaignN);

        // 참가인원의 몇명인지 누르면 참가자 리스트 보여주기
        tv_participantsN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ParticipantList.class);
                startActivity(intent);
            }
        });

        // setup의 intent면 내용 불러오기
        gIntent = getIntent();
        if(isSetup()) {
            getSetupInfo();
            bt_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), SetUpCampaignPage.class);
                    startActivity(intent);
                }
            });
        }

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

    // signal이 setup인지 확인
    boolean isSetup() {
        return gIntent.hasExtra("signal") && gIntent.getStringExtra("signal").equals("setup");
    }

    // setup 내용 불러오기
    void getSetupInfo() {
        // 불러오기
        byte[] logoArr = gIntent.getByteArrayExtra("logo");
        Bitmap logo = BitmapFactory.decodeByteArray(logoArr, 0, logoArr.length);
        String cp_name = gIntent.getStringExtra("cp_name");
        String frequency = gIntent.getStringExtra("frequency");
        String period = gIntent.getStringExtra("period");
//        String eDate = gIntent.getStringExtra("eDate");

        // 붙이기
        iv_logo.setImageBitmap(logo);
        tv_cp_name.setText(cp_name);
        tv_frequency.setText(frequency);
        tv_period.setText(period);
        tv_participantsN.setText("0명");
        tv_reCampaignN.setText("0회");

        fragmentSetup();
    }

    // fragment에 setup 정보 입력하기
    void fragmentSetup() {
//        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.tabcontent);
        if(tabLayout.getSelectedTabPosition() == 0) {
            String info = gIntent.getStringExtra("info");
            byte[] infoImage1 = gIntent.getByteArrayExtra("infoImage1");
            byte[] infoImage2 = gIntent.getByteArrayExtra("infoImage2");
            byte[] infoImage3 = gIntent.getByteArrayExtra("infoImage3");
            byte[] infoImage4 = gIntent.getByteArrayExtra("infoImage4");
            byte[] infoImage5 = gIntent.getByteArrayExtra("infoImage5");
            byte[] checkImage = gIntent.getByteArrayExtra("checkImage");

            Bundle bundle1 = new Bundle(); // 괄호 안에 전달하려는 값의 갯수 넣을 수 있음
            bundle1.putString("info", info);
            bundle1.putByteArray("infoImage1", infoImage1);
            bundle1.putByteArray("infoImage2", infoImage2);
            bundle1.putByteArray("infoImage3", infoImage3);
            bundle1.putByteArray("infoImage4", infoImage4);
            bundle1.putByteArray("infoImage5", infoImage5);
            bundle1.putByteArray("checkImage", checkImage);

            fragmentInfo.setArguments(bundle1);
        }
        else if(tabLayout.getSelectedTabPosition() == 1) {
            String way = gIntent.getStringExtra("way");
            byte[] rPhoto1 = gIntent.getByteArrayExtra("rPhoto1");
            byte[] rPhoto2 = gIntent.getByteArrayExtra("rPhoto2");
            String rInfo = gIntent.getStringExtra("rInfo");
            String rInfo2 = gIntent.getStringExtra("rInfo2");
            byte[] wPhoto1 = gIntent.getByteArrayExtra("wPhoto1");
            byte[] wPhoto2 = gIntent.getByteArrayExtra("wPhoto2");
            String wInfo = gIntent.getStringExtra("wInfo");
            String wInfo2 = gIntent.getStringExtra("wInfo2");
            byte[] checkImage2 = gIntent.getByteArrayExtra("checkImage2");
            String frequency = gIntent.getStringExtra("frequency");
            String period = gIntent.getStringExtra("period");

            Bundle bundle2 = new Bundle();
            bundle2.putString("way", way);
            bundle2.putByteArray("rPhoto1", rPhoto1);
            bundle2.putByteArray("rPhoto2", rPhoto2);
            bundle2.putString("rInfo", rInfo);
            bundle2.putString("rInfo2", rInfo2);
            bundle2.putByteArray("wPhoto1", wPhoto1);
            bundle2.putByteArray("wPhoto2", wPhoto2);
            bundle2.putString("wInfo", wInfo);
            bundle2.putString("wInfo2", wInfo2);
            bundle2.putByteArray("checkImage2", checkImage2);
            bundle2.putString("frequency", frequency);
            bundle2.putString("period", period);

            fragmentWay.setArguments(bundle2);
        }
    }

    // 참가하기 버튼 alertdialog
    void showDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(CampaignInformation.this)
                .setTitle("캠페인 참가하기")
                .setMessage("\n관련 사항을 모두 숙지하셨습니까?\n\n※한 번 참가하면 취소가 불가합니다.")
                .setPositiveButton("참가", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tv_participation.setText("참가완료");
                        String tv = tv_participantsN.getText().toString();
                        int n = Integer.parseInt(tv.substring(0, tv.length()-1)) + 1;
                        tv_participantsN.setText( n + "명");
                        Toast.makeText(CampaignInformation.this, "캠페인 참가가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("취소", null);
        AlertDialog alertDlg = alertBuilder.create();
        alertDlg.show();
    }
}