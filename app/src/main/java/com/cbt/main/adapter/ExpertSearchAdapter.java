package com.cbt.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.cbt.main.R;

import java.util.List;

/**
 * Created by vigorous on 18/3/30.
 * 首页农气产品
 */

public class ExpertSearchAdapter extends AppBaseAdapter {
    public ExpertSearchAdapter(List<String> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    public View getCView(int position, View view, ViewGroup viewGroup) {
        View itemView = mInflater.inflate(R.layout.item_expert_search, null, false);
        return itemView;
    }
}
