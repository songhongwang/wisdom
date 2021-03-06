package com.cbt.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cbt.main.R;
import com.cbt.main.activity.ExpertConsultActivity;
import com.cbt.main.activity.MarketDetailActivity;
import com.cbt.main.activity.WebActivity;
import com.cbt.main.model.IndexProductModel;
import com.cbt.main.utils.ToastUtils;

import java.util.List;

/**
 * Created by vigorous on 18/3/30.
 * 首页农气产品
 */

public class IndexProductAdapter extends AppBaseAdapter {
    public IndexProductAdapter(List<IndexProductModel> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    public View getCView(final int position, View view, ViewGroup viewGroup) {
        View itemView = mInflater.inflate(R.layout.item_index_product, null, false);

        IndexProductModel model = (IndexProductModel) mDataList.get(position);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.show(mContext, "产品详情");
                if(position == 0){
                    Intent intent = new Intent(mContext, WebActivity.class);
                    mContext.startActivity(intent);
                }else if(position == 1) {
                    Intent intent = new Intent(mContext, ExpertConsultActivity.class);
                    mContext.startActivity(intent);
                }else{
                    Intent intent = new Intent(mContext, MarketDetailActivity.class);
                    mContext.startActivity(intent);
                }
            }
        });

        TextView tvProduct = (TextView) itemView.findViewById(R.id.tv_product);
        tvProduct.setText(model.getProductname());

        return itemView;
    }
}
