package com.example.environmentalcampaign.cp_info;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.set_up_page.SetUpCampaignPage;
import com.google.android.material.tabs.TabLayout;

public class CampaignInformation extends FragmentActivity {

    TabLayout tabLayout;
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

        // setup의 intent면 내용 불러오기
        gIntent = getIntent();
        if(gIntent.hasExtra("signal")) {
            if(gIntent.getStringExtra("signal").equals("setup")) {
                getSetupInfo();
                bt_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SetUpCampaignPage.class);
                        startActivity(intent);
                    }
                });
            }
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

    // setup 내용 불러오기
    void getSetupInfo() {
        // 불러오기
        byte[] logoArr = gIntent.getByteArrayExtra("logo");
        Bitmap logo = BitmapFactory.decodeByteArray(logoArr, 0, logoArr.length);
        String cp_name = gIntent.getStringExtra("cp_name");
        String frequency = gIntent.getStringExtra("frequency");
        String period = gIntent.getStringExtra("period");
//        String eDate = gIntent.getStringExtra("eDate");

        byte[] arr1 = gIntent.getByteArrayExtra("infoImage1");
        Bitmap infoImage1 = BitmapFactory.decodeByteArray(arr1, 0, arr1.length);

        Bitmap infoImage2 = (Bitmap)gIntent.getExtras().get("infoImage2");
        Bitmap infoImage3 = (Bitmap)gIntent.getExtras().get("infoImage3");
        Bitmap infoImage4 = (Bitmap)gIntent.getExtras().get("infoImage4");
        Bitmap infoImage5 = (Bitmap)gIntent.getExtras().get("infoImage5");
        String way = gIntent.getStringExtra("way");
        Bitmap rPhoto1 = (Bitmap)gIntent.getExtras().get("rPhoto1");
        Bitmap rPhoto2 = (Bitmap)gIntent.getExtras().get("rPhoto2");
        String rInfo = gIntent.getStringExtra("rInfo");
        String rInfo2 = gIntent.getStringExtra("rInfo2");
        Bitmap wPhoto1 = (Bitmap)gIntent.getExtras().get("wPhoto1");
        Bitmap wPhoto2 = (Bitmap)gIntent.getExtras().get("wPhoto2");
        String wInfo = gIntent.getStringExtra("wInfo");
        String wInfo2 = gIntent.getStringExtra("wInfo2");

        // 붙이기
        iv_logo.setImageBitmap(logo);
        tv_cp_name.setText(cp_name);
        tv_frequency.setText(frequency);
        tv_period.setText(period);
        tv_participantsN.setText("0명");
        tv_reCampaignN.setText("0회");

        infoSetup();
    }

    // setup info
    void infoSetup() {
        String info = gIntent.getStringExtra("info");

        Fragment fragment = getSupportFragmentManager().findFragmentById(fragmentInfo.getId());

//        LayoutInflater mInflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
//        FrameLayout tabcontent = (FrameLayout)findViewById(R.id.tabcontent);
//        fragment.getLayoutInflater().inflate(R.layout.fragment_info, tabcontent, false);
//        TextView tv_cpInfo = (TextView)viewGroup.findViewById(R.id.tv_cpInfo);
//        tv_cpInfo.setText(info);
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