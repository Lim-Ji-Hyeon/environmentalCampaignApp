package com.example.environmentalcampaign.certification_page;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.cp_info.ReviewItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReviewPage extends AppCompatActivity {
    ImageButton bt_back;
    RatingBar review_ratingbar;
    TextView review_content;
    Button review_button;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    String campaignCode, uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_page);

        bt_back = (ImageButton)findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        review_ratingbar = (RatingBar)findViewById(R.id.review_ratingbar);
        review_content = (TextView)findViewById(R.id.review_content);
        review_button = (Button)findViewById(R.id.review_button);

        Intent intent = getIntent();
        campaignCode = intent.getStringExtra("campaignCode");

        // uid 가져오기
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();

        review_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 현재시간 가져오기
                long now = System.currentTimeMillis();
                Date mDate = new Date(now);
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyMMddhhmmss");
                String getTime = simpleDate.format(mDate);

                ReviewItem reviewItem = new ReviewItem();
                reviewItem.setUid(uid);
                reviewItem.setDatetime(getTime);
                reviewItem.setRating(review_ratingbar.getRating());
                reviewItem.setContent(review_content.getText().toString());

                database = FirebaseDatabase.getInstance();
                databaseReference = database.getReference("environmentalCampaign").child("Campaign").child(campaignCode).child("reviews");
                databaseReference.child(getTime).setValue(reviewItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        database.getReference("environmentalCampaign").child("MyCampaign").child(uid).child(campaignCode).child("reviewComplete").setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                finish();
                            }
                        });
                    }
                });
            }
        });
    }
}