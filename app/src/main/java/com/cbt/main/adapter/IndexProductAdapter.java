package com.cbt.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cbt.main.R;
import com.cbt.main.model.IndexProductModel;

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
    public View getCView(int position, View view, ViewGroup viewGroup) {
        View itemView = mInflater.inflate(R.layout.item_index_product, null, false);

        IndexProductModel model = (IndexProductModel) mDataList.get(position);


        TextView tvProduct = (TextView) itemView.findViewById(R.id.tv_product);
        tvProduct.setText(model.getProductname());

        return itemView;
    }
}
