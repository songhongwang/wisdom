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
public class MoreFragment extends Fragment {

    private View mRootView;

    ImageView mIvBack;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab5, container, false);
        mRootView = view;
        initUI();
        return view;
    }

    private void initUI() {
        mIvBack = (ImageView) mRootView.findViewById(R.id.iv_back);
        mIvBack.setVisibility(View.VISIBLE);
    }
}
