package com.example.environmentalcampaign.certification_page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.cp_info.CampaignItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;

public class CertificationCampaign extends AppCompatActivity {

    TabLayout tabLayout;
    FrameLayout tabcontent;
    FragmentCertiRate fragmentCertiRate;
    FragmentCertiPhotos fragmentCertiPhotos;

    TextView tv_cp_name, tv_certiTime, tv_certiLeftDate, tv_certiFrequency;
    ImageView iv_logo;
    FrameLayout fl_rightPhoto1, fl_rightPhoto2, fl_wrongPhoto1, fl_wrongPhoto2;
    ImageView iv_rightPhoto1, iv_rightPhoto2, iv_wrongPhoto1, iv_wrongPhoto2;
    TextView tv_rightPhotoInfo1, tv_rightPhotoInfo2, tv_wrongPhotoInfo1, tv_wrongPhotoInfo2,
            tv_rightPhotoPath1, tv_rightPhotoPath2, tv_wrongPhotoPath1, tv_wrongPhotoPath2;

    ImageButton bt_back;
    Button certi_button;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

//    String name, time, Dday, period, frequency;
//    byte[] arr;
//    int rate;
    String campaignCode, Dday;
    int certiRate, certiCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification_campaign);

        Intent gIntent = getIntent();
        campaignCode = gIntent.getStringExtra("campaignCode");
        Dday = gIntent.getStringExtra("Dday");
        certiRate = gIntent.getIntExtra("certiRate", 0);
        certiCount = gIntent.getIntExtra("certiCount", 0);

        tv_cp_name = (TextView)findViewById(R.id.tv_cp_name);
        tv_certiLeftDate = (TextView)findViewById(R.id.tv_certiLeftDate);
        tv_certiFrequency = (TextView)findViewById(R.id.tv_certiFrequency);
        iv_logo = (ImageView)findViewById(R.id.iv_logo);

        fl_rightPhoto1 = (FrameLayout)findViewById(R.id.fl_rightPhoto1);
        fl_rightPhoto2 = (FrameLayout)findViewById(R.id.fl_rightPhoto2);
        fl_wrongPhoto1 = (FrameLayout)findViewById(R.id.fl_wrongPhoto1);
        fl_wrongPhoto2 = (FrameLayout)findViewById(R.id.fl_wrongPhoto2);
        iv_rightPhoto1 = (ImageView)findViewById(R.id.iv_rightPhoto1);
        iv_rightPhoto2 = (ImageView)findViewById(R.id.iv_rightPhoto2);
        iv_wrongPhoto1 = (ImageView)findViewById(R.id.iv_wrongPhoto1);
        iv_wrongPhoto2 = (ImageView)findViewById(R.id.iv_wrongPhoto2);
        tv_rightPhotoInfo1 = (TextView)findViewById(R.id.tv_rightPhotoInfo1);
        tv_rightPhotoInfo2 = (TextView)findViewById(R.id.tv_rightPhotoInfo2);
        tv_wrongPhotoInfo1 = (TextView)findViewById(R.id.tv_wrongPhotoInfo1);
        tv_wrongPhotoInfo2 = (TextView)findViewById(R.id.tv_wrongPhotoInfo2);
        tv_rightPhotoPath1 = (TextView)findViewById(R.id.tv_rightPhotoPath1);
        tv_rightPhotoPath2 = (TextView)findViewById(R.id.tv_rightPhotoPath2);
        tv_wrongPhotoPath1 = (TextView)findViewById(R.id.tv_wrongPhotoPath1);
        tv_wrongPhotoPath2 = (TextView)findViewById(R.id.tv_wrongPhotoPath2);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("environmentalCampaign").child("Campaign").child(campaignCode).child("campaign");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CampaignItem campaignItem = snapshot.getValue(CampaignItem.class);

                String logo = campaignItem.getLogo();
                String rPhoto1 = campaignItem.getRightPhoto1();
                String rPhoto2 = campaignItem.getRightPhoto2();
                String wPhoto1 = campaignItem.getWrongPhoto1();
                String wPhoto2 = campaignItem.getWrongPhoto2();
                String rPhotoInfo1 = campaignItem.getRightPhotoInfo1();
                String rPhotoInfo2 = campaignItem.getRightPhotoInfo2();
                String wPhotoInfo1 = campaignItem.getWrongPhotoInfo1();
                String wPhotoInfo2 = campaignItem.getWrongPhotoInfo2();

                tv_cp_name.setText(campaignItem.getTitle());
                tv_certiFrequency.setText(campaignItem.getPeriod() + ", " + campaignItem.getFrequency());
                tv_certiLeftDate.setText(Dday);
                Glide.with(CertificationCampaign.this).load(logo).into(iv_logo);

                String[] photos = {rPhoto1, rPhoto2, wPhoto1, wPhoto2};
                String[] photoInfos = {rPhotoInfo1, rPhotoInfo2, wPhotoInfo1, wPhotoInfo2};
                ImageView[] imageViews = {iv_rightPhoto1, iv_rightPhoto2, iv_wrongPhoto1, iv_wrongPhoto2};
                TextView[] textViews = {tv_rightPhotoInfo1, tv_rightPhotoInfo2, tv_wrongPhotoInfo1, tv_wrongPhotoInfo2};
                TextView[] paths = {tv_rightPhotoPath1, tv_rightPhotoPath2, tv_wrongPhotoPath1, tv_wrongPhotoPath2};
                FrameLayout[] frameLayouts = {fl_rightPhoto1, fl_rightPhoto2, fl_wrongPhoto1, fl_wrongPhoto2};
                for(int i = 0; i < photos.length; i++) {
                    if(!photos[i].equals("")) {
                        Glide.with(CertificationCampaign.this).load(photos[i]).into(imageViews[i]);
                        textViews[i].setText(photoInfos[i]);
                        paths[i].setText(photos[i]);
                    } else {
                        frameLayouts[i].setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Log.e("CertificationCampaign", String.valueOf(error.toException())); //에러문 출력
            }
        });

        // 이미지뷰 클릭 시 팝업 설정
        View.OnClickListener ivListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.iv_rightPhoto1:
                        Intent intent1 = new Intent(CertificationCampaign.this, PopupActivity.class);
//                        byte[] byteArray1 = bitmapToByteArray(iv_rightPhoto1);
//                        intent1.putExtra("image", byteArray1);
                        intent1.putExtra("image", tv_rightPhotoPath1.getText());
                        intent1.putExtra("info", tv_rightPhotoInfo1.getText());
                        startActivityForResult(intent1, 1);
                        break;
                    case R.id.iv_rightPhoto2:
                        Intent intent2 = new Intent(CertificationCampaign.this, PopupActivity.class);
//                        byte[] byteArray2 = bitmapToByteArray(iv_rightPhoto2);
//                        intent2.putExtra("image", byteArray2);
                        intent2.putExtra("image", tv_rightPhotoPath2.getText());
                        intent2.putExtra("info", tv_rightPhotoInfo2.getText());
                        startActivityForResult(intent2, 1);
                        break;
                    case R.id.iv_wrongPhoto1:
                        Intent intent3 = new Intent(CertificationCampaign.this, PopupActivity.class);
//                        byte[] byteArray3 = bitmapToByteArray(iv_wrongPhoto1);
//                        intent3.putExtra("image", byteArray3);
                        intent3.putExtra("image", tv_wrongPhotoPath1.getText());
                        intent3.putExtra("info", tv_wrongPhotoInfo1.getText());
                        startActivityForResult(intent3, 1);
                        break;
                    case R.id.iv_wrongPhoto2:
                        Intent intent4 = new Intent(CertificationCampaign.this, PopupActivity.class);
//                        byte[] byteArray4 = bitmapToByteArray(iv_wrongPhoto2);
//                        intent4.putExtra("image", byteArray4);
                        intent4.putExtra("image", tv_wrongPhotoPath2.getText());
                        intent4.putExtra("info", tv_wrongPhotoInfo2.getText());
                        startActivityForResult(intent4, 1);
                        break;
                }
            }
        };

        iv_rightPhoto1.setOnClickListener(ivListener);
        iv_rightPhoto2.setOnClickListener(ivListener);
        iv_wrongPhoto1.setOnClickListener(ivListener);
        iv_wrongPhoto2.setOnClickListener(ivListener);

        // tablayout과 fragment 연결
        fragmentCertiRate = new FragmentCertiRate();
        fragmentCertiPhotos = new FragmentCertiPhotos();

        getSupportFragmentManager().beginTransaction().add(R.id.tabcontent, fragmentCertiRate).commit();

        tabLayout = findViewById(R.id.layout_tab);
        tabcontent = (FrameLayout)findViewById(R.id.tabcontent);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if(position == 0)
                    selected = fragmentCertiRate;
                else if(position == 1)
                    selected = fragmentCertiPhotos;
                getSupportFragmentManager().beginTransaction().replace(R.id.tabcontent, selected).commit();
                fragmentInitialization();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        // 리스트뷰의 캠페인 내용 불러오기
//        Intent gIntent = getIntent();
//        name = gIntent.getStringExtra("name");
//        Dday = gIntent.getStringExtra("Dday");
//        period = gIntent.getStringExtra("period");
//        frequency = gIntent.getStringExtra("frequency");
//        arr = gIntent.getByteArrayExtra("logo");
//        Bitmap logo = BitmapFactory.decodeByteArray(arr, 0, arr.length);
//        rate = gIntent.getIntExtra("rate", 0);
//
//        // 붙이기
//        tv_cp_name.setText(name);
//        tv_certiLeftDate.setText(Dday);
//        tv_certiFrequency.setText(period + ", " + frequency);
//        iv_logo.setImageBitmap(logo);

        fragmentInitialization();

        // 뒤로가기 버튼 이벤트
        bt_back = (ImageButton)findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // 인증하기 버튼 누르면 인증할 수 있는 페이지로 넘어간다.
        certi_button = (Button)findViewById(R.id.certi_button);
        certi_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // second certification page로 넘어간다
                Intent intent = new Intent(getApplicationContext(), Second_Certification_Page.class);
                intent.putExtra("campaignCode", campaignCode);
                startActivity(intent);
            }
        });
    }

//    // 총 인증해야 하는 갯수 구하는 메소드
//    public int photoN(String period, String frequency) {
//        int periodN = Integer.parseInt(period.substring(0, period.length()-1));
//        int frequencyN = Integer.parseInt(frequency.substring(2, frequency.length()-1));
//        return periodN * frequencyN;
//    }

    // fragment에 정보 입력
    void fragmentInitialization() {
        if(tabLayout.getSelectedTabPosition() == 0) {
//            int count = photoN(period, frequency);

            Bundle bundle1 = new Bundle();
            bundle1.putInt("rate", certiRate);
            bundle1.putInt("count", certiCount);
            bundle1.putString("campaignCode", campaignCode);

            fragmentCertiRate.setArguments(bundle1);
        }
        else if(tabLayout.getSelectedTabPosition() == 1) {
            Bundle bundle2 = new Bundle();
            bundle2.putString("campaignCode", campaignCode);
            fragmentCertiPhotos.setArguments(bundle2);
        }
    }

    // imageView에서 bitmap을 byte[]로 변환
    public byte[] bitmapToByteArray(ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable)imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}