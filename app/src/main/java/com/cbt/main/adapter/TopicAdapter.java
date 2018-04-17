package com.cbt.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbt.main.R;
import com.cbt.main.activity.MarketDetailActivity;
import com.cbt.main.model.Data;
import com.cbt.main.view.piaoquan.MessagePicturesLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;


public class TopicAdapter extends RecyclerView.Adapter {
    private final List<Data> mDataList = new ArrayList<>();
    private CropCircleTransformation mCropCircleTransformation = null;
    private MessagePicturesLayout.Callback mCallback;
    private Context mContext;

    public TopicAdapter(Context context) {
         mContext = context;
    }

    public TopicAdapter setPictureClickCallback(MessagePicturesLayout.Callback callback) {
        mCropCircleTransformation = new CropCircleTransformation();
        mCallback = callback;
        return this;
    }

    public void set(List<Data> dataList) {
        mDataList.clear();
        if (dataList != null) {
            mDataList.addAll(dataList);
        }
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvTime,tv_des,tv_count;

        Data mData;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tv_des = (TextView) itemView.findViewById(R.id.tv_des);
            tv_count = (TextView) itemView.findViewById(R.id.tv_count);

        }

        void refresh(int pos) {
            mData = mDataList.get(pos);
            tvName.setText(mData.getNickname());
            tvTime.setText(mData.getCreateTime());
            tv_des.setText(mData.getShenfen());
            tv_count.setText(mData.getRplaycount());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(mDataList.size() > 0) {
            final Data mdatatemp = mDataList.get(position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MarketDetailActivity.class);
                    intent.putExtra("iid", mdatatemp.getIid());
                    mContext.startActivity(intent);
                }
            });

            ((ViewHolder) holder).refresh(position % mDataList.size());
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
