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
import com.cbt.main.app.GlobalApplication;
import com.cbt.main.callback.IWatcherImage;
import com.cbt.main.model.Data;
import com.cbt.main.model.MarketinformationView;
import com.cbt.main.model.MomentMode;
import com.cbt.main.model.WentiModel;
import com.cbt.main.utils.OnRcvScrollListener;
import com.cbt.main.utils.net.ApiClient;
import com.cbt.main.view.piaoquan.MessagePicturesLayout;
import com.cbt.main.view.piaoquan.SpaceItemDecoration;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vigorous on 17/12/10.
 * 问答页面
 */

public class TopicFragment extends BaseFragment {
    private List<Data> goodList = new ArrayList<>();
    private RecyclerView vRecycler;
    private TopicAdapter adapter;
    private int mPage;
    private boolean mIsLoading;
    private boolean mHasMore = true;
    private TwinklingRefreshLayout mTwinklingRefreshLayout;

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
        MessagePicturesLayout.Callback callback = null;
        if(getActivity() instanceof IWatcherImage){
            callback = ((IWatcherImage) getActivity()).getWatcherCallBack();
        }
        vRecycler.setAdapter(adapter = new TopicAdapter(getActivity()).setPictureClickCallback(callback));
//        adapter.set(Data.get());
        vRecycler.addOnScrollListener(new OnRcvScrollListener(){
            @Override
            public void onBottom() {
                super.onBottom();
                if(!mIsLoading && mHasMore){
                    getData();
                }
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


//        Utils.fitsSystemWindows(isTranslucentStatus, mRootView.findViewById(R.id.v_fit));
    }

    private void getData() {
        mIsLoading = true;
        MomentMode mode = (MomentMode) getArguments().getSerializable("mode");
        if (mode == MomentMode.zj_zuixin)
        {
            ApiClient.getInstance().getBasicService(GlobalApplication.mApp).getmarketnew(mPage).enqueue(new Callback<List<MarketinformationView>>() {
                @Override
                public void onResponse(Call<List<MarketinformationView>> call, Response<List<MarketinformationView>> response) {
                    List<MarketinformationView> dataList = response.body();

                    mIsLoading = false;
                    mPage ++;

                    if(dataList.size() > 0){
                        if(mPage ==0){
                            goodList.clear();
                        }
                        for(int i = 0; i< dataList.size(); i ++){
                            goodList.add(MarketinformationView.convert(dataList.get(i)));
                        }
                        adapter.set(goodList);
                        adapter.notifyDataSetChanged();


                        mHasMore =true;
                    }else{
                        mHasMore = false;
                    }
                }

                @Override
                public void onFailure(Call<List<MarketinformationView>> call, Throwable t) {

                    mIsLoading = false;
                }
            });
        }
        else if (mode == MomentMode.zj_wode)
        {
            ApiClient.getInstance().getBasicService(GlobalApplication.mApp).getmarketmypublish(mPage).enqueue(new Callback<List<MarketinformationView>>() {
                @Override
                public void onResponse(Call<List<MarketinformationView>> call, Response<List<MarketinformationView>> response) {
                    List<MarketinformationView> dataList = response.body();

                    mIsLoading = false;
                    mPage ++;

                    if(dataList.size() > 0){
                        for(int i = 0; i< dataList.size(); i ++){
                            goodList.add(MarketinformationView.convert(dataList.get(i)));
                        }
                        adapter.set(goodList);
                        adapter.notifyDataSetChanged();


                        mHasMore =true;
                    }else{
                        mHasMore = false;
                    }
                }

                @Override
                public void onFailure(Call<List<MarketinformationView>> call, Throwable t) {

                    mIsLoading = false;
                }
            });
        }
//        else
//        {
//            ApiClient.getInstance().getBasicService(getContext()).getmarketnew(mPage).enqueue(new Callback<List<MarketinformationView>>() {
//                @Override
//                public void onResponse(Call<List<MarketinformationView>> call, Response<List<MarketinformationView>> response) {
//                    List<MarketinformationView> dataList = response.body();
//
//                    mIsLoading = false;
//                    mPage ++;
//
//                    if(dataList.size() > 0){
//                        for(int i = 0; i< dataList.size(); i ++){
//                            goodList.add(MarketinformationView.convert(dataList.get(i)));
//                        }
//                        adapter.set(goodList);
//                        adapter.notifyDataSetChanged();
//
//
//                        mHasMore =true;
//                    }else{
//                        mHasMore = false;
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<List<MarketinformationView>> call, Throwable t) {
//
//                    mIsLoading = false;
//                }
//            });
//        }

    }

    @Override
    protected void lazyLoad() {
        getData();
    }

}
