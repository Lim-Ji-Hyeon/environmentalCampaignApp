package com.example.environmentalcampaign.set_up_page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.environmentalcampaign.search_page.SearchViewItem;
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

    private final int GALLERY_CODE1 = 111;
    private final int GALLERY_CODE2 = 222;
    private final int GALLERY_CODE3 = 333;
    private final int GALLERY_CODE4 = 444;

    private FirebaseDatabase database;
    private DatabaseReference temporaryRef, databaseReference, searchDatabaseRef;
    FirebaseStorage storage;
    String uid, datetime;

    String imagePath1;
    String imagePath2;
    String imagePath3;
    String imagePath4;

    private int bookmarkN=0;


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

        storage = FirebaseStorage.getInstance();

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

        // uid ????????????
        Intent preIntent = getIntent();
        uid = preIntent.getStringExtra("uid");

        // ?????? ?????????
        tv_pre = (TextView)findViewById(R.id.tv_pre);
        tv_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { onBackPressed(); }
        });

        // ?????? ?????????
        tv_next = (TextView)findViewById(R.id.tv_next);
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkEditText(et_cp_way)) {
                    Toast.makeText(setup3.this, "????????? ??????????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                }
                else if((checkImage(iv_rightPhoto) || checkEditText(et_rightPhotoInfo)) && (checkImage(iv_rightPhoto2) || checkEditText(et_rightPhotoInfo2))) {
                    Toast.makeText(setup3.this, "????????? ??????????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                }
                else {
                    setup_alert();
                }
            }
        });

        // ???????????? ?????? ?????????
        bt_back = (ImageButton)findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SetUpCampaignPage.class);
                startActivity(intent);
            }
        });

    }

    // ????????? ?????? ??????
    void setup_alert() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(setup3.this)
                .setTitle("????????? ??????")
                .setMessage("\n????????? ???????????? ???????????? ?????????????????????????")
                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        datetime = getTimeMilli();
                        setup();

                        Toast.makeText(setup3.this, "???????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("??????", null);
        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    // ????????? ?????? ???????????? intent
    void setup() {
        // setup3??? ?????? ????????????
        String rPhoto1="", rPhoto2="", wPhoto1="", wPhoto2="";
        ImageView[] photos = {iv_rightPhoto, iv_rightPhoto2, iv_wrongPhoto, iv_wrongPhoto2};
        for(int i = 0; i < photos.length; i++) {
            if(!checkImage(photos[i])) {
                switch (i) {
                    case 0 :
                        rPhoto1 = makeToken(imagePath1);
                        break;
                    case 1 :
                        rPhoto2 = makeToken(imagePath2);
                        break;
                    case 2 :
                        wPhoto1 = makeToken(imagePath3);
                        break;
                    case 3 :
                        wPhoto2 = makeToken(imagePath4);
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

        // TemporarySave - uid ??? ????????? ?????? ????????????
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
        temporaryRef.child("bookmarkN").setValue(bookmarkN);

        temporaryRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                CampaignItem campaignItem = dataSnapshot.getValue(CampaignItem.class);

                RecyclerViewItem recyclerViewItem = new RecyclerViewItem();
                recyclerViewItem.setTitle(campaignItem.getTitle());
                recyclerViewItem.setImage(campaignItem.getLogo());
                recyclerViewItem.setCampaignCode(campaignItem.getDatetime());
                recyclerViewItem.setReCampaignN(campaignItem.getReCampaignN());

                SetUpCampaignItem setUpCampaignItem = new SetUpCampaignItem();
                setUpCampaignItem.setCampaignCode(datetime);

                SearchViewItem searchViewItem = new SearchViewItem();
                searchViewItem.setParticipantsN(campaignItem.getParticipantsN());
                searchViewItem.setFrequency(campaignItem.getFrequency());
                searchViewItem.setCampaignName(campaignItem.getTitle());
                searchViewItem.setCampaignLogo(campaignItem.getLogo());
                searchViewItem.setCampaignCode(datetime);
                database.getReference("environmentalCampaign").child("Search").child(datetime).setValue(searchViewItem);


                // ????????????????????? ??????
                databaseReference = database.getReference("environmentalCampaign");
                databaseReference.child("SetUpCampaign").child(uid).child(datetime).setValue(setUpCampaignItem); // ?????? ????????? ????????? ???????????? ??????.
                databaseReference.child("HomeCampaign").child(datetime).setValue(recyclerViewItem); // ???????????? ???????????????, ?????????????????? ??????.
                databaseReference.child("Campaign").child(datetime).child("campaign").setValue(campaignItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // ????????? ????????? ???????????? ??????
                        Intent intent = new Intent(getApplicationContext(), CampaignInformation.class);

                        // setup????????? ????????????
                        intent.putExtra("signal", "setup");
                        intent.putExtra("datetime", datetime);

                        startActivity(intent);
                    }
                });

                databaseReference.child("HomeCampaign").child(datetime).setValue(recyclerViewItem);
            }
        });
    }

    // ????????? ???????????? ?????? ????????? 1
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GALLERY_CODE1:
                    sendPicture1(data.getData()); //??????????????? ????????????
                    break;
                case GALLERY_CODE2:
                    sendPicture2(data.getData()); //??????????????? ????????????
                    break;
                case GALLERY_CODE3:
                    sendPicture3(data.getData()); //??????????????? ????????????
                    break;
                case GALLERY_CODE4:
                    sendPicture4(data.getData()); //??????????????? ????????????
                    break;
                default:
                    break;
            }
            // Storage??? ????????????
            StorageReference storageRef = storage.getReference();

            Uri file = Uri.fromFile(new File(getRealPathFromURI(data.getData())));
            StorageReference riversRef = storageRef.child("Campaign/").child("images/"+file.getLastPathSegment());
            UploadTask uploadTask = riversRef.putFile(file);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            }).addOnSuccessListener
                    (new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        }
                    });
        }
        imageInfo();
    }

    // ????????? ???????????? ?????? ????????? 2-1
    private void sendPicture1(Uri imgUri) {
        imagePath1 = getRealPathFromURI(imgUri); // path ??????
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath1);//????????? ?????? ??????????????? ??????
        iv_rightPhoto.setImageBitmap(rotate(bitmap, exifDegree));//????????? ?????? ????????? ??????
    }

    // ????????? ???????????? ?????? ????????? 2-2
    private void sendPicture2(Uri imgUri) {
        imagePath2 = getRealPathFromURI(imgUri); // path ??????
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath2);//????????? ?????? ??????????????? ??????
        iv_rightPhoto2.setImageBitmap(rotate(bitmap, exifDegree));//????????? ?????? ????????? ??????
    }

    // ????????? ???????????? ?????? ????????? 2-3
    private void sendPicture3(Uri imgUri) {
        imagePath3 = getRealPathFromURI(imgUri); // path ??????
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath3);//????????? ?????? ??????????????? ??????
        iv_wrongPhoto.setImageBitmap(rotate(bitmap, exifDegree));//????????? ?????? ????????? ??????
    }

    // ????????? ???????????? ?????? ????????? 2-4
    private void sendPicture4(Uri imgUri) {
        imagePath4 = getRealPathFromURI(imgUri); // path ??????
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath4);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath4);//????????? ?????? ??????????????? ??????
        iv_wrongPhoto2.setImageBitmap(rotate(bitmap, exifDegree));//????????? ?????? ????????? ??????
    }

    // ????????? ???????????? ?????? ????????? 3
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

    // ????????? ???????????? ?????? ????????? 4
    private Bitmap rotate(Bitmap src, float degree) {
        // Matrix ?????? ??????
        Matrix matrix = new Matrix();
        // ?????? ?????? ??????
        matrix.postRotate(degree);
        // ???????????? Matrix ??? ???????????? Bitmap ?????? ??????
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }

    // ????????? ???????????? ?????? ????????? 5
    private String getRealPathFromURI(Uri contentUri) {
        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        return cursor.getString(column_index);
    }

    // edittext??? ??????????????? ??????
    boolean checkEditText(EditText editText) {
        return editText.getText().toString().equals("") || editText.getText().toString()==null;
    }

    // ????????? ??????????????? ??????(??????????????? false)
    boolean checkImage(ImageView imageView) {
        BitmapDrawable imageDrawable = (BitmapDrawable)imageView.getDrawable();
        Bitmap imageBitmap = imageDrawable.getBitmap();

        BitmapDrawable checkDrawable = (BitmapDrawable)getResources().getDrawable(R.drawable.add_image);
        Bitmap checkBitmap = checkDrawable.getBitmap();

        return imageBitmap.equals(checkBitmap);
    }

    // ????????? ?????? ?????? ???????????????
    void imageInfo() {
        // ????????? ???????????? ?????? ????????? ?????? ????????? ??? ????????? ??????.
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

    // imageView?????? bitmap??? byte[]??? ??????
    public byte[] bitmapToByteArray(ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable)imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    // byte[]??? String?????? ??????
    public static String byteArrayToBinaryString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < b.length; ++i) {
            sb.append(byteToBinaryString(b[i]));
        }
        return sb.toString();
    }

    // byte??? String?????? ??????
    public static String byteToBinaryString(byte n) {
        StringBuilder sb = new StringBuilder("00000000");
        for(int bit = 0; bit < 8; bit++) {
            if(((n >> bit) & 1) > 0) {
                sb.setCharAt(7 - bit, '1');
            }
        }
        return sb.toString();
    }

    // ???????????? ?????????
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

    // ?????? ?????????
    String makeToken(String imagePath) {
        int index = imagePath.lastIndexOf("/");
        String imageName = imagePath.substring(index+1);
        String token = "https://firebasestorage.googleapis.com/v0/b/environmental-campaign.appspot.com/o/Campaign%2Fimages%2F"
                + imageName + "?alt=media";

        return token;
    }
}