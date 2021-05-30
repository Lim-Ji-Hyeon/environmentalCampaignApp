package com.example.environmentalcampaign.cp_info;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

public class ParticipantAdapter extends BaseAdapter {

    Context context = null;
    ArrayList<ParticipantItem> sample = new ArrayList<ParticipantItem>();

    public ParticipantAdapter(ArrayList<ParticipantItem> sample, Context context){
        this.sample = sample;
        this.context = context;
    }

    @Override
    public int getCount() { return sample.size(); }

    @Override
    public Object getItem(int position) { return sample.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        FirebaseDatabase database;
        DatabaseReference databaseReference;

        // "participant_listview" layout을 inflate하여 convertView 참조 획득.
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.participant_listview, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView profile = (ImageView)convertView.findViewById(R.id.iv_profile);
        TextView nickname = (TextView)convertView.findViewById(R.id.tv_nickname);

        ParticipantItem listViewItem = sample.get(position);

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB를 가져오던 중 에러 발생 시
                Log.e("ParticipantAdapter", String.valueOf(error.toException())); //에러문 출력
            }
        });

        return convertView;
    }

//    public void addItem(Drawable profile, String nickname) {
//        ParticipantData item = new ParticipantData();
//
//        item.setProfile(profile);
//        item.setNickname(nickname);
//
//        sample.add(item);
//    }
}
