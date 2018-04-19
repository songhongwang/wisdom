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
import com.cbt.main.adapter.ZaiqingAdapter;
import com.cbt.main.app.GlobalApplication;
import com.cbt.main.callback.IWatcherImage;
import com.cbt.main.model.Data;
import com.cbt.main.model.IndexFeedModel;
import com.cbt.main.model.MomentMode;
import com.cbt.main.model.ZaiqingModel;
import com.cbt.main.utils.OnRcvScrollListener;
import com.cbt.main.utils.ToastUtils;
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
 * 朋友圈页面
 */

public class ZaiqingFragment extends BaseFragment {

    private RecyclerView vRecycler;
    private ZaiqingAdapter adapter;
    private int mPage;
    private boolean mIsLoading;
    private boolean mHasMore = true;
    private TwinklingRefreshLayout mTwinklingRefreshLayout;

    public static ZaiqingFragment getInstance(MomentMode mode){
        ZaiqingFragment fragment = new ZaiqingFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("mode",MomentMode.zai_qing);
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
        vRecycler = (RecyclerView) mRootView.findViewById(R.id.v_recycler);
        vRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        vRecycler.addItemDecoration(new SpaceItemDecoration(getActivity()).setSpace(5).setSpaceColor(0xFFECECEC));

        MessagePicturesLayout.Callback callback = null;
        if(getActivity() instanceof IWatcherImage){
            callback = ((IWatcherImage) getActivity()).getWatcherCallBack();
        }
        vRecycler.setAdapter(adapter = new ZaiqingAdapter(getActivity()).setPictureClickCallback(callback));

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
           // ToastUtils.show(getContext(), "mode --" + mode);
        }
    }

    @Override
    protected void lazyLoad() {
        getData();
    }

    private void getData() {
        mIsLoading = true;
        ApiClient.getInstance().getBasicService(GlobalApplication.mApp).getZaiqingForFm(mPage).enqueue(new Callback<List<ZaiqingModel>>() {
            @Override
            public void onResponse(Call<List<ZaiqingModel>> call, Response<List<ZaiqingModel>> response) {
                List<ZaiqingModel> dataList = response.body();

                mIsLoading = false;
                mPage ++;

                if(dataList.size() > 0){
                    List<Data> goodList = new ArrayList<>();
                    for(int i = 0; i< dataList.size(); i ++){
                        goodList.add(ZaiqingModel.convert(dataList.get(i)));
                    }
                    adapter.set(goodList);
                    adapter.notifyDataSetChanged();


                    mHasMore =true;
                }else{
                    mHasMore = false;
                }
            }

            @Override
            public void onFailure(Call<List<ZaiqingModel>> call, Throwable t) {

                mIsLoading = false;
            }
        });
    }
// test reset
}
