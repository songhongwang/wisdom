package com.cbt.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cbt.main.R;
import com.cbt.main.engin.SceneSurfaceView;

/**
 * Created by vigorous on 16/1/4.
 * 首页
 */
public class IndexFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1, container, false);
        mRootView = view;
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void initUI() {
        mTvTitle.setText("北京市海淀区");
    }

    @Override
    protected void lazyLoad() {

    }


}
