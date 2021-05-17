package com.environmentalcampaign;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.environmentalcampaign.mypage.FragmentCpMakeList;
import com.environmentalcampaign.mypage.FragmentCpMakeList2;
import com.google.android.material.tabs.TabLayout;

public class CpMakelist extends FragmentActivity {

    TabLayout tabLayout;
    FragmentCpMakeList fragmentCpMakeList;
    FragmentCpMakeList2 fragmentCpMakeList2;
    ImageButton bt_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cp_makelist);

        bt_back = (ImageButton)findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        fragmentCpMakeList = new FragmentCpMakeList();
        fragmentCpMakeList2 = new FragmentCpMakeList2();

        getSupportFragmentManager().beginTransaction().add(R.id.tabcontent, fragmentCpMakeList).commit();

        tabLayout = findViewById(R.id.layout_tab);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if(position == 0) {
                    selected = fragmentCpMakeList;
                }
                else if(position == 1) {
                    selected = fragmentCpMakeList2;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.tabcontent, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}