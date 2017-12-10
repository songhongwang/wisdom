package com.cbt.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cbt.main.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caobotao on 16/1/4.
 * 更多页面
 */
public class MoreFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab5, container, false);
        mRootView = view;
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public void initUI() {
        mIvBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void lazyLoad() {
    }
}
