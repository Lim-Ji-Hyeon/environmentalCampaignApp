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
    LinearLayout lo_wrongPhoto;

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
        lo_wrongPhoto = (LinearLayout) rootView.findViewById(R.id.lo_wrongPhoto);
        iv_wrongPhoto = (ImageView)rootView.findViewById(R.id.iv_wrongPhoto);
        iv_wrongPhoto2 = (ImageView)rootView.findViewById(R.id.iv_wrongPhoto2);
        tv_wrongPhotoInfo = (TextView)rootView.findViewById(R.id.tv_wrongPhotoInfo);
        tv_wrongPhotoInfo2 = (TextView)rootView.findViewById(R.id.tv_wrongPhotoInfo2);
        tv_frequency2 = (TextView)rootView.findViewById(R.id.tv_frequency2);
        tv_period2 = (TextView)rootView.findViewById(R.id.tv_period2);

        Bundle bundle = getArguments();

        if(bundle != null) {
            String way = bundle.getString("way");
            String rPhoto1 = bundle.getString("rPhoto1");
            String rPhoto2 = bundle.getString("rPhoto2");
            String wPhoto1 = bundle.getString("wPhoto1");
            String wPhoto2 = bundle.getString("wPhoto2");
//            byte[] arr1 = bundle.getByteArray("rPhoto1");
//            byte[] arr2 = bundle.getByteArray("rPhoto2");
//            byte[] arr3 = bundle.getByteArray("wPhoto1");
//            byte[] arr4 = bundle.getByteArray("wPhoto2");
//            byte[] checkByteArray = new byte[]{};
            String rInfo = bundle.getString("rInfo");
            String rInfo2 = bundle.getString("rInfo2");
            String wInfo = bundle.getString("wInfo");
            String wInfo2 = bundle.getString("wInfo2");
            String frequency = bundle.getString("frequency");
            String period = bundle.getString("period");

            if(rPhoto1 != "") {
                iv_rightPhoto.setImageDrawable(byteArrayToDrawable(binaryStringToByteArray(rPhoto1)));
                iv_rightPhoto.setVisibility(View.VISIBLE);
            }
            if(rPhoto2 != "") {
                iv_rightPhoto2.setImageDrawable(byteArrayToDrawable(binaryStringToByteArray(rPhoto2)));
                iv_rightPhoto2.setVisibility(View.VISIBLE);
            }
            if(wPhoto1 != "") {
                iv_wrongPhoto.setImageDrawable(byteArrayToDrawable(binaryStringToByteArray(wPhoto1)));
                iv_wrongPhoto.setVisibility(View.VISIBLE);
            }
            if(wPhoto2 != "") {
                iv_wrongPhoto2.setImageDrawable(byteArrayToDrawable(binaryStringToByteArray(wPhoto2)));
                iv_wrongPhoto2.setVisibility(View.VISIBLE);
            }
            if((wPhoto1 != "") || (wPhoto2 != "")) { lo_wrongPhoto.setVisibility(View.VISIBLE); }
            else { lo_wrongPhoto.setVisibility(View.GONE); }

//            if(arr1 != checkByteArray) {
//                iv_rightPhoto.setImageDrawable(byteArrayToDrawable(arr1));
//                iv_rightPhoto.setVisibility(View.VISIBLE);
//            }
//            if(arr2 != checkByteArray) {
//                iv_rightPhoto2.setImageDrawable(byteArrayToDrawable(arr2));
//                iv_rightPhoto2.setVisibility(View.VISIBLE);
//            }
//            if(arr3 != checkByteArray) {
//                iv_wrongPhoto.setImageDrawable(byteArrayToDrawable(arr3));
//                iv_wrongPhoto.setVisibility(View.VISIBLE);
//            }
//            if(arr4 != checkByteArray) {
//                iv_wrongPhoto2.setImageDrawable(byteArrayToDrawable(arr4));
//                iv_wrongPhoto2.setVisibility(View.VISIBLE);
//            }
//            if((arr3 != checkByteArray) || (arr4 != checkByteArray)) { tv_wrongPhoto.setVisibility(View.VISIBLE); }

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

    // String을 byte[]로 변환
    public static byte[] binaryStringToByteArray(String s) {
        int count = s.length() / 8;
        byte[] b = new byte[count];
        for(int i = 1; i < count; ++i) {
            String t = s.substring((i-1)*8, i*8);
            b[i-1] = binaryStringToByte(t);
        }
        return b;
    }

    // String을 byte로 변환
    public static byte binaryStringToByte(String s) {
        byte ret = 0, total = 0;
        for(int i = 0; i < 8; ++i) {
            ret = (s.charAt(7 - i) == '1') ? (byte)(1 << i) : 0;
            total = (byte) (ret | total);
        }
        return total;
    }

    // byte[]를 Drawable로 변환
    public Drawable byteArrayToDrawable(byte[] b) {
        ByteArrayInputStream is = new ByteArrayInputStream(b);
        Drawable drawable = Drawable.createFromStream(is, "drawable");
        return drawable;
    }
}