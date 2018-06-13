package com.cbt.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cbt.main.R;
import com.cbt.main.activity.MainActivity;
import com.cbt.main.adapter.ZaiqingAdapter;
import com.cbt.main.app.GlobalApplication;
import com.cbt.main.callback.IWatcherImage;
import com.cbt.main.dialog.ReplyDialog;
import com.cbt.main.model.BaseMsgModel;
import com.cbt.main.model.BottomTabTip;
import com.cbt.main.model.Data;
import com.cbt.main.model.MomentMode;
import com.cbt.main.model.MsgCountModel;
import com.cbt.main.model.ZaiqingModel;
import com.cbt.main.model.event.EventPublishSuccess;
import com.cbt.main.utils.OnRcvScrollListener;
import com.cbt.main.utils.net.ApiClient;
import com.cbt.main.view.piaoquan.MessagePicturesLayout;
import com.cbt.main.view.piaoquan.SpaceItemDecoration;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;
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
    List<Data> goodList = new ArrayList<>();
    private int mPage;
    private boolean mIsLoading;
    private boolean mHasMore = true;
    private View mVLoading;
    private int ismydo;
    private TwinklingRefreshLayout mTwinklingRefreshLayout;

    public static ZaiqingFragment getInstance(MomentMode mode,int ismy){
        ZaiqingFragment fragment = new ZaiqingFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("mode",MomentMode.zai_qing);
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
        EventBus.getDefault().register(this);
        ismydo = (int) getArguments().getSerializable("ismy");
        mVLoading = mRootView.findViewById(R.id.rl_loading);
        vRecycler = (RecyclerView) mRootView.findViewById(R.id.v_recycler);
        vRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        vRecycler.addItemDecoration(new SpaceItemDecoration(getActivity()).setSpace(5).setSpaceColor(0xFFECECEC));

        MessagePicturesLayout.Callback callback = null;
        if(getActivity() instanceof IWatcherImage){
            callback = ((IWatcherImage) getActivity()).getWatcherCallBack();
        }
        vRecycler.setAdapter(adapter = new ZaiqingAdapter(getActivity(),ismydo).setPictureClickCallback(callback));

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
           // ToastUtils.show(getContext(), "mode --" + mode);
        }
    }

    public void refresh(){
        getData();
    }

    @Override
    protected void lazyLoad() {
        getData();
    }

    private void showBottomTip(MsgCountModel msgCountModel){
        if(getActivity() instanceof MainActivity && getActivity() != null && msgCountModel != null){
            ((MainActivity)getActivity()).updateBottomTabTip(BottomTabTip.tab2, msgCountModel.getC1() > 0 || msgCountModel.getC2() > 0);
//            ((MainActivity)getActivity()).updateBottomTabTip(BottomTabTip.tab3, msgCountModel.getC2() > 0);
            ((MainActivity)getActivity()).updateBottomTabTip(BottomTabTip.tab4, msgCountModel.getC3() > 0);

        }
    }

    private void getData() {
        if(mVLoading != null){
            mVLoading.setVisibility(View.VISIBLE);
        }
        mIsLoading = true;
        ismydo = (int) getArguments().getSerializable("ismy");
        if (ismydo == 1)
        {
            ApiClient.getInstance().getBasicService(GlobalApplication.mApp).myZaiqingForFm(mPage).enqueue(new Callback<List<ZaiqingModel>>() {
                @Override
                public void onResponse(Call<List<ZaiqingModel>> call, Response<List<ZaiqingModel>> response) {
                    List<ZaiqingModel> dataList = response.body();

                    mIsLoading = false;

                    if (dataList.size() > 0) {
                        if(mPage == 0){
                            goodList.clear();
                        }

                        for (int i = 0; i < dataList.size(); i++) {
                            goodList.add(ZaiqingModel.convert(dataList.get(i)));
                        }

                        adapter.set(goodList);
                        adapter.notifyDataSetChanged();

                        mPage++;
                        mHasMore = true;
                    } else {
                        mHasMore = false;
                    }
                    if(mVLoading != null){
                        mVLoading.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onFailure(Call<List<ZaiqingModel>> call, Throwable t) {
                    if(mVLoading != null) {
                        mVLoading.setVisibility(View.GONE);
                    }

                    mIsLoading = false;
                }
            });
        }
        else if (ismydo == 2)
        {
            ApiClient.getInstance().getBasicService(GlobalApplication.mApp).scZaiqingForFm(mPage).enqueue(new Callback<List<ZaiqingModel>>() {
                @Override
                public void onResponse(Call<List<ZaiqingModel>> call, Response<List<ZaiqingModel>> response) {
                    List<ZaiqingModel> dataList = response.body();

                    mIsLoading = false;


                    if (dataList.size() > 0) {
                        if (mPage == 0) {
                            goodList.clear();
                        }
                        for (int i = 0; i < dataList.size(); i++) {
                            goodList.add(ZaiqingModel.convert(dataList.get(i)));
                        }
                        adapter.set(goodList);
                        adapter.notifyDataSetChanged();

                        mPage++;
                        mHasMore = true;
                    } else {
                        mHasMore = false;
                    }
                    if(mVLoading != null){
                        mVLoading.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onFailure(Call<List<ZaiqingModel>> call, Throwable t) {
                    if(mVLoading != null) {
                        mVLoading.setVisibility(View.GONE);
                    }

                    mIsLoading = false;
                }
            });
        }
        else if (ismydo == 8)
        {
            String otheruserid = (String) getArguments().getSerializable("otheruserid");
            ApiClient.getInstance().getBasicService(GlobalApplication.mApp).bierenZaiqingForFm(mPage,otheruserid).enqueue(new Callback<List<ZaiqingModel>>() {
                @Override
                public void onResponse(Call<List<ZaiqingModel>> call, Response<List<ZaiqingModel>> response) {
                    List<ZaiqingModel> dataList = response.body();

                    mIsLoading = false;

                    if (dataList.size() > 0) {
                        if(mPage == 0){
                            goodList.clear();
                        }

                        for (int i = 0; i < dataList.size(); i++) {
                            goodList.add(ZaiqingModel.convert(dataList.get(i)));
                        }

                        adapter.set(goodList);
                        adapter.notifyDataSetChanged();

                        mPage++;
                        mHasMore = true;
                    } else {
                        mHasMore = false;
                    }
                    if(mVLoading != null){
                        mVLoading.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onFailure(Call<List<ZaiqingModel>> call, Throwable t) {
                    if(mVLoading != null) {
                        mVLoading.setVisibility(View.GONE);
                    }

                    mIsLoading = false;
                }
            });
        }
        else {

//            ApiClient.getInstance().getBasicService(GlobalApplication.mApp).getZaiqingForFm(mPage).enqueue(new Callback<ZaiqingBigModel>() {
//                @Override
//                public void onResponse(Call<ZaiqingBigModel> call, Response<ZaiqingBigModel> response) {
//                    ZaiqingBigModel modelzong = response.body();
////                    showBottomTip(modelzong.getMscount());
//                    List<ZaiqingModel> dataList =modelzong.getNongqinglist();
//                    mIsLoading = false;
//
//                    if (dataList.size() > 0) {
//                        if(mPage == 0){
//                            goodList.clear();
//                        }
//                        for (int i = 0; i < dataList.size(); i++) {
//                            goodList.add(ZaiqingModel.convert(dataList.get(i)));
//                        }
//                        adapter.set(goodList);
//                        adapter.notifyDataSetChanged();
//
//                        mPage++;
//                        mHasMore = true;
//                    } else {
//                        mHasMore = false;
//                    }
//                    if(mVLoading != null){
//                        mVLoading.setVisibility(View.GONE);
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<ZaiqingBigModel> call, Throwable t) {
//                if(mVLoading != null) {
//                    mVLoading.setVisibility(View.GONE);
//                }
//
//                mIsLoading = false;
//                }
//            });

            ApiClient.getInstance().getBasicService(GlobalApplication.mApp).getZaiqingForFm(mPage).enqueue(new Callback<BaseMsgModel<List<ZaiqingModel>>>() {
                @Override
                public void onResponse(Call<BaseMsgModel<List<ZaiqingModel>>> call, Response<BaseMsgModel<List<ZaiqingModel>>> response) {
                    showBottomTip(response.body().getMscount());
                    List<ZaiqingModel> dataList =response.body().getNongqinglist();
                    mIsLoading = false;

                    if (dataList.size() > 0) {
                        if(mPage == 0){
                            goodList.clear();
                        }
                        for (int i = 0; i < dataList.size(); i++) {
                            goodList.add(ZaiqingModel.convert(dataList.get(i)));
                        }
                        adapter.set(goodList);
                        adapter.notifyDataSetChanged();

                        mPage++;
                        mHasMore = true;
                    } else {
                        mHasMore = false;
                    }
                    if(mVLoading != null){
                        mVLoading.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onFailure(Call<BaseMsgModel<List<ZaiqingModel>>> call, Throwable t) {
                if(mVLoading != null) {
                    mVLoading.setVisibility(View.GONE);
                }

                mIsLoading = false;
                }
            });
        }
    }
// test reset



    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventPublishSuccess publishSuccess) {
        mPage = 0;
        getData();
    }
}
