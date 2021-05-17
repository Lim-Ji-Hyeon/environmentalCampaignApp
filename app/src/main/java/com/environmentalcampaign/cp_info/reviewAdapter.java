package com.environmentalcampaign.cp_info;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.environmentalcampaign.R;

import java.util.ArrayList;

public class reviewAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<reviewData> sample = new ArrayList<reviewData>();

    public reviewAdapter(){}

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

        // Data set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        reviewData listViewItem = sample.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        profile.setImageDrawable(listViewItem.getProfile());
        nickname.setText(listViewItem.getNickname());
        int rdate = listViewItem.getDate();
        date.setText(ryear(rdate) + "." + rmonth(rdate) + "." + rday(rdate));
        reviewRating.setRating((float)listViewItem.getRatingbar());
        review.setText(listViewItem.getReview());

        return convertView;
    }

    public void addItem(Drawable profile, String nickname, int date, double ratingbar, String review) {
        reviewData item = new reviewData();

        item.setProfile(profile);
        item.setNickname(nickname);
        item.setDate(date);
        item.setRatingbar(ratingbar);
        item.setReview(review);

        sample.add(item);
    }

    public int ryear(int rdate) { return Integer.parseInt(String.valueOf(rdate).substring(0,4)); }
    public int rmonth(int rdate) { return Integer.parseInt(String.valueOf(rdate).substring(4,6)); }
    public int rday(int rdate) { return Integer.parseInt(String.valueOf(rdate).substring(6,8)); }

}
