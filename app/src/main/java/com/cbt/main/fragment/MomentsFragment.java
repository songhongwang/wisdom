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
import com.cbt.main.callback.IWatcherImage;
import com.cbt.main.model.Data;
import com.cbt.main.view.piaoquan.MessagePicturesLayout;
import com.cbt.main.view.piaoquan.SpaceItemDecoration;

/**
 * Created by vigorous on 17/12/10.
 * 朋友圈页面
 */

public class MomentsFragment extends BaseFragment {

    private RecyclerView vRecycler;
    private MessageAdapter adapter;

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
        vRecycler.addItemDecoration(new SpaceItemDecoration(getActivity()).setSpace(5).setSpaceColor(0xFFECECEC));

        MessagePicturesLayout.Callback callback = null;
        if(getActivity() instanceof IWatcherImage){
            callback = ((IWatcherImage) getActivity()).getWatcherCallBack();
        }
        vRecycler.setAdapter(adapter = new MessageAdapter(getActivity()).setPictureClickCallback(callback));
        adapter.set(Data.get());

//        Utils.fitsSystemWindows(isTranslucentStatus, mRootView.findViewById(R.id.v_fit));
    }

    @Override
    protected void lazyLoad() {

    }

//    @Override
//    public void onBackPressed() {
//        if (!vImageWatcher.handleBackPressed()) {
//            super.onBackPressed();
//        }
//    }
}
