package com.example.environmentalcampaign.search_page;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
import com.example.environmentalcampaign.home.RecyclerViewAdapter;


import java.util.ArrayList;
import java.util.Locale;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> implements Filterable {

    private ArrayList<SearchViewItem> searchViewItems, filteredItemList;
    private Context context;

    public SearchAdapter(ArrayList<SearchViewItem> searchViewItems, Context context) {
        this.searchViewItems = searchViewItems;
        this.context = context;
        this.filteredItemList = searchViewItems;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_recyclerview_item, parent, false);
        SearchViewHolder holder = new SearchViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        // 각 아이템을 연결해준다.
        Glide.with(holder.itemView)
                .load(filteredItemList.get(position).getCampaignLogo())
                .into(holder.iv_camapign);
        holder.campaignCode.setText(filteredItemList.get(position).getCampaignCode());
        holder.tv_frequency.setText(filteredItemList.get(position).getFrequency());
        holder.tv_participantsN.setText(filteredItemList.get(position).getParticipantsN()+"명 참여중");
        holder.tv_cp_name.setText(filteredItemList.get(position).getCampaignName());
    }

    @Override
    public int getItemCount() { return (filteredItemList != null ? filteredItemList.size() : 0); }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                    filteredItemList = searchViewItems;
                } else {
                    ArrayList<SearchViewItem> filteringList = new ArrayList<>();
                    for(SearchViewItem items : searchViewItems) {
                        if(items.getCampaignName().toLowerCase().contains(charString.toLowerCase())) {
                            filteringList.add(items);
                        }
                    }
                    filteredItemList = filteringList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredItemList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredItemList = (ArrayList<SearchViewItem>)results.values;
                notifyDataSetChanged();
            }
        };
    }


    public class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView tv_cp_name, tv_frequency,campaignCode, tv_participantsN;
        ImageView iv_camapign;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            campaignCode = itemView.findViewById(R.id.campaignCode);
            tv_frequency = itemView.findViewById(R.id.tv_frequency);
            tv_participantsN = itemView.findViewById(R.id.tv_participantsN);
            tv_cp_name = itemView.findViewById(R.id.tv_cp_name);
            iv_camapign = itemView.findViewById(R.id.iv_camapign);


            // recyclerView에 있는 image를 클릭했을 때 CampaignInformation Activity로 넘어간다.
            iv_camapign.setOnClickListener(new View.OnClickListener() {
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




}
