package com.cbt.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.cbt.main.R;
import com.cbt.main.model.ZuoWuModel;

import java.util.List;

/**
 * Created by vigorous on 18/4/1.
 */

public class PerfactAccountAdapter<T> extends AppBaseAdapter {


    public PerfactAccountAdapter(List<T> dataList, Context context) {
        super(dataList, context);
    }

    public void deleteItem(ZuoWuModel zuoWuModel){
        for(int i = 0;i < mDataList.size(); i ++){
            ZuoWuModel itemModel = (ZuoWuModel) mDataList.get(i);
            if(itemModel.id == zuoWuModel.id){
                mDataList.remove(i);
            }
        }
        notifyDataSetChanged();
    }

    public void addItem(ZuoWuModel zuoWuModel){
        mDataList.add(zuoWuModel);
        notifyDataSetChanged();
    }
    @Override
    public View getCView(int position, View view, ViewGroup viewGroup) {
        View itemView = mInflater.inflate(R.layout.item_perfact_account, null, false);

        final ZuoWuModel zuoWuModel = (ZuoWuModel) mDataList.get(position);


        itemView.findViewById(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(zuoWuModel);
            }
        });
        return itemView;
    }

    public List<ZuoWuModel> getDataList(){
        return mDataList;
    }
}
