package com.example.environmentalcampaign.mypage;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.environmentalcampaign.MyAdapter;
import com.example.environmentalcampaign.R;

public class FragmentCpMakeList extends Fragment {

    public FragmentCpMakeList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_cp_make_list, container, false);

        ListView list_make_ing_cp;
        MyAdapter adapter1;

        // Adapter 생성
        adapter1 = new MyAdapter();

        // 리스트뷰 참조 및 Adapter 달기
        list_make_ing_cp = (ListView)rootView.findViewById(R.id.lv_cp_situation);
        list_make_ing_cp.setAdapter(adapter1);

        Context context = getActivity().getApplicationContext();
        adapter1.addItem(100, 2, "버리스타", "주 2일", "00:00:00", "24:00:00", 20210501, ContextCompat.getDrawable(context, R.drawable.burista));
        return rootView;
    }
}