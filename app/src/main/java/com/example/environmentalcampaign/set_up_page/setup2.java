package com.example.environmentalcampaign.set_up_page;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.cp_info.CampaignItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class setup2 extends AppCompatActivity {

    ImageButton bt_back;
    TextView tv_pre, tv_next;

    EditText et_cp_info;
    ImageView iv_cp_info1, iv_cp_info2, iv_cp_info3, iv_cp_info4, iv_cp_info5, checkImage;

    private final int GALLERY_CODE1 = 111;
    private final int GALLERY_CODE2 = 222;
    private final int GALLERY_CODE3 = 333;
    private final int GALLERY_CODE4 = 444;
    private final int GALLERY_CODE5 = 555;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);

        et_cp_info = (EditText)findViewById(R.id.et_cp_info);
        iv_cp_info1 = (ImageView)findViewById(R.id.iv_cp_info1);
        iv_cp_info2 = (ImageView)findViewById(R.id.iv_cp_info2);
        iv_cp_info3 = (ImageView)findViewById(R.id.iv_cp_info3);
        iv_cp_info4 = (ImageView)findViewById(R.id.iv_cp_info4);
        iv_cp_info5 = (ImageView)findViewById(R.id.iv_cp_info5);

        iv_cp_info1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image_1/*");
                startActivityForResult(intent, GALLERY_CODE1);
            }
        });
        iv_cp_info2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image_2/*");
                startActivityForResult(intent, GALLERY_CODE2);
            }
        });
        iv_cp_info3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image_3/*");
                startActivityForResult(intent, GALLERY_CODE3);
            }
        });
        iv_cp_info4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image_4/*");
                startActivityForResult(intent, GALLERY_CODE4);
            }
        });
        iv_cp_info5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image_5/*");
                startActivityForResult(intent, GALLERY_CODE5);
            }
        });

        // uid 불러오기
        Intent preIntent = getIntent();
        String uid = preIntent.getStringExtra("uid");

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
                if(checkEditText(et_cp_info)) {
                    Toast.makeText(setup2.this, "캠페인 설명을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    String infoImage1="", infoImage2="", infoImage3="", infoImage4="", infoImage5="";
                    ImageView[] infoImages = {iv_cp_info1, iv_cp_info2, iv_cp_info3, iv_cp_info4, iv_cp_info5};
                    for(int i = 0; i < infoImages.length; i++) {
                        if(!checkImage(infoImages[i])) {
                            switch (i) {
                                case 0 :
                                    infoImage1 = byteArrayToBinaryString(bitmapToByteArray(iv_cp_info1));
                                    break;
                                case 1 :
                                    infoImage2 = byteArrayToBinaryString(bitmapToByteArray(iv_cp_info2));
                                    break;
                                case 2 :
                                    infoImage3 = byteArrayToBinaryString(bitmapToByteArray(iv_cp_info3));
                                    break;
                                case 3 :
                                    infoImage4 = byteArrayToBinaryString(bitmapToByteArray(iv_cp_info4));
                                    break;
                                case 4 :
                                    infoImage5 = byteArrayToBinaryString(bitmapToByteArray(iv_cp_info5));
                                    break;
                            }
                        }
                    }

                    // TemporarySave - uid에 저장
                    database = FirebaseDatabase.getInstance();
                    databaseReference = database.getReference("environmentalCampaign").child("TemporarySave").child(uid);
                    databaseReference.child("cpInfo").setValue(et_cp_info.getText().toString());
                    databaseReference.child("infoImage1").setValue(infoImage1);
                    databaseReference.child("infoImage2").setValue(infoImage2);
                    databaseReference.child("infoImage3").setValue(infoImage3);
                    databaseReference.child("infoImage4").setValue(infoImage4);
                    databaseReference.child("infoImage5").setValue(infoImage5);

                    Intent intent = new Intent(getApplicationContext(), setup3.class);
                    intent.putExtra("uid", uid);
                    startActivity(intent);
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

    // 갤러리 연동하기 위한 메소드 1
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GALLERY_CODE1:
                    sendPicture1(data.getData()); //갤러리에서 가져오기
                    break;
                case GALLERY_CODE2:
                    sendPicture2(data.getData()); //갤러리에서 가져오기
                    break;
                case GALLERY_CODE3:
                    sendPicture3(data.getData()); //갤러리에서 가져오기
                    break;
                case GALLERY_CODE4:
                    sendPicture4(data.getData()); //갤러리에서 가져오기
                    break;
                case GALLERY_CODE5:
                    sendPicture5(data.getData()); //갤러리에서 가져오기
                    break;
                default:
                    break;
            }
        }
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
        iv_cp_info1.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기
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
        iv_cp_info2.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기
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
        iv_cp_info3.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기
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
        iv_cp_info4.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기
    }

    // 갤러리 연동하기 위한 메소드 2-5
    private void sendPicture5(Uri imgUri) {
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
        iv_cp_info5.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기
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