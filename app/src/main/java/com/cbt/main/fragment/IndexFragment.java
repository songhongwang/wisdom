package com.cbt.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cbt.main.R;
import com.cbt.main.engin.SceneSurfaceView;

/**
 * Created by caobotao on 16/1/4.
 */
public class IndexFragment extends Fragment {
//    private SceneSurfaceView mSceneSurfaceView;
//    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1, container, false);
//        mRootView = view;
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mSceneSurfaceView = (SceneSurfaceView) mRootView.findViewById(R.id.mSceneSurfaceView);
    }

}
