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
import com.cbt.main.app.GlobalApplication;
import com.cbt.main.callback.IWatcherImage;
import com.cbt.main.dialog.ReplyDialog;
import com.cbt.main.model.AgriculturalModel;
import com.cbt.main.model.Data;
import com.cbt.main.model.IndexFeedModel;
import com.cbt.main.model.MomentMode;
import com.cbt.main.utils.OnRcvScrollListener;
import com.cbt.main.utils.ToastUtils;
import com.cbt.main.utils.net.ApiClient;
import com.cbt.main.view.piaoquan.MessagePicturesLayout;
import com.cbt.main.view.piaoquan.SpaceItemDecoration;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vigorous on 17/12/10.
 * 朋友圈页面
 */

public class MomentsFragment extends BaseFragment {

    private RecyclerView vRecycler;
    private MessageAdapter adapter;
    List<Data> goodList = new ArrayList<>();
    private int mPage;
    private int ismydo;
    private boolean mIsLoading;
    private boolean mHasMore = true;
    private View mVLoading;
    private TwinklingRefreshLayout mTwinklingRefreshLayout;

    public static MomentsFragment getInstance(MomentMode mode,int ismy){
        MomentsFragment fragment = new MomentsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("mode",MomentMode.nong_qing);
        bundle.putSerializable("ismy",ismy);
        fragment.setArguments(bundle);
        return fragment;
    }

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
        mVLoading = mRootView.findViewById(R.id.rl_loading);
        vRecycler = (RecyclerView) mRootView.findViewById(R.id.v_recycler);
        vRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        vRecycler.addItemDecoration(new SpaceItemDecoration(getActivity()).setSpace(5).setSpaceColor(0xFFECECEC));

        MessagePicturesLayout.Callback callback = null;
        if(getActivity() instanceof IWatcherImage){
            callback = ((IWatcherImage) getActivity()).getWatcherCallBack();
        }
        vRecycler.setAdapter(adapter = new MessageAdapter(getActivity(),ismydo).setPictureClickCallback(callback));

//        Utils.fitsSystemWindows(isTranslucentStatus, mRootView.findViewById(R.id.v_fit));

        vRecycler.addOnScrollListener(new OnRcvScrollListener(){
            @Override
            public void onBottom() {
                super.onBottom();
                if(!mIsLoading && mHasMore){
                    getData();
                }
            }
        });

        adapter.setOnReplySuccessListener(new ReplyDialog.OnReplySuccessListener() {
        @Override
        public void onSuccess() {
            mPage = 0;
            getData();
        }
    });
    mTwinklingRefreshLayout = (TwinklingRefreshLayout) mRootView.findViewById(R.id.twinkRefreshlayout);
    ProgressLayout headerView = new ProgressLayout(getActivity());
        mTwinklingRefreshLayout.setHeaderView(headerView);
        mTwinklingRefreshLayout.setOverScrollBottomShow(false);
        mTwinklingRefreshLayout.setEnableLoadmore(false);
        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
        @Override
        public void onRefresh(TwinklingRefreshLayout refreshLayout) {
            super.onRefresh(refreshLayout);
            refreshLayout.finishRefreshing();
            mPage = 0;
            getData();
        }
    });

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments() != null){
            MomentMode mode = (MomentMode) getArguments().getSerializable("mode");
            //ToastUtils.show(getContext(), "mode --" + mode);
        }
    }
    // 更新页面
    public void refresh(){
        getData();
    }

    @Override
    protected void lazyLoad() {
        getData();
    }

    private void getData() {
        if(mVLoading != null){
            mVLoading.setVisibility(View.VISIBLE);
        }
        mIsLoading = true;
        ismydo = (int) getArguments().getSerializable("ismy");
        if (ismydo == 1)
        {
            ApiClient.getInstance().getBasicService(GlobalApplication.mApp).myMyfarmfarming(mPage).enqueue(new Callback<List<AgriculturalModel>>() {
                @Override
                public void onResponse(Call<List<AgriculturalModel>> call, Response<List<AgriculturalModel>> response) {
                    List<AgriculturalModel> dataList = response.body();


                    mIsLoading = false;
                    mPage ++;

                    if(dataList.size() > 0){
                        if(mPage == 0){
                            goodList.clear();
                        }
                        for(int i = 0; i< dataList.size(); i ++){
                            goodList.add(AgriculturalModel.convert(dataList.get(i)));
                        }
                        adapter.set(goodList);
                        adapter.notifyDataSetChanged();


                        mHasMore =true;
                    }else{
                        mHasMore = false;
                    }
                    if(mVLoading != null){
                        mVLoading.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onFailure(Call<List<AgriculturalModel>> call, Throwable t) {
                    if(mVLoading != null) {
                        mVLoading.setVisibility(View.GONE);
                    }

                    mIsLoading = false;
                }
            });
        }
        else         if (ismydo == 2) {
            ApiClient.getInstance().getBasicService(GlobalApplication.mApp).scMyfarmfarming(mPage).enqueue(new Callback<List<AgriculturalModel>>() {
                @Override
                public void onResponse(Call<List<AgriculturalModel>> call, Response<List<AgriculturalModel>> response) {
                    List<AgriculturalModel> dataList = response.body();

                    mIsLoading = false;
                    mPage++;
                    if (dataList.size() > 0) {
                        if (mPage == 0) {
                            goodList.clear();
                        }
                        for (int i = 0; i < dataList.size(); i++) {
                            goodList.add(AgriculturalModel.convert(dataList.get(i)));
                        }
                        adapter.set(goodList);
                        adapter.notifyDataSetChanged();
                    }

                    if (mVLoading != null) {
                        mVLoading.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<List<AgriculturalModel>> call, Throwable t) {
                    if (mVLoading != null) {
                        mVLoading.setVisibility(View.GONE);
                    }

                    mIsLoading = false;
                }
            });

        }
        else
        {
           ApiClient.getInstance().getBasicService(GlobalApplication.mApp).getMyfarmfarming(mPage).enqueue(new Callback<List<AgriculturalModel>>() {
               @Override
               public void onResponse(Call<List<AgriculturalModel>> call, Response<List<AgriculturalModel>> response) {
                   List<AgriculturalModel> dataList = response.body();


                   mIsLoading = false;
                   mPage ++;

                   if(dataList.size() > 0){
                       if(mPage == 0){
                           goodList.clear();
                       }
                       for(int i = 0; i< dataList.size(); i ++){
                           goodList.add(AgriculturalModel.convert(dataList.get(i)));
                       }
                       adapter.set(goodList);
                       adapter.notifyDataSetChanged();


                       mHasMore =true;
                   }else{
                       mHasMore = false;
                   }
                   if(mVLoading != null){
                       mVLoading.setVisibility(View.GONE);
                   }

               }

               @Override
               public void onFailure(Call<List<AgriculturalModel>> call, Throwable t) {
                   if(mVLoading != null) {
                       mVLoading.setVisibility(View.GONE);
                   }

                   mIsLoading = false;
               }
           });
        }
    }
// test reset
}
