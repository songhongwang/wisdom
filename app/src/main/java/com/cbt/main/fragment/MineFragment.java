package com.cbt.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cbt.main.R;

/**
 * Created by caobotao on 16/1/4.
 */
public class MineFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2, container, false);
        mRootView = view;
        super.onCreateView(inflater, container, savedInstanceState);

        return view;
    }

    @Override
    public void initUI() {

    }

    @Override
    protected void lazyLoad() {

    }
}
