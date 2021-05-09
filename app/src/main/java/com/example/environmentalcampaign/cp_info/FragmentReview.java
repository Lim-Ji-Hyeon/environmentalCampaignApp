package com.example.environmentalcampaign.cp_info;

import android.content.Context;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.environmentalcampaign.R;

public class FragmentReview extends Fragment {

    public FragmentReview() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_review, container, false);

        ListView rListView;
        reviewAdapter rAdpter;

        rAdpter = new reviewAdapter();

        rListView = (ListView)rootView.findViewById(R.id.lv_review);
//        scrollView = (ScrollView)rootView.findViewById(R.id.sv);
//        rListView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                // 리스트뷰에서 터치가되면 스크롤뷰만 움직이게
//                scrollView.requestDisallowInterceptTouchEvent(true);
//                return false;
//            }
//        });
        rListView.setAdapter(rAdpter);

        Context context = getActivity().getApplicationContext();
//        rAdpter.addItem(ContextCompat.getDrawable(context, R.drawable.profile), "녹색아줌마", 20210404, 5,
//                "나름 분리수거를 잘 하고 있다고 생각했는데 아직 많이 부족하다는 걸 느꼈어요ㅠㅠ 이번 기회를 통해 올바르게 분리배출하는 방법을 알게 되어 좋았습니다!! 많은 사람들이 캠페인에 참여했으면 좋겠네요~~");
//        rAdpter.addItem(ContextCompat.getDrawable(context, R.drawable.profile), "지구지킴이", 20210329, 4.5,
//                "평소 분리수거만 잘하면 되겠거니 하고 살아왔는데 잘못된 분리수거 중이었다니 충격이네요.. 앞으로 더욱 신경써서 분리배출하겠습니다!!!!");
//        rAdpter.addItem(ContextCompat.getDrawable(context, R.drawable.profile), "으쌰으쌰", 20210328, 5,
//                "느끼는 것이 많은 캠페인입니다. 계속 re캠페인 중입니다.");

        if(rAdpter.isEmpty()) {
            TextView tv_noReview = (TextView)rootView.findViewById(R.id.tv_noReview);
            LinearLayout lo_review = (LinearLayout)rootView.findViewById(R.id.lo_review);
            tv_noReview.setVisibility(View.VISIBLE);
            lo_review.setVisibility(View.GONE);
        } else {
            TextView tv_reviewN = (TextView)rootView.findViewById(R.id.tv_reviewN);
            int n = rAdpter.getCount();
            if(n > 100) {
                tv_reviewN.setText("(100+)");
            } else {
                tv_reviewN.setText("(" + n + ")");
            }

            RatingBar ratingBar = (RatingBar)rootView.findViewById(R.id.ratingBar);
            ratingBar.setRating(reviewAverage(rListView));

            TextView tv_reviewAvr = (TextView)rootView.findViewById(R.id.tv_reviewAvr);
            tv_reviewAvr.setText(Float.toString(reviewAverage(rListView)));

            setListViewHeightBasedOnChildren(rListView);
        }

        return rootView;
    }

    // 리스트뷰 높이 조정
    public static boolean setListViewHeightBasedOnChildren(ListView listview) {

        ListAdapter listAdapter = listview.getAdapter();
        if(listAdapter != null) {
            int numberOfItems = listAdapter.getCount();

            int totalItemsHeight = 0;
            for(int i=0; i<numberOfItems; i++) {
                View listItem = listAdapter.getView(i, null, listview);
                float px = 500 * (listview.getResources().getDisplayMetrics().density);
                listItem.measure(View.MeasureSpec.makeMeasureSpec((int)px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalItemsHeight += listItem.getMeasuredHeight();
            }

            int totalDividersHeight = listview.getDividerHeight() * (numberOfItems - 1);
            int totalPadding = listview.getPaddingTop() + listview.getPaddingBottom();

            ViewGroup.LayoutParams params = listview.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + totalPadding + 160;
            listview.setLayoutParams(params);
            listview.requestLayout();

            return true;

        } else {
            return false;
        }
    }

    // 리뷰 평균 구하기
    public float reviewAverage(ListView listview) {
        ListAdapter listAdapter = listview.getAdapter();
        double sum = 0;
        for(int i=0; i < listAdapter.getCount(); i++) {
            reviewData r = (reviewData)listAdapter.getItem(i);
            sum += r.getRatingbar();
        }
        return Float.parseFloat(String.format("%.2f", sum / listAdapter.getCount()));
    }

}