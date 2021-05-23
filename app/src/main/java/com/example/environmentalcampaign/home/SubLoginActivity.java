package com.example.environmentalcampaign.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.environmentalcampaign.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class SubLoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스

    private String strNick, strProfileImg, strEmail;
    Button btn_logout, btn_check;
    private String password = "11111111";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("environmentalCampaign");

        Intent intent = getIntent();
        strNick = intent.getStringExtra("name");
        strProfileImg = intent.getStringExtra("profileImg");
        strEmail = intent.getStringExtra("email");

        TextView kakaoEmail = (TextView) findViewById(R.id.kakaoEmail);
        TextView kakaoName = (TextView) findViewById(R.id.kakaoName);
        ImageView kakaoImg = (ImageView) findViewById(R.id.kakaoImg);

        // 이메일 set
        kakaoEmail.setText(strEmail);

        // 닉네임 set
        kakaoName.setText(strNick);

        //프로필 이미지 사진 set
        Glide.with(this).load(strProfileImg).into(kakaoImg);

        btn_logout = (Button)findViewById(R.id.btn_logout);
        btn_check = (Button)findViewById(R.id.btn_check);

        // 로그아웃 처리
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        // 로그아웃 성공시 수행한다,
                        mFirebaseAuth.signOut();
                        Intent intent = new Intent(SubLoginActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish(); // 현재 엑티비티를 종료시킨다.
                    }
                });
            }
        });

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mFirebaseAuth.signInWithEmailAndPassword(strEmail, password).addOnCompleteListener(SubLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            // 로그인 성공
                            Toast.makeText(SubLoginActivity.this, "환영합니다.", Toast.LENGTH_SHORT).show();;

                            Intent intent = new Intent(SubLoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            //로그인 실패

                            //Firebase Auth 진행
                            mFirebaseAuth.createUserWithEmailAndPassword(strEmail, password).addOnCompleteListener(SubLoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful()){
                                        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                                        UserAccount account = new UserAccount();
                                        account.setIdToken(firebaseUser.getUid());
                                        account.setEmailId(firebaseUser.getEmail());
                                        account.setPassword(password);
                                        account.setNickName(strNick);
                                        account.setProfileImg(strProfileImg);

                                        // setValue : database에 insert(삽입)
                                        mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                                        mFirebaseAuth.signInWithEmailAndPassword(strEmail,password).addOnCompleteListener(SubLoginActivity.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {

                                                if(task.isSuccessful()){
                                                    Toast.makeText(SubLoginActivity.this, "환영합니다.", Toast.LENGTH_SHORT).show();;

                                                    Intent intent = new Intent(SubLoginActivity.this, HomeActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                                else{
                                                    Toast.makeText(SubLoginActivity.this, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();;
                                                }
                                            }
                                        });
                                    }
                                    else{
                                        Toast.makeText(SubLoginActivity.this, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();;
                                    }
                                }
                            });
                        }
                    }
                });







            }
        });


    }
}