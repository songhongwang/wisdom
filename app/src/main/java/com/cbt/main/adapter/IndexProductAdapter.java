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

        final IndexProductModel model = (IndexProductModel) mDataList.get(position);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.show(mContext, "产品详情");
                if(model.getIsreadall().equals("0")){
                    Intent intent = new Intent(mContext, WebActivity.class);
                    intent.putExtra("model", model);
                    mContext.startActivity(intent);
                }else if(model.getIsreadall().equals("2")) {
                    Intent intent = new Intent(mContext, ExpertConsultActivity.class);
                    intent.putExtra("iid", model.getPid());
                    intent.putExtra("model", model);
                    mContext.startActivity(intent);
                }else{
                    Intent intent = new Intent(mContext, MarketDetailActivity.class);
                    intent.putExtra("iid", model.getPid());
                    intent.putExtra("model", model);
                    mContext.startActivity(intent);
                }
            }
        });

        TextView tvProduct = (TextView) itemView.findViewById(R.id.tv_product);
        tvProduct.setText(model.getProductname());

        return itemView;
    }
}
