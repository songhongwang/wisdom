package com.cbt.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cbt.main.R;
import com.cbt.main.activity.UserActivity;
import com.cbt.main.model.Data;
import com.cbt.main.model.ReplayMyprolemView;
import com.cbt.main.utils.ToastUtils;
import com.cbt.main.utils.net.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import me.nereo.imagechoose.bean.Image;

/**
 * Created by vigorous on 17/12/27.
 */

public class MarketDetailActAdapter extends AppBaseAdapter {
    private CropCircleTransformation mCropCircleTransformation;
    private boolean mHadAdopt = false;
    public MarketDetailActAdapter(Context context, List<ReplayMyprolemView> dataList) {
        super(dataList, context);
        mCropCircleTransformation = new CropCircleTransformation();
    }

    @Override
    public void resetData(List list) {
        super.resetData(list);
        for(int i=0;i<mDataList.size();i++){
            ReplayMyprolemView item = (ReplayMyprolemView) mDataList.get(i);
            if(item.isIsadopt()){
                mHadAdopt = true;
                break;
            }
            mHadAdopt = false;
        }
    }

    @Override
    public View getCView(int position, View view, ViewGroup viewGroup) {
        final ReplayMyprolemView mData = (ReplayMyprolemView)mDataList.get(position);
        View itemView = mInflater.inflate(R.layout.item_market_detail_activity, null, false);
//        ImageView iv = (ImageView) itemView.findViewById(R.id.iv_img);
////
//        String s = (String) mDataList.get(position);
//        if(!s.equals("holder")){
//            Glide.with(mContext).load(s).into(iv);
//        }
        ImageView iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
        if(!TextUtils.isEmpty(mData.getUicon())){
            Picasso.with(itemView.getContext()).load(Constants.getBaseUrl() + mData.getUicon()).placeholder(R.drawable.login_default_icon)
                    .transform(mCropCircleTransformation)
                    .into(iv_img);
        }else{
            iv_img.setImageResource(R.drawable.login_default_icon);
        }
        iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UserActivity.class);
                intent.putExtra("model", mData);
                mContext.startActivity(intent);
            }
        });

        TextView tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        if(TextUtils.isEmpty(mData.getUsername())){
            tv_name.setText(mData.getUsername());
        }else{
            tv_name.setText("匿名");
        }
        TextView tv_type = (TextView) itemView.findViewById(R.id.tv_type);
        if(!TextUtils.isEmpty(mData.getIcon1())){
            tv_type.setText(mData.getIcon1().equals("1") ? "普通农户" : "");
        }
        TextView rp_content = (TextView) itemView.findViewById(R.id.rp_content);
        rp_content.setText(mData.getContent());
        final TextView tv_operate = (TextView) itemView.findViewById(R.id.tv_operate);
        final ImageView iv_operate = (ImageView) itemView.findViewById(R.id.iv_operate);
        tv_operate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.show(mContext, "采纳本条");
                iv_operate.setVisibility(View.VISIBLE);
                tv_operate.setVisibility(View.GONE);
                mData.setIsadopt(true);

                for(int i=0;i<mDataList.size();i++){
                    ReplayMyprolemView item = (ReplayMyprolemView) mDataList.get(i);
                    if(item.getRid().equals(mData.getRid())){
                        item.setIsadopt(true);
                        mHadAdopt = true;
                    }else{
                        item.setIsadopt(false);
                    }
                }

                notifyDataSetChanged();
            }
        });

        if(mHadAdopt){
            if(mData.isIsadopt()){
                iv_operate.setVisibility(View.VISIBLE);
                tv_operate.setVisibility(View.GONE);
            }else{
                iv_operate.setVisibility(View.GONE);
                tv_operate.setVisibility(View.GONE);
            }

        }else{
            if(mData.isIsadopt()){
                iv_operate.setVisibility(View.VISIBLE);
                tv_operate.setVisibility(View.GONE);
            }else{
                iv_operate.setVisibility(View.GONE);
                tv_operate.setVisibility(View.VISIBLE);
            }
        }

        return itemView;
    }
}
