package com.example.environmentalcampaign.certification_page;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.bookmark.BookMark;
import com.example.environmentalcampaign.feed.FeedCommentActivity;
import com.example.environmentalcampaign.feed.FeedCommentItem;
import com.example.environmentalcampaign.feed.FeedItem;
import com.example.environmentalcampaign.feed.FeedPage;
import com.example.environmentalcampaign.home.HomeActivity;
import com.example.environmentalcampaign.home.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.os.Environment.DIRECTORY_PICTURES;

public class Second_Certification_Page extends AppCompatActivity {

    private final int CAMERA_CODE = 1111;
    private String currentPhotoPath;
    String mImageCaptureName;
    private Uri photoUri;
    String imagePath;
    private final int GALLERY_CODE=1112;

    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabaseRef;//실시간 데이터베이스
    String contents, certiImg, publisher, certi_date;
    FirebaseStorage storage;
    private FirebaseUser firebaseUser;

    String campaignCode, title, uid, userImg, nickname;
    TextView tv_title;

    ImageView img1;
    Button btn1;
    EditText editText;
    ImageButton bt_back;
    Button camera_btn;
    Button gallery_btn;

    private int heartN=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second__certification__page);

        Intent gIntent = getIntent();
        campaignCode = gIntent.getStringExtra("campaignCode");
        title = gIntent.getStringExtra("title");

        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText(title);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("environmentalCampaign");
        firebaseUser = mFirebaseAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();
        uid = firebaseUser.getUid();

        img1 = (ImageView)findViewById(R.id.certi_image);
        btn1 = (Button)findViewById(R.id.certi_button);
        editText = (EditText)findViewById(R.id.certi_text);
        camera_btn = (Button)findViewById(R.id.camera_btn);
        gallery_btn = (Button)findViewById(R.id.gallery_btn);

        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String state = Environment.getExternalStorageState();
                if (Environment.MEDIA_MOUNTED.equals(state)) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        File photoFile = null;
                        try {
                            photoFile = createImageFile();
                        }
                        catch (IOException ex) {

                        }
                        if (photoFile != null) {
                            photoUri = FileProvider.getUriForFile(Second_Certification_Page.this, getPackageName(), photoFile);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                            startActivityForResult(intent, CAMERA_CODE);
                        }
                    }
                }

            }
        });

        gallery_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_CODE);

            }
        });

        // 현재 사용자의 사진과 닉네임을 가져온다.
        mDatabaseRef.child("UserAccount").child(uid).addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                UserAccount userAccount = snapshot.getValue(UserAccount.class);
                userImg = userAccount.getProfileImg();
                nickname = userAccount.getNickName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Log.e("SecondCertificationPage", String.valueOf(error.toException())); //에러문 출력
            }
        });



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // realtime database에 저장한다.
                publisher = firebaseUser.getUid();
                contents = editText.getText().toString();

                // 현재시간 가져오기
                long now = System.currentTimeMillis();
                Date mDate = new Date(now);
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String getTime = simpleDate.format(mDate);

                // 카메라로 찍으면 photoUri를 가져오고, 갤러리로 가져왔다면 imagePath를 이용한다.
                if(photoUri != null){
                    certiImg = makeToken(photoUri.toString());
                }else{
                    certiImg = makeToken(imagePath);
                }

                Certi_Info certi_info = new Certi_Info();
                certi_info.setPublisher(publisher);
                certi_info.setContents(contents);
                certi_info.setCerti_date(getTime);
                certi_info.setPhotoUrl(certiImg);

                FeedItem feedItem = new FeedItem();
                feedItem.setImage(certiImg);
                feedItem.setPublisher(publisher);
                feedItem.setDate(getTime);
                feedItem.setContents(contents);
                feedItem.setHeartN(heartN);

                FeedCommentItem feedCommentItem = new FeedCommentItem();
                feedCommentItem.setUid(publisher);
                feedCommentItem.setUserImage(userImg);
                feedCommentItem.setNickname(nickname);
                feedCommentItem.setPublisher(publisher);
                feedCommentItem.setFeedDate(getTime);
                feedCommentItem.setComment(contents);
                feedCommentItem.setDate(getTime);

                mDatabaseRef.child("Certification").child(publisher).child(campaignCode).child(getTime).setValue(certi_info);
                mDatabaseRef.child("Feed").child(getTime).setValue(feedItem);
                mDatabaseRef.child("Campaign").child(campaignCode).child("certifications").child(getTime).setValue(feedItem);
                mDatabaseRef.child("FeedComment").child(getTime).child(getTime).setValue(feedCommentItem);

                Toast.makeText(Second_Certification_Page.this, "인증이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CertificationPage.class);
                startActivity(intent);
                finish();

            }
        });

        // 뒤로 가기
        bt_back = (ImageButton)findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void getPictureForPhoto() {
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(currentPhotoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation;
        int exifDegree;
        if (exif != null) {
            exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            exifDegree = exifOrientationToDegrees(exifOrientation);
        } else {
            exifDegree = 0;
        }
        img1.setImageBitmap(rotate(bitmap, exifDegree)); //이미지 뷰에 비트맵 넣기
    }


    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GALLERY_CODE:
                    sendPicture(data.getData()); //갤러리에서 가져오기

                    // Storage에 저장하기
                   StorageReference storageRef = storage.getReference();

                    Uri file = Uri.fromFile(new File(getRealPathFromURI(data.getData())));
                    StorageReference riversRef = storageRef.child("Feed/").child("images/"+file.getLastPathSegment());
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
                     break;
                     case CAMERA_CODE:
                         getPictureForPhoto(); //카메라에서 가져오기
                         StorageReference storageRef1 = storage.getReference();

                         StorageReference riversRef1 = storageRef1.child("Feed/").child("images/"+photoUri.getLastPathSegment());
                         UploadTask uploadTask1 = riversRef1.putFile(photoUri);

                         // Register observers to listen for when the download is done or if it fails
                         uploadTask1.addOnFailureListener(new OnFailureListener() {
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
    }

    private void sendPicture(Uri imgUri) {
        imagePath = getRealPathFromURI(imgUri); // path 경로
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);//경로를 통해 비트맵으로 전환
        img1.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기
    }



    // 촬영한 사진을 이미지 파일로 저장하는 함수 createImageFile() 생성
    private File createImageFile() throws IOException {
        File dir = new File(Environment.getExternalStorageDirectory() + "/Android/data/com.example.environmentalcampaign/files/Pictures/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        mImageCaptureName = timeStamp + ".png";
        File storageDir = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/Android/data/com.example.environmentalcampaign/files/Pictures/" + mImageCaptureName);
        currentPhotoPath = storageDir.getAbsolutePath();
        return storageDir;
    }

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

    private Bitmap rotate(Bitmap src, float degree) {
        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }


    private String getRealPathFromURI(Uri contentUri) {
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        return cursor.getString(column_index);
    }

    // 토큰 만들기
    String makeToken(String imagePath) {
        int index = imagePath.lastIndexOf("/");
        String imageName = imagePath.substring(index+1);
        String token = "https://firebasestorage.googleapis.com/v0/b/environmental-campaign.appspot.com/o/Feed%2Fimages%2F"
                + imageName + "?alt=media";

        return token;
    }

}



