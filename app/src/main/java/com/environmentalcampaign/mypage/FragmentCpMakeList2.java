package com.environmentalcampaign.mypage;

import android.content.Context;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.environmentalcampaign.MyCompleteAdapter;
import com.environmentalcampaign.R;

public class FragmentCpMakeList2 extends Fragment {

    public FragmentCpMakeList2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_cp_make_list2, container, false);

        ListView list_make_complete_cp;
        MyCompleteAdapter adapter2;

        adapter2 = new MyCompleteAdapter();

        list_make_complete_cp = (ListView)rootView.findViewById(R.id.lv_cp_situation);
        list_make_complete_cp.setAdapter(adapter2);

        Context context = getActivity().getApplicationContext();
        adapter2.addItem("버리스타", 100, "주 2일", 2, ContextCompat.getDrawable(context, R.drawable.burista));
        return rootView;
    }
}