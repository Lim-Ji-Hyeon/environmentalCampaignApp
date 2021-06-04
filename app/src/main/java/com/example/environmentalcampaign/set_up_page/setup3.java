package com.example.environmentalcampaign.set_up_page;

import androidx.annotation.NonNull;
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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.cp_info.CampaignInformation;
import com.example.environmentalcampaign.cp_info.CampaignItem;
import com.example.environmentalcampaign.home.RecyclerViewItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class setup3 extends AppCompatActivity {

    ImageButton bt_back;
    TextView tv_pre, tv_next;

    EditText et_cp_way, et_rightPhotoInfo, et_rightPhotoInfo2, et_wrongPhotoInfo, et_wrongPhotoInfo2;
    ImageView iv_rightPhoto, iv_rightPhoto2, iv_wrongPhoto, iv_wrongPhoto2;

    FirebaseStorage storage;

    private final int GALLERY_CODE1 = 111;
    private final int GALLERY_CODE2 = 222;
    private final int GALLERY_CODE3 = 333;
    private final int GALLERY_CODE4 = 444;

    private FirebaseDatabase database;
    private DatabaseReference temporaryRef, databaseReference;
    String uid, datetime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);

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

        iv_rightPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_CODE1);
            }
        });
        iv_rightPhoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_CODE2);
            }
        });
        iv_wrongPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_CODE3);
            }
        });
        iv_wrongPhoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_CODE4);
            }
        });

        // uid 불러오기
        Intent preIntent = getIntent();
        uid = preIntent.getStringExtra("uid");

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
                else if((checkImage(iv_rightPhoto) || checkEditText(et_rightPhotoInfo)) && (checkImage(iv_rightPhoto2) || checkEditText(et_rightPhotoInfo2))) {
                    Toast.makeText(setup3.this, "올바른 인증방법을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
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

    }

    // 마지막 확인 팝업
    void setup_alert() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(setup3.this)
                .setTitle("캠페인 개설")
                .setMessage("\n작성한 내용대로 캠페인을 개설하시겠습니까?")
                .setPositiveButton("개설", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        datetime = getTimeMilli();
                        setup();

                        Toast.makeText(setup3.this, "캠페인이 개설되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("취소", null);
        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    // 캠페인 정보 페이지로 intent
    void setup() {
        // setup3의 정보 가져오기
        String rPhoto1="", rPhoto2="", wPhoto1="", wPhoto2="";
        ImageView[] photos = {iv_rightPhoto, iv_rightPhoto2, iv_wrongPhoto, iv_wrongPhoto2};
        for(int i = 0; i < photos.length; i++) {
            if(!checkImage(photos[i])) {
                switch (i) {
                    case 0 :
                        rPhoto1 = byteArrayToBinaryString(bitmapToByteArray(iv_rightPhoto));
                        break;
                    case 1 :
                        rPhoto2 = byteArrayToBinaryString(bitmapToByteArray(iv_rightPhoto2));
                        break;
                    case 2 :
                        wPhoto1 = byteArrayToBinaryString(bitmapToByteArray(iv_wrongPhoto));
                        break;
                    case 3 :
                        wPhoto2 = byteArrayToBinaryString(bitmapToByteArray(iv_wrongPhoto2));
                        break;
                }
            }
        }

        String rInfo1="", rInfo2="", wInfo1="", wInfo2="";
        EditText[] photoInfos = {et_rightPhotoInfo, et_rightPhotoInfo2, et_wrongPhotoInfo, et_wrongPhotoInfo2};
        for(int i = 0; i < photoInfos.length; i++) {
            if(!checkEditText(photoInfos[i])) {
                switch (i) {
                    case 0 :
                        rInfo1 = et_rightPhotoInfo.getText().toString();
                        break;
                    case 1 :
                        rInfo2 = et_rightPhotoInfo2.getText().toString();
                        break;
                    case 2 :
                        wInfo1 = et_wrongPhotoInfo.getText().toString();
                        break;
                    case 3 :
                        wInfo2 = et_wrongPhotoInfo2.getText().toString();
                        break;
                }
            }
        }

        // TemporarySave - uid 에 나머지 내용 저장하기
        database = FirebaseDatabase.getInstance();
        temporaryRef = database.getReference("environmentalCampaign").child("TemporarySave").child(uid);
        temporaryRef.child("wayInfo").setValue(et_cp_way.getText().toString());
        temporaryRef.child("datetime").setValue(datetime);
        temporaryRef.child("participantsN").setValue(0);
        temporaryRef.child("reCampaignN").setValue(0);
        temporaryRef.child("rightPhoto1").setValue(rPhoto1);
        temporaryRef.child("rightPhoto2").setValue(rPhoto2);
        temporaryRef.child("wrongPhoto1").setValue(wPhoto1);
        temporaryRef.child("wrongPhoto2").setValue(wPhoto2);
        temporaryRef.child("rightPhotoInfo1").setValue(rInfo1);
        temporaryRef.child("rightPhotoInfo2").setValue(rInfo2);
        temporaryRef.child("wrongPhotoInfo1").setValue(wInfo1);
        temporaryRef.child("wrongPhotoInfo2").setValue(wInfo2);

        temporaryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CampaignItem campaignItem = snapshot.getValue(CampaignItem.class);
                RecyclerViewItem recyclerViewItem = new RecyclerViewItem();
                recyclerViewItem.setTitle(campaignItem.getTitle());
                recyclerViewItem.setImage(campaignItem.getLogo());
                recyclerViewItem.setCampaignCode(campaignItem.getDatetime());
                recyclerViewItem.setReCampaignN(campaignItem.getReCampaignN());

                // 데이터베이스에 삽입
                databaseReference = database.getReference("environmentalCampaign");
                databaseReference.child("SetUpCampaign").child(uid).child(datetime).child("campaignCode").setValue(datetime); // 내가 개설한 캠페인 확인하기 위함.
                databaseReference.child("HomeCampaign").child(datetime).setValue(recyclerViewItem); // 홈화면에 신규캠페인, 인기캠페인을 위함.
                databaseReference.child("Campaign").child(datetime).child("campaign").setValue(campaignItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // 데이터 삽입이 성공하면 실행
                        Intent intent = new Intent(getApplicationContext(), CampaignInformation.class);

                        // setup이라는 신호주기
                        intent.putExtra("signal", "setup");
                        intent.putExtra("datetime", datetime);

                        startActivity(intent);
                    }
                });

                databaseReference.child("HomeCampaign").child(datetime).setValue(recyclerViewItem);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Log.e("SetUpActivity", String.valueOf(error.toException())); //에러문 출력
            }
        });
    }

    // 갤러리 연동하기 위한 메소드 1
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GALLERY_CODE1:
                    sendPicture1(data.getData()); //갤러리에서 가져오기

