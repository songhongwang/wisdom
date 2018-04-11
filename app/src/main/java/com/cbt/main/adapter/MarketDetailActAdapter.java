package com.cbt.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cbt.main.R;

import java.util.List;

/**
 * Created by vigorous on 17/12/27.
 */

public class MarketDetailActAdapter extends AppBaseAdapter {
    public MarketDetailActAdapter(Context context, List<String> dataList) {
        super(dataList, context);
    }

    @Override
    public View getCView(int position, View view, ViewGroup viewGroup) {
        View itemView = mInflater.inflate(R.layout.item_market_detail_activity, null, false);
        ImageView iv = (ImageView) itemView.findViewById(R.id.iv_img);

        String s = (String) mDataList.get(position);
        if(!s.equals("holder")){
            Glide.with(mContext).load(s).into(iv);
        }

        return itemView;
    }
}
