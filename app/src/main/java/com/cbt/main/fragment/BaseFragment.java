package com.cbt.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbt.main.R;

/**
 * Created by vigorous on 17/12/10.
 */

public abstract class BaseFragment extends Fragment {
    public View mRootView;
    public ImageView mIvBack;
    public TextView mTvTitle;
    public ImageView mIvComplete;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initHeader();
        initUI();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initHeader(){
        mIvBack = (ImageView) mRootView.findViewById(R.id.iv_back);
        mTvTitle = (TextView) mRootView.findViewById(R.id.tv_title);
        mIvComplete = (ImageView) mRootView.findViewById(R.id.iv_complete);
    }

    public abstract void initUI();


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }

    /**
     * 不可见
     */
    protected void onInvisible() {

    }

    /**
     * 延迟加载 子类必须重写此方法
     */
    protected abstract void lazyLoad();
}
