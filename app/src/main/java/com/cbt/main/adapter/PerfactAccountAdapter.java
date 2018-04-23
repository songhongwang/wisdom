package com.cbt.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cbt.main.R;
import com.cbt.main.model.Usercrop;
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

    public void deleteItem(Usercrop zuoWuModel){
        for(int i = 0;i < mDataList.size(); i ++){
            Usercrop itemModel = (Usercrop) mDataList.get(i);
            if(itemModel.getP1().equals(zuoWuModel.getP1())){
                mOperateListener.onDel(itemModel);
                mDataList.remove(i);
            }
        }
        notifyDataSetChanged();
    }

    public void addItem(Usercrop zuoWuModel){
        mDataList.add(zuoWuModel);
        notifyDataSetChanged();
        mOperateListener.onAdd(zuoWuModel);
    }
    @Override
    public View getCView(int position, View view, ViewGroup viewGroup) {
        View itemView = mInflater.inflate(R.layout.item_perfact_account, null, false);

        final Usercrop zuoWuModel = (Usercrop) mDataList.get(position);


        itemView.findViewById(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(zuoWuModel);
            }
        });
        ((TextView)itemView.findViewById(R.id.tv_txt_leixing)).setText(zuoWuModel.getCtname());
        ((TextView)itemView.findViewById(R.id.tv_txt_zhognlei)).setText(zuoWuModel.getCname());

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

    public List<Usercrop> getDataList(){
        return mDataList;
    }

    public void notifyItem(Usercrop currentZuowu) {
        for(int i = 0;i < mDataList.size(); i ++){
            Usercrop itemModel = (Usercrop) mDataList.get(i);
            if(itemModel.getP1().equals(currentZuowu.getP1())){
                itemModel.setCid(currentZuowu.getCid());
                itemModel.setCname(currentZuowu.getCname());
                itemModel.setCtid(currentZuowu.getCtid());
                itemModel.setCtname(currentZuowu.getCtname());
                itemModel.setP1(itemModel.getUid()+"-"+itemModel.getCtid()+"-"+itemModel.getCid());
            }
        }
        notifyDataSetChanged();
    }

    public interface OperateListener{
        void onAdd(Usercrop zuoWuModel);
        void onDel(Usercrop zuoWuModel);

        void onLeixin(Usercrop zuoWuModel);
        void onPinzhong(Usercrop zuoWuModel);
    }
}
