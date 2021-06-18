package com.example.environmentalcampaign.feed;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.home.UserAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FeedCommentActivity extends AppCompatActivity {

    ImageView bt_back, comment_profile;
    TextView comment_check, userNickname;
    EditText comment;

    private RecyclerView commentList;
    private RecyclerView.Adapter cAdapter;
    private RecyclerView.LayoutManager cLayoutManager;
    public ArrayList<FeedCommentItem> fcArrayList;

    private FirebaseAuth cFirebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference, fcDatabaseReference, databaseReference1;
    private FirebaseUser firebaseUser;

    NestedScrollView nestedScrollView;

    String feedDate, feedPublisher, uid, commentstr, userImg, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_comment);

        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동

        userNickname = (TextView)findViewById(R.id.userNickname);
        comment_profile = (ImageView)findViewById(R.id.comment_profile);
        comment_check = (TextView)findViewById(R.id.comment_check);
        comment = (EditText)findViewById(R.id.comment);

        nestedScrollView = (NestedScrollView)findViewById(R.id.nestedScrollView);

        Intent intent = getIntent();
        feedDate = intent.getStringExtra("commentFeedDate"); //작성날짜
        feedPublisher = intent.getStringExtra("commentFeedPublisher"); //피드 게시자

        cFirebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = cFirebaseAuth.getCurrentUser();
        uid = firebaseUser.getUid();

        // RecyclerView 삽입
        commentList = findViewById(R.id.commentList);
        commentList.setHasFixedSize(true);
        cLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        commentList.setLayoutManager(cLayoutManager);

        fcArrayList = new ArrayList<>();

        fcDatabaseReference = database.getReference("environmentalCampaign").child("FeedComment").child(feedDate);
        fcDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                fcArrayList.clear(); // 기존 배열 리스트가 존재하지 않게 초기화
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){ // 반복문으로 데이터 리스트를 추출해냄.
                    FeedCommentItem feedCommentItem = snapshot.getValue(FeedCommentItem.class); //만들어둔 FeedItem 객체를 담는다.
                    fcArrayList.add(feedCommentItem); // 담은 데이터들을 배열 리스트에 넣고 recyclerview에 보낼 준비를 한다.
                }
                cAdapter.notifyDataSetChanged(); //리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Log.e("FeedCommentActivity", String.valueOf(error.toException())); //에러문 출력
            }
        });

        cAdapter = new FeedCommentAdapter(fcArrayList, this);
        commentList.setAdapter(cAdapter); //리사이클러뷰에 어댑터 연결



        // 현재 사용자의 사진과 닉네임을 가져온다.
        databaseReference = database.getReference("environmentalCampaign").child("UserAccount").child(uid); // DB 테이블 연결
        databaseReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                UserAccount userAccount = snapshot.getValue(UserAccount.class);
                Glide.with(FeedCommentActivity.this).load(userAccount.getProfileImg()).into(comment_profile);
                comment_profile.setBackground(new ShapeDrawable(new OvalShape()));
                comment_profile.setClipToOutline(true);
                userImg = userAccount.getProfileImg();
//                userNickname.setText(userAccount.getNickName());
                userId = userAccount.getNickName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Log.e("FeedCommentActivity", String.valueOf(error.toException())); //에러문 출력
            }
        });

        databaseReference1 = database.getReference("environmentalCampaign");

        comment_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 작성한 내용 String형으로 바꾸기
                commentstr = comment.getText().toString();

                // 현재시간 가져오기
                long now = System.currentTimeMillis();
                Date mDate = new Date(now);
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String getTime = simpleDate.format(mDate);

                FeedCommentItem feedCommentItem = new FeedCommentItem();
                feedCommentItem.setComment(commentstr);
                feedCommentItem.setDate(getTime);
                feedCommentItem.setFeedDate(feedDate);
                feedCommentItem.setPublisher(feedPublisher);
                feedCommentItem.setNickname(userId);
                feedCommentItem.setUserImage(userImg);
                feedCommentItem.setUid(uid);

                databaseReference1.child("FeedComment").child(feedDate).child(getTime).setValue(feedCommentItem);
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
}