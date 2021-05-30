package com.example.environmentalcampaign.cp_info;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.home.UserAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReviewAdapter extends BaseAdapter {

    Context context = null;
    ArrayList<ReviewItem> sample = new ArrayList<ReviewItem>();

    public ReviewAdapter(ArrayList<ReviewItem> sample, Context context){
        this.sample = sample;
        this.context = context;
    }

    @Override
    public int getCount() { return sample.size(); }

    @Override
    public Object getItem(int position) {
        return sample.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        FirebaseDatabase database;
        DatabaseReference databaseReference;

        // "review_listview" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.review_listview, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView profile = (ImageView)convertView.findViewById(R.id.iv_profile);
        TextView nickname = (TextView)convertView.findViewById(R.id.tv_nickname);
        TextView date = (TextView)convertView.findViewById(R.id.tv_reviewDate);
        RatingBar reviewRating = (RatingBar)convertView.findViewById(R.id.reviewRatingBar);
        TextView review = (TextView)convertView.findViewById(R.id.tv_review);

        ReviewItem listViewItem = sample.get(position);

        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
        databaseReference = database.getReference("environmentalCampaign").child("UserAccount").child(listViewItem.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                UserAccount userAccount = snapshot.getValue(UserAccount.class);
                // 아이템 내 각 위젯에 데이터 반영
                Glide.with(context).load(userAccount.getProfileImg()).into(profile);
                profile.setBackground(new ShapeDrawable(new OvalShape()));
                profile.setClipToOutline(true);
                nickname.setText(userAccount.getNickName());
                int rDatetime = listViewItem.getDatetime();
                date.setText(ryear(rDatetime) + "." + rmonth(rDatetime) + "." + rday(rDatetime));
                reviewRating.setRating((float)listViewItem.getRating());
                review.setText(listViewItem.getContent());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Log.e("ReviewAdapter", String.valueOf(error.toException())); //에러문 출력
            }
        });

//        // Data set(listViewItemList)에서 position에 위치한 데이터 참조 획득
//        reviewData listViewItem = sample.get(position);
//
//        // 아이템 내 각 위젯에 데이터 반영
//        profile.setImageDrawable(listViewItem.getProfile());
//        nickname.setText(listViewItem.getNickname());
//        int rdate = listViewItem.getDate();
//        date.setText(ryear(rdate) + "." + rmonth(rdate) + "." + rday(rdate));
//        reviewRating.setRating((float)listViewItem.getRatingbar());
//        review.setText(listViewItem.getReview());

        return convertView;
    }

//    public void addItem(Drawable profile, String nickname, int date, double ratingbar, String review) {
//        reviewData item = new reviewData();
//
//        item.setProfile(profile);
//        item.setNickname(nickname);
//        item.setDate(date);
//        item.setRatingbar(ratingbar);
//        item.setReview(review);
//
//        sample.add(item);
//    }

    // YYYYmmDDHHMMSSsss 변환
    public int ryear(int rdate) { return Integer.parseInt(String.valueOf(rdate).substring(0,4)); }
    public int rmonth(int rdate) { return Integer.parseInt(String.valueOf(rdate).substring(4,6)); }
    public int rday(int rdate) { return Integer.parseInt(String.valueOf(rdate).substring(6,8)); }

}
