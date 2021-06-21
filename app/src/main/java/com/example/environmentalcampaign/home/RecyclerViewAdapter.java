package com.example.environmentalcampaign.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.environmentalcampaign.R;
import com.example.environmentalcampaign.cp_info.CampaignInformation;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>{

    private ArrayList<RecyclerViewItem> recyclerViewItems;
    private Context context;

    public RecyclerViewAdapter(ArrayList<RecyclerViewItem> recyclerViewItems, Context context) {
        this.recyclerViewItems = recyclerViewItems;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_grid_layout, parent, false);
        RecyclerViewHolder holder = new RecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // 각 아이템을 연결해준다.
        Glide.with(holder.itemView)
                .load(recyclerViewItems.get(position).getImage())
                .into(holder.image);
        holder.campaignCode.setText(recyclerViewItems.get(position).getCampaignCode());
        holder.title.setText(recyclerViewItems.get(position).getTitle());
    }

    @Override
    public int getItemCount() { return (recyclerViewItems != null ? recyclerViewItems.size() : 0); }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView campaignCode;
        ImageView image;
        TextView title;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            campaignCode = itemView.findViewById(R.id.tv_campaignCode);
            image = itemView.findViewById(R.id.card_image);
            title = itemView.findViewById(R.id.card_text);
            image.setClipToOutline(true);

            // recyclerView에 있는 image를 클릭했을 때 CampaignInformation Activity로 넘어간다.
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), CampaignInformation.class);
                    intent.putExtra("signal", "recyclerView");
                    intent.putExtra("campaignCode", campaignCode.getText().toString());
                    view.getContext().startActivity(intent);
                }
            });
        }
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
