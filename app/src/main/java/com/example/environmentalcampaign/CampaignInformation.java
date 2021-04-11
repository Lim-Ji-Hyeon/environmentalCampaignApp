package com.example.environmentalcampaign;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;

public class CampaignInformation extends FragmentActivity {

    TabLayout tabLayout;
    FragmentInfo fragmentInfo;
    FragmentWay fragmentWay;
    FragmentReview fragmentReview;
    ImageButton bt_back;


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
    }
}