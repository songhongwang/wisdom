package com.cbt.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cbt.main.R;
import com.cbt.main.model.ZuoWuModel;

import java.util.List;

/**
 * Created by vigorous on 18/4/1.
 */

public class PerfactAccountAdapter<T> extends AppBaseAdapter {
    private OperateListener mOperateListener;

    public OperateListener getOperateListener() {
        return mOperateListener;
    }

    public void setOperateListener(OperateListener operateListener) {
        mOperateListener = operateListener;
    }

    public PerfactAccountAdapter(List<T> dataList, Context context) {
        super(dataList, context);
    }

    public void deleteItem(ZuoWuModel zuoWuModel){
        for(int i = 0;i < mDataList.size(); i ++){
            ZuoWuModel itemModel = (ZuoWuModel) mDataList.get(i);
            if(itemModel.id == zuoWuModel.id){
                mOperateListener.onDel(itemModel);
                mDataList.remove(i);
            }
        }
        notifyDataSetChanged();
    }

    public void addItem(ZuoWuModel zuoWuModel){
        mDataList.add(zuoWuModel);
        notifyDataSetChanged();
        mOperateListener.onAdd(zuoWuModel);
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
        ((TextView)itemView.findViewById(R.id.tv_txt_leixing)).setText(zuoWuModel.strLeixin);
        ((TextView)itemView.findViewById(R.id.tv_txt_zhognlei)).setText(zuoWuModel.strPinzhong);

        itemView.findViewById(R.id.rl_leixing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOperateListener.onLeixin(zuoWuModel);
            }
        });

        itemView.findViewById(R.id.rl_pinzhong).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOperateListener.onPinzhong(zuoWuModel);
            }
        });

        return itemView;
    }

    public List<ZuoWuModel> getDataList(){
        return mDataList;
    }

    public void notifyItem(ZuoWuModel currentZuowu) {
        for(int i = 0;i < mDataList.size(); i ++){
            ZuoWuModel itemModel = (ZuoWuModel) mDataList.get(i);
            if(itemModel.id == currentZuowu.id){
                itemModel.pinzhong = currentZuowu.pinzhong;
                itemModel.leixin = currentZuowu.leixin;
            }
        }
        notifyDataSetChanged();
    }

    public interface OperateListener{
        void onAdd(ZuoWuModel zuoWuModel);
        void onDel(ZuoWuModel zuoWuModel);

        void onLeixin(ZuoWuModel zuoWuModel);
        void onPinzhong(ZuoWuModel zuoWuModel);
    }
}
