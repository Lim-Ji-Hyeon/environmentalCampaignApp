package com.example.environmentalcampaign.mypage;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.mypage.FragmentCpMakeList;
import com.example.environmentalcampaign.mypage.FragmentCpMakeList2;
import com.example.environmentalcampaign.set_up_page.SetUpCampaignItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CpMakelist extends FragmentActivity {

    TabLayout tabLayout;
    FragmentCpMakeList fragmentCpMakeList;
    FragmentCpMakeList2 fragmentCpMakeList2;
    ImageButton bt_back;

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String uid;

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
//                setFragment();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        setFragment();
    }

    // fragment에 정보 입력
    void setFragment() {
        // uid 가져오기
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("environmentalCampaign").child("SetUpCampaign");

        if(tabLayout.getSelectedTabPosition() == 0) {
            Bundle bundle1 = new Bundle();

            ArrayList<String> campaignCodes = new ArrayList<>();
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    campaignCodes.clear();
                    if(dataSnapshot.hasChild(uid)) {
                        for(DataSnapshot snapshot : dataSnapshot.child(uid).getChildren()) {
                            SetUpCampaignItem setUpCampaignItem = snapshot.getValue(SetUpCampaignItem.class);
                            campaignCodes.add(setUpCampaignItem.getCampaignCode());
                        }
                    }
                    bundle1.putStringArrayList("campaignCodes", campaignCodes);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            fragmentCpMakeList.setArguments(bundle1);
        }
        else if (tabLayout.getSelectedTabPosition() == 1) {
            Bundle bundle2 = new Bundle();

            ArrayList<String> campaignCodes = new ArrayList<>();
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    campaignCodes.clear();
                    if(dataSnapshot.hasChild(uid)) {
                        for(DataSnapshot snapshot : dataSnapshot.child(uid).getChildren()) {
                            SetUpCampaignItem setUpCampaignItem = snapshot.getValue(SetUpCampaignItem.class);
                            campaignCodes.add(setUpCampaignItem.getCampaignCode());
                        }
                    }
                    bundle2.putStringArrayList("campaignCodes", campaignCodes);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            fragmentCpMakeList2.setArguments(bundle2);
        }
    }
}