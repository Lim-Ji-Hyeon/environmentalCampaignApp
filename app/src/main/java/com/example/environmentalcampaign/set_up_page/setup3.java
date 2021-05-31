package com.example.environmentalcampaign.set_up_page;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.cp_info.CampaignInformation;
import com.example.environmentalcampaign.cp_info.CampaignItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class setup3 extends AppCompatActivity {

    ImageButton bt_back;
    TextView tv_pre, tv_next;

//    byte[] logo, infoImage1, infoImage2, infoImage3, infoImage4, infoImage5, checkImage;
//    String cp_name, frequency, period, eDate, info;

    EditText et_cp_way, et_rightPhotoInfo, et_rightPhotoInfo2, et_wrongPhotoInfo, et_wrongPhotoInfo2;
    ImageView iv_rightPhoto, iv_rightPhoto2, iv_wrongPhoto, iv_wrongPhoto2, checkImage2;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    // 다른 액티비티에서 접근하기 위함.
    public static Context context_setup3;
    public CampaignItem campaignItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);

        context_setup3 = this;

        et_cp_way = (EditText)findViewById(R.id.et_cp_way);
        iv_rightPhoto = (ImageView)findViewById(R.id.iv_rightPhoto);
        iv_rightPhoto2 = (ImageView)findViewById(R.id.iv_rightPhoto2);
        et_rightPhotoInfo = (EditText)findViewById(R.id.et_rightPhotoInfo);
        et_rightPhotoInfo2 = (EditText)findViewById(R.id.et_rightPhotoInfo2);
        iv_wrongPhoto = (ImageView)findViewById(R.id.iv_wrongPhoto);
        iv_wrongPhoto2 = (ImageView)findViewById(R.id.iv_wrongPhoto2);
        et_wrongPhotoInfo = (EditText)findViewById(R.id.et_wrongPhotoInfo);
        et_wrongPhotoInfo2 = (EditText)findViewById(R.id.et_wrongPhotoInfo2);
//        checkImage2 = (ImageView)findViewById(R.id.checkImage2);

//        // 전 페이지 내용들 불러오기
//        Intent preIntent = getIntent();
//        logo = preIntent.getByteArrayExtra("logo");
//        cp_name = preIntent.getStringExtra("cp_name");
//        frequency = preIntent.getStringExtra("frequency");
//        period = preIntent.getStringExtra("period");
////        eDate = preIntent.getStringExtra("eDate");
//        info = preIntent.getStringExtra("info");
//
//        infoImage1 = preIntent.getByteArrayExtra("infoImage1");
//        infoImage2 = preIntent.getByteArrayExtra("infoImage2");
//        infoImage3 = preIntent.getByteArrayExtra("infoImage3");
//        infoImage4 = preIntent.getByteArrayExtra("infoImage4");
//        infoImage5 = preIntent.getByteArrayExtra("infoImage5");
//        checkImage = preIntent.getByteArrayExtra("checkImage");

        // 이전 페이지
        tv_pre = (TextView)findViewById(R.id.tv_pre);
        tv_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { onBackPressed(); }
        });

        // 다음 페이지
        tv_next = (TextView)findViewById(R.id.tv_next);
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkEditText(et_cp_way)) {
                    Toast.makeText(setup3.this, "캠페인 인증방법을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
//                else if((checkImage(iv_rightPhoto) || checkEditText(et_rightPhotoInfo)) && (checkImage(iv_rightPhoto2) || checkEditText(et_rightPhotoInfo2))) {
//                    Toast.makeText(setup3.this, "올바른 인증방법을 입력해주세요.", Toast.LENGTH_SHORT).show();
//                }
                else {
                    setup_alert();
                }
            }
        });

        // 뒤로가기 버튼 이벤트
        bt_back = (ImageButton)findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SetUpCampaignPage.class);
                startActivity(intent);
            }
        });

//        // 나중에 사진 연동되면 추가할 것. 인증샷 선택하면 해당 인증샷 설명 입력할 수 있도록 하기.
//        if(!checkImage(iv_rightPhoto)) {
//            et_rightPhotoInfo.setClickable(true);
//            et_rightPhotoInfo.setFocusable(true);
//        }
//        if(!checkImage(iv_rightPhoto2)) {
//            et_rightPhotoInfo2.setClickable(true);
//            et_rightPhotoInfo2.setFocusable(true);
//        }
//        if(!checkImage(iv_wrongPhoto)) {
//            et_wrongPhotoInfo.setClickable(true);
//            et_wrongPhotoInfo.setFocusable(true);
//        }
//        if(!checkImage(iv_wrongPhoto2)) {
//            et_wrongPhotoInfo2.setClickable(true);
//            et_wrongPhotoInfo2.setFocusable(true);
//        }
    }

    // 마지막 확인 팝업
    void setup_alert() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(setup3.this)
                .setTitle("캠페인 개설")
                .setMessage("\n작성한 내용대로 캠페인을 개설하시겠습니까?")
                .setPositiveButton("개설", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setup();
                        Toast.makeText(setup3.this, "캠페인이 개설되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("취소", null);
        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    // 캠페인 정보 페이지로 intent
    void setup() {
        // setup2의 campaignItem 가져오기
        campaignItem = ((setup2)setup2.context_setup2).campaignItem;

        campaignItem.setWayInfo(et_cp_way.getText().toString());

        ImageView[] photos = {iv_rightPhoto, iv_rightPhoto2, iv_wrongPhoto, iv_wrongPhoto2};
        for(int i = 0; i < photos.length; i++) {
            if(!checkImage(photos[i])) {
                switch (i) {
                    case 0 :
                        campaignItem.setRightPhoto1(byteArrayToBinaryString(bitmapToByteArray(iv_rightPhoto)));
                        break;
                    case 1 :
                        campaignItem.setRightPhoto2(byteArrayToBinaryString(bitmapToByteArray(iv_rightPhoto2)));
                        break;
                    case 2 :
                        campaignItem.setWrongPhoto1(byteArrayToBinaryString(bitmapToByteArray(iv_wrongPhoto)));
                        break;
                    case 3 :
                        campaignItem.setWrongPhoto2(byteArrayToBinaryString(bitmapToByteArray(iv_wrongPhoto2)));
                        break;
                }
            }
        }

        EditText[] photoInfos = {et_rightPhotoInfo, et_rightPhotoInfo2, et_wrongPhotoInfo, et_wrongPhotoInfo2};
        for(int i = 0; i < photoInfos.length; i++) {
            if(!checkEditText(photoInfos[i])) {
                switch (i) {
                    case 0 :
                        campaignItem.setRightPhotoInfo1(et_rightPhotoInfo.getText().toString());
                        break;
                    case 1 :
                        campaignItem.setRightPhotoInfo2(et_rightPhotoInfo2.getText().toString());
                        break;
                    case 2 :
                        campaignItem.setWrongPhotoInfo1(et_wrongPhotoInfo.getText().toString());
                        break;
                    case 3 :
                        campaignItem.setWrongPhotoInfo2(et_wrongPhotoInfo2.getText().toString());
                        break;
                }
            }
        }

        String datetime = getTimeMilli();
        campaignItem.setDatetime(datetime);
        campaignItem.setParticipantsN(0);
        campaignItem.setReCampaignN(0);

        // 데이터베이스에 삽입
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("environmentalCampaign");
        databaseReference.child("Campaign").child(datetime).child("campaign").setValue(campaignItem);

        Intent intent = new Intent(getApplicationContext(), CampaignInformation.class);

//        // 전 페이지 내용 그대로 옮겨주기
//        intent.putExtra("logo", logo);
//        intent.putExtra("cp_name", cp_name);
//        intent.putExtra("frequency", frequency);
//        intent.putExtra("period", period);
////        intent.putExtra("eDate", eDate);
//        intent.putExtra("info", info);
//        intent.putExtra("infoImage1", infoImage1);
//        intent.putExtra("infoImage2", infoImage2);
//        intent.putExtra("infoImage3", infoImage3);
//        intent.putExtra("infoImage4", infoImage4);
//        intent.putExtra("infoImage5", infoImage5);
//        intent.putExtra("checkImage", checkImage);
//
//        // 이미지 Bitmap 변환
//        byte[] byteArray1 = bitmapToByteArray(iv_rightPhoto);
//        byte[] byteArray2 = bitmapToByteArray(iv_rightPhoto2);
//        byte[] byteArray3 = bitmapToByteArray(iv_wrongPhoto);
//        byte[] byteArray4 = bitmapToByteArray(iv_wrongPhoto2);
//        byte[] checkbyte2 = bitmapToByteArray(checkImage2);
//
//        // 현재 페이지 내용 옮기기
//        intent.putExtra("way", et_cp_way.getText().toString());
//        intent.putExtra("rPhoto1", byteArray1);
//        intent.putExtra("rPhoto2", byteArray2);
//        intent.putExtra("rInfo", et_rightPhotoInfo.getText().toString());
//        intent.putExtra("rInfo2", et_rightPhotoInfo2.getText().toString());
//        intent.putExtra("wPhoto1", byteArray3);
//        intent.putExtra("wPhoto2", byteArray4);
//        intent.putExtra("wInfo", et_wrongPhotoInfo.getText().toString());
//        intent.putExtra("rInfo2", et_wrongPhotoInfo2.getText().toString());
//        intent.putExtra("checkImage2", checkbyte2);

        // setup이라는 신호주기
        intent.putExtra("signal", "setup");
        intent.putExtra("datetime", datetime);

        startActivity(intent);
    }

    // edittext를 입력했는지 확인
    boolean checkEditText(EditText editText) {
        return editText.getText().toString().equals("") || editText.getText().toString()==null;
    }

    // 이미지 선택했는지 확인(선택했으면 false)
    boolean checkImage(ImageView imageView) {
        BitmapDrawable imageDrawable = (BitmapDrawable)imageView.getDrawable();
        Bitmap imageBitmap = imageDrawable.getBitmap();

        BitmapDrawable checkDrawable = (BitmapDrawable)getResources().getDrawable(R.drawable.add_image);
        Bitmap checkBitmap = checkDrawable.getBitmap();

        return imageBitmap.equals(checkBitmap);
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

    // byte[]를 String으로 변환
    public static String byteArrayToBinaryString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < b.length; ++i) {
            sb.append(byteToBinaryString(b[i]));
        }
        return sb.toString();
    }

    // byte를 String으로 변환
    public static String byteToBinaryString(byte n) {
        StringBuilder sb = new StringBuilder("00000000");
        for(int bit = 0; bit < 8; bit++) {
            if(((n >> bit) & 1) > 0) {
                sb.setCharAt(7 - bit, '1');
            }
        }
        return sb.toString();
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
}