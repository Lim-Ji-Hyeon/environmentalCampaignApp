package com.example.environmentalcampaign.cp_info;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.environmentalcampaign.R;

import java.util.ArrayList;

public class ParticipantAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<ParticipantData> sample = new ArrayList<ParticipantData>();

    public ParticipantAdapter(){}

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

        // "participant_listview" layout을 inflate하여 convertView 참조 획득.
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.participant_listview, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView profile = (ImageView)convertView.findViewById(R.id.iv_profile);
        TextView nickname = (TextView)convertView.findViewById(R.id.tv_nickname);

        ParticipantData listViewItem = sample.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        profile.setImageDrawable(listViewItem.getProfile());
        nickname.setText(listViewItem.getNickname());

        return convertView;
    }

    public void addItem(Drawable profile, String nickname) {
        ParticipantData item = new ParticipantData();

        item.setProfile(profile);
        item.setNickname(nickname);

        sample.add(item);
    }
}
