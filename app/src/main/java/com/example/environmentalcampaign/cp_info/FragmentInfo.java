package com.example.environmentalcampaign.cp_info;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.environmentalcampaign.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class FragmentInfo extends Fragment {

    TextView cpInfo;
    ImageView iv_info1, iv_info2, iv_info3, iv_info4, iv_info5;

    public FragmentInfo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_info, container, false);
        cpInfo = (TextView)rootView.findViewById(R.id.tv_cpInfo);
        iv_info1 = (ImageView)rootView.findViewById(R.id.iv_info1);
        iv_info2 = (ImageView)rootView.findViewById(R.id.iv_info2);
        iv_info3 = (ImageView)rootView.findViewById(R.id.iv_info3);
        iv_info4 = (ImageView)rootView.findViewById(R.id.iv_info4);
        iv_info5 = (ImageView)rootView.findViewById(R.id.iv_info5);

        Bundle bundle = getArguments();

        if(bundle != null) {
            String info = bundle.getString("info");
            String infoImage1 = bundle.getString("infoImage1");
            String infoImage2 = bundle.getString("infoImage2");
            String infoImage3 = bundle.getString("infoImage3");
            String infoImage4 = bundle.getString("infoImage4");
            String infoImage5 = bundle.getString("infoImage5");

            String[] infoImages = {infoImage1, infoImage2, infoImage3, infoImage4, infoImage5};
            ImageView[] imageViews = {iv_info1, iv_info2, iv_info3, iv_info4, iv_info5};
            for(int i = 0; i < infoImages.length; i++) {
                if(!infoImages[i].equals("")) {
                    Glide.with(FragmentInfo.this).load(infoImages[i]).into(imageViews[i]);
//                    imageViews[i].setImageDrawable(byteArrayToDrawable(binaryStringToByteArray(infoImages[i])));
                    imageViews[i].setVisibility(View.VISIBLE);
                } else { imageViews[i].setVisibility(View.GONE); }
            }

            cpInfo.setText(info);
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