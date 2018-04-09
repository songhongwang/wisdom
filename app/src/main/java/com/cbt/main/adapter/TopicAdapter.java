package com.cbt.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbt.main.R;
import com.cbt.main.model.Data;

import java.util.ArrayList;
import java.util.List;


public class TopicAdapter extends RecyclerView.Adapter {
    private final List<Data> mDataList = new ArrayList<>();
    private Context mContext;

    public TopicAdapter(Context context) {
         mContext = context;
    }

    public void set(List<Data> dataList) {
        mDataList.clear();
        if (dataList != null) {
            mDataList.addAll(dataList);
        }
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvTime;

        Data mData;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        }

        void refresh(int pos) {
            mData = mDataList.get(pos);
            tvName.setText(mData.getNickname());
            tvTime.setText(mData.getCreateTime());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(mDataList.size() > 0)
        ((ViewHolder) holder).refresh(position % mDataList.size());
    }

    @Override
    public int getItemCount() {
        return 99999;
    }
}
