package com.cbt.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.cbt.main.R;
import com.cbt.main.activity.WeatherForcastActivity;
import com.cbt.main.adapter.IndexProductAdapter;
import com.cbt.main.engin.SceneSurfaceView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vigorous on 16/1/4.
 * 首页
 */
public class IndexFragment extends BaseFragment {

    @Nullable@BindView(R.id.listView)
    ListView mLv;
    private IndexProductAdapter mIndexProductAdapter;

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
        mLv = (ListView) mRootView.findViewById(R.id.listView) ;
        mRootView.findViewById(R.id.tv_product_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mRootView.findViewById(R.id.tv_weather).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WeatherForcastActivity.class);
                startActivity(intent);
            }
        });


        mTvTitle.setText("北京市海淀区");
        List<String> dataList = new ArrayList<>();
        dataList.add("aaa");
        dataList.add("aaa");
        dataList.add("aaa");
        dataList.add("aaa");
        dataList.add("aaa");
        mIndexProductAdapter = new IndexProductAdapter(dataList, getActivity());
        mLv.setAdapter(mIndexProductAdapter);
    }

    @Override
    protected void lazyLoad() {

    }


}