//                    // Storage에 저장하기
//                    StorageReference storageRef1 = storage.getReference();
//
//                    Uri file1 = Uri.fromFile(new File(getRealPathFromURI(data.getData())));
//                    StorageReference riversRef1 = storageRef1.child("Campaign/").child("images/"+file1.getLastPathSegment());
//                    UploadTask uploadTask1 = riversRef1.putFile(file1);
//
//                    uploadTask1.addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception exception) {
//                        }
//                    }).addOnSuccessListener
//                            (new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                }
//                            });
                    break;
                case GALLERY_CODE2:
                    sendPicture2(data.getData()); //갤러리에서 가져오기

                    // Storage에 저장하기
                    StorageReference storageRef2 = storage.getReference();

                    Uri file2 = Uri.fromFile(new File(getRealPathFromURI(data.getData())));
                    StorageReference riversRef2 = storageRef2.child("Campaign/").child("images/"+file2.getLastPathSegment());
                    UploadTask uploadTask2 = riversRef2.putFile(file2);

                    uploadTask2.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                        }
                    }).addOnSuccessListener
                            (new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                }
                            });
                    break;
                case GALLERY_CODE3:
                    sendPicture3(data.getData()); //갤러리에서 가져오기

//                    // Storage에 저장하기
//                    StorageReference storageRef3 = storage.getReference();
//
//                    Uri file3 = Uri.fromFile(new File(getRealPathFromURI(data.getData())));
//                    StorageReference riversRef3 = storageRef3.child("Campaign/").child("images/"+file3.getLastPathSegment());
//                    UploadTask uploadTask3 = riversRef3.putFile(file3);
//
//                    uploadTask3.addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception exception) {
//                        }
//                    }).addOnSuccessListener
//                            (new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                }
//                            });
                    break;
                case GALLERY_CODE4:
                    sendPicture4(data.getData()); //갤러리에서 가져오기

                    // Storage에 저장하기
                    StorageReference storageRef4 = storage.getReference();

                    Uri file4 = Uri.fromFile(new File(getRealPathFromURI(data.getData())));
                    StorageReference riversRef4 = storageRef4.child("Campaign/").child("images/"+file4.getLastPathSegment());
                    UploadTask uploadTask4 = riversRef4.putFile(file4);

                    uploadTask4.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                        }
                    }).addOnSuccessListener
                            (new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                }
                            });
                    break;
                default:
                    break;
            }
        }
        imageInfo();
    }

    // 갤러리 연동하기 위한 메소드 2-1
    private void sendPicture1(Uri imgUri) {
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
        iv_rightPhoto.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기
    }

    // 갤러리 연동하기 위한 메소드 2-2
    private void sendPicture2(Uri imgUri) {
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
        iv_rightPhoto2.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기
    }

    // 갤러리 연동하기 위한 메소드 2-3
    private void sendPicture3(Uri imgUri) {
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
        iv_wrongPhoto.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기
    }

    // 갤러리 연동하기 위한 메소드 2-4
    private void sendPicture4(Uri imgUri) {
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
        iv_wrongPhoto2.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기
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

    // 이미지 설명 입력 가능하도록
    void imageInfo() {
        // 인증샷 선택하면 해당 인증샷 설명 입력할 수 있도록 하기.
        if(!checkImage(iv_rightPhoto)) {
            et_rightPhotoInfo.setFocusableInTouchMode(true);
            et_rightPhotoInfo.setClickable(true);
            et_rightPhotoInfo.setFocusable(true);
        } else {
            et_rightPhotoInfo.setClickable(false);
            et_rightPhotoInfo.setFocusable(false);
        }
        if(!checkImage(iv_rightPhoto2)) {
            et_rightPhotoInfo2.setFocusableInTouchMode(true);
            et_rightPhotoInfo2.setClickable(true);
            et_rightPhotoInfo2.setFocusable(true);
        } else {
            et_rightPhotoInfo2.setClickable(false);
            et_rightPhotoInfo2.setFocusable(false);
        }
        if(!checkImage(iv_wrongPhoto)) {
            et_wrongPhotoInfo.setFocusableInTouchMode(true);
            et_wrongPhotoInfo.setClickable(true);
            et_wrongPhotoInfo.setFocusable(true);
        } else {
            et_wrongPhotoInfo.setClickable(false);
            et_wrongPhotoInfo.setFocusable(false);
        }
        if(!checkImage(iv_wrongPhoto2)) {
            et_wrongPhotoInfo2.setFocusableInTouchMode(true);
            et_wrongPhotoInfo2.setClickable(true);
            et_wrongPhotoInfo2.setFocusable(true);
        } else {
            et_wrongPhotoInfo2.setClickable(false);
            et_wrongPhotoInfo2.setFocusable(false);
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