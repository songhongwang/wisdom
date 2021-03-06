package com.cbt.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by vigorous on 17/12/27.
 */

public abstract class AppBaseAdapter<T> extends android.widget.BaseAdapter {
    public List<T> mDataList = new ArrayList<>();
    public Context mContext;
    public LayoutInflater mInflater;

    public AppBaseAdapter(List<T> dataList, Context context) {
        mDataList = dataList;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public void resetData(List<T> list){
        mDataList = list;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public T getItem(int i) {
        return mDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return getCView(i, view, viewGroup);
    }

    public abstract View getCView(int position, View view, ViewGroup viewGroup);


}
