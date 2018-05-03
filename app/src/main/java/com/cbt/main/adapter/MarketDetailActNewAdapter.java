package com.cbt.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cbt.main.R;
import com.cbt.main.activity.UserActivity;
import com.cbt.main.model.Data;
import com.cbt.main.model.ReplayMarketView;
import com.cbt.main.model.ReplayMyprolemView;
import com.cbt.main.utils.net.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by vigorous on 17/12/27.
 */

public class MarketDetailActNewAdapter extends AppBaseAdapter {
    private CropCircleTransformation mCropCircleTransformation;
    public MarketDetailActNewAdapter(Context context, List<ReplayMyprolemView> dataList) {

        super(dataList, context);
    }

    @Override
    public View getCView(int position, View view, ViewGroup viewGroup) {
        final ReplayMarketView mData = (ReplayMarketView)mDataList.get(position);
        View itemView = mInflater.inflate(R.layout.item_market_detail_activity, null, false);
//        ImageView iv = (ImageView) itemView.findViewById(R.id.iv_img);
//
//        String s = (String) mDataList.get(position);
//        if(!s.equals("holder")){
//            Glide.with(mContext).load(s).into(iv);
//        }
        ImageView iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
        if(!TextUtils.isEmpty(mData.getReplyicon())){
            Picasso.with(itemView.getContext()).load(Constants.getBaseUrl() + mData.getReplyicon()).placeholder(R.drawable.login_default_icon)
                    .transform(mCropCircleTransformation)
                    .into(iv_img);
        }else{
            iv_img.setImageResource(R.drawable.login_default_icon);
        }
        iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UserActivity.class);
                Data data = new Data();
                data.setUid(mData.getReplayuserid());
                data.setIid(mData.getReplayuserid());
                data.setAvatar(mData.getReplyicon());
                data.setNickname(mData.getReplayusername());

                intent.putExtra("model", data);
                mContext.startActivity(intent);
            }
        });

        TextView tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        tv_name.setText(mData.getReplayusername());
        TextView tv_type = (TextView) itemView.findViewById(R.id.tv_type);
        tv_type.setText(mData.getIcon1().equals("1") ? "普通农户" : "");
        TextView rp_content = (TextView) itemView.findViewById(R.id.rp_content);
        rp_content.setText(mData.getContent());
        return itemView;
    }
}
