package com.cbt.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cbt.main.R;
import com.cbt.main.adapter.MessageAdapter;
import com.cbt.main.adapter.TopicAdapter;
import com.cbt.main.callback.IWatcherImage;
import com.cbt.main.model.Data;
import com.cbt.main.view.piaoquan.MessagePicturesLayout;
import com.cbt.main.view.piaoquan.SpaceItemDecoration;

/**
 * Created by vigorous on 17/12/10.
 * 问答页面
 */

public class TopicFragment extends BaseFragment {

    private RecyclerView vRecycler;
    private TopicAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moments, container, false);
        mRootView = view;
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void initUI() {
        vRecycler = (RecyclerView) mRootView.findViewById(R.id.v_recycler);
        vRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        vRecycler.addItemDecoration(new SpaceItemDecoration(getActivity()).setSpace(1).setSpaceColor(0xFFECECEC));

        vRecycler.setAdapter(adapter = new TopicAdapter(getActivity()));
//        adapter.set(Data.get());

//        Utils.fitsSystemWindows(isTranslucentStatus, mRootView.findViewById(R.id.v_fit));
    }

    @Override
    protected void lazyLoad() {

    }

}
