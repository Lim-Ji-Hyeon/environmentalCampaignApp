package com.example.environmentalcampaign.cp_info;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.environmentalcampaign.R;

import java.io.ByteArrayInputStream;

public class FragmentWay extends Fragment {

    TextView tv_wayInfo, tv_rightPhotoInfo, tv_rightPhotoInfo2, tv_wrongPhotoInfo, tv_wrongPhotoInfo2,
            tv_frequency2, tv_period2;
    ImageView iv_rightPhoto, iv_rightPhoto2, iv_wrongPhoto, iv_wrongPhoto2;
    LinearLayout tv_wrongPhoto;

    public FragmentWay() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_way, container, false);
        tv_wayInfo = (TextView)rootView.findViewById(R.id.tv_wayInfo);
        iv_rightPhoto = (ImageView)rootView.findViewById(R.id.iv_rightPhoto);
        iv_rightPhoto2 = (ImageView)rootView.findViewById(R.id.iv_rightPhoto2);
        tv_rightPhotoInfo = (TextView)rootView.findViewById(R.id.tv_rightPhotoInfo);
        tv_rightPhotoInfo2 = (TextView)rootView.findViewById(R.id.tv_rightPhotoInfo2);
        tv_wrongPhoto = (LinearLayout) rootView.findViewById(R.id.tv_wrongPhoto);
        iv_wrongPhoto = (ImageView)rootView.findViewById(R.id.iv_wrongPhoto);
        iv_wrongPhoto2 = (ImageView)rootView.findViewById(R.id.iv_wrongPhoto2);
        tv_wrongPhotoInfo = (TextView)rootView.findViewById(R.id.tv_wrongPhotoInfo);
        tv_wrongPhotoInfo2 = (TextView)rootView.findViewById(R.id.tv_wrongPhotoInfo2);
        tv_frequency2 = (TextView)rootView.findViewById(R.id.tv_frequency2);
        tv_period2 = (TextView)rootView.findViewById(R.id.tv_period2);

        Bundle bundle = getArguments();

        if(bundle != null) {
            String way = bundle.getString("way");
            byte[] arr1 = bundle.getByteArray("rPhoto1");
            byte[] arr2 = bundle.getByteArray("rPhoto2");
            byte[] arr3 = bundle.getByteArray("wPhoto1");
            byte[] arr4 = bundle.getByteArray("wPhoto2");
            byte[] checkByteArray = new byte[]{};
//            Bitmap rPhoto1 = BitmapFactory.decodeByteArray(arr1, 0, arr1.length);
//            Bitmap rPhoto2 = BitmapFactory.decodeByteArray(arr2, 0, arr2.length);
//            Bitmap wPhoto1 = BitmapFactory.decodeByteArray(arr3, 0, arr3.length);
//            Bitmap wPhoto2 = BitmapFactory.decodeByteArray(arr4, 0, arr4.length);
            String rInfo = bundle.getString("rInfo");
            String rInfo2 = bundle.getString("rInfo2");
            String wInfo = bundle.getString("wInfo");
            String wInfo2 = bundle.getString("wInfo2");
//            byte[] checkbyte2 = bundle.getByteArray("checkImage2");
//            Bitmap checkImage2 = BitmapFactory.decodeByteArray(checkbyte2, 0, checkbyte2.length);
            String frequency = bundle.getString("frequency");
            String period = bundle.getString("period");

            if(arr1 != checkByteArray) {
                iv_rightPhoto.setImageDrawable(byteArrayToDrawable(arr1));
                iv_rightPhoto.setVisibility(View.VISIBLE);
            }
            if(arr2 != checkByteArray) {
                iv_rightPhoto2.setImageDrawable(byteArrayToDrawable(arr2));
                iv_rightPhoto2.setVisibility(View.VISIBLE);
            }
            if(arr3 != checkByteArray) {
                iv_wrongPhoto.setImageDrawable(byteArrayToDrawable(arr3));
                iv_wrongPhoto.setVisibility(View.VISIBLE);
            }
            if(arr4 != checkByteArray) {
                iv_wrongPhoto2.setImageDrawable(byteArrayToDrawable(arr4));
                iv_wrongPhoto2.setVisibility(View.VISIBLE);
            }
            if((arr3 != checkByteArray) || (arr4 != checkByteArray)) { tv_wrongPhoto.setVisibility(View.VISIBLE); }

//            Bitmap[] photos = {rPhoto1, rPhoto2, wPhoto1, wPhoto2};
//            ImageView[] imageViews = {iv_rightPhoto, iv_rightPhoto2, iv_wrongPhoto, iv_wrongPhoto2};
            String[] photoInfos = {rInfo, rInfo2, wInfo, wInfo2};
            TextView[] textViews = {tv_rightPhotoInfo, tv_rightPhotoInfo2, tv_wrongPhotoInfo, tv_wrongPhotoInfo2};

//            for(int i = 0; i < photos.length; i++) {
//                if(!(photos[i].sameAs(checkImage2))) {
//                    imageViews[i].setImageBitmap(photos[i]);
//                    imageViews[i].setVisibility(View.VISIBLE);
//                    if(!(photos[2].sameAs(checkImage2)) || !(photos[3].sameAs(checkImage2))) {
//                        tv_wrongPhoto.setVisibility(View.VISIBLE);
//                    }
//                }
//                if(photoInfos[i] != null) {
//                    textViews[i].setText(photoInfos[i]);
//                    textViews[i].setVisibility(View.VISIBLE);
//                }
//            }

            for(int i = 0; i < photoInfos.length; i++) {
                if(photoInfos[i] != null) {
                    textViews[i].setText(photoInfos[i]);
                    textViews[i].setVisibility(View.VISIBLE);
                }
            }

            tv_wayInfo.setText(way);
            tv_frequency2.setText(frequency);
            tv_period2.setText(period);
        }

        return rootView;
    }

    // byte[]를 Drawable로 변환
    public Drawable byteArrayToDrawable(byte[] b) {
        ByteArrayInputStream is = new ByteArrayInputStream(b);
        Drawable drawable = Drawable.createFromStream(is, "drawable");
        return drawable;
    }
}