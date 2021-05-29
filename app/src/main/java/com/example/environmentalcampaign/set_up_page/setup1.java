package com.example.environmentalcampaign.set_up_page;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.cp_info.CampaignItem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class setup1 extends AppCompatActivity {

    ImageButton bt_back;
    TextView tv_next;

    ImageView iv_cp_logo;
    EditText et_cp_name;
    TextView tv_frequency, tv_period;

    TextView tv_frequency_select, tv_period_select;
    TextView tv_sDate, tv_eDate;
    SimpleDateFormat simpleDateFormat;

    private final int GALLERY_CODE = 777;

    // 다른 액티비티에서 접근하기 위함.
    public static Context context_setup1;
    public CampaignItem campaignItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);

        context_setup1 = this;

        iv_cp_logo = (ImageView)findViewById(R.id.iv_cp_logo);
        et_cp_name = (EditText)findViewById(R.id.et_cp_name);
        tv_frequency = (TextView)findViewById(R.id.tv_frequency);
        tv_period = (TextView)findViewById(R.id.tv_period);

        // 대표사진 클릭시 갤러리앱에서 이미지 선택
        iv_cp_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_CODE);
            }
        });


        // 오늘 날짜 구하기
        Calendar cal = Calendar.getInstance(Locale.KOREA);
        simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
        String today = simpleDateFormat.format(cal.getTime());

        tv_sDate = (TextView)findViewById(R.id.tv_sDate);
        tv_sDate.setText(today);
        tv_eDate = (TextView)findViewById(R.id.tv_eDate);

        // 인증 빈도 선택 버튼
        tv_frequency_select = (TextView)findViewById(R.id.tv_frequency_select);
        tv_frequency_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFrequency();
            }
        });

        // 인증 기간 선택 버튼
        tv_period_select = (TextView)findViewById(R.id.tv_period_select);
        tv_period_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPeriod();
            }
        });

        // 다음 페이지
        tv_next = (TextView)findViewById(R.id.tv_next);
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkImage(iv_cp_logo)) {
                    Toast.makeText(setup1.this, "이미지를 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
                else
                    if(checkEditText(et_cp_name)) {
                    Toast.makeText(setup1.this, "캠페인 이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(tv_frequency.getVisibility() == View.GONE) {
                    Toast.makeText(setup1.this, "인증 빈도를 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(tv_period.getVisibility() == View.GONE) {
                    Toast.makeText(setup1.this, "인증 기간을 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    // CampaignItem 객체 생성해서 내용 추가하기
                    campaignItem = new CampaignItem();

                    campaignItem.setLogo(byteArrayToBinaryString(bitmapToByteArray(iv_cp_logo)));
                    campaignItem.setTitle(et_cp_name.getText().toString());
                    campaignItem.setFrequency(tv_frequency.getText().toString());
                    campaignItem.setPeriod(tv_period.getText().toString());

                    Intent intent = new Intent(getApplicationContext(), setup2.class);

//                    // 이미지 Bitmap 변환
//                    byte[] byteArray = bitmapToByteArray(iv_cp_logo);
//
//                    intent.putExtra("logo", byteArray);
//                    intent.putExtra("cp_name", et_cp_name.getText().toString());
//                    intent.putExtra("frequency", tv_frequency.getText().toString());
//                    intent.putExtra("period", tv_period.getText().toString());
////                intent.putExtra("eDate", tv_eDate.getText().toString());

                    startActivity(intent);
                }
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

    // 인증 빈도 선택 이벤트
    void selectFrequency() {
        final CharSequence[] fItems = {"주 1일", "주 2일", "주 3일", "주 4일", "주 5일", "주 6일", "주 7일"};

        AlertDialog.Builder fDialog = new AlertDialog.Builder(this);
        fDialog.setTitle("인증 빈도를 선택하세요")
                .setItems(fItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tv_frequency.setText(fItems[i]);
                        tv_frequency.setVisibility(View.VISIBLE);
                    }
                }).show();
    }

    // 인증 기간 선택 이벤트
    void selectPeriod() {
        final CharSequence[] pItems = {"2주", "3주", "4주", "5주", "6주", "7주", "8주", "9주", "10주"};

        AlertDialog.Builder pDialog = new AlertDialog.Builder(this);
        pDialog.setTitle("인증 기간을 선택하세요")
                .setItems(pItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tv_period.setText(pItems[i]);
                        tv_period.setVisibility(View.VISIBLE);
                        tv_eDate.setText(endDate(tv_period.getText().toString()));
                        tv_eDate.setVisibility(View.VISIBLE);
                    }
                }).show();
    }

    // 인증 기간 마지막 날짜 구하기
    String endDate(String period) {
        int len = period.length();
        int n = Integer.parseInt(period.substring(0, len-1));

        Calendar eCal = Calendar.getInstance(Locale.KOREA);
        eCal.add(Calendar.DATE, 7*n);
        String end = simpleDateFormat.format(eCal.getTime());

        return end;
    }

    // 이미지 선택했는지 확인(선택했으면 false)
    boolean checkImage(ImageView imageView) {
        BitmapDrawable imageDrawable = (BitmapDrawable)imageView.getDrawable();
        Bitmap imageBitmap = imageDrawable.getBitmap();

        BitmapDrawable checkDrawable = (BitmapDrawable)getResources().getDrawable(R.drawable.add_image);
        Bitmap checkBitmap = checkDrawable.getBitmap();

        return imageBitmap.equals(checkBitmap);
    }

    // edittext를 입력했는지 확인
    boolean checkEditText(EditText editText) {
        return editText.getText().toString().equals("") || editText.getText().toString()==null;
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

    // 갤러리 연동하기 위한 메소드 1
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GALLERY_CODE:
                    sendPicture(data.getData()); //갤러리에서 가져오기
                    break;
                default:
                    break;
            }
        }
    }

    // 갤러리 연동하기 위한 메소드 2
    private void sendPicture(Uri imgUri) {
        String imagePath = getRealPathFromURI(imgUri); // path 경로
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);//경로를 통해 비트맵으로 전환
        iv_cp_logo.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기
    }

    // 갤러리 연동하기 위한 메소드 3
    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    // 갤러리 연동하기 위한 메소드 4
    private Bitmap rotate(Bitmap src, float degree) {
        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }

    // 갤러리 연동하기 위한 메소드 5
    private String getRealPathFromURI(Uri contentUri) {
        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        return cursor.getString(column_index);
    }
}