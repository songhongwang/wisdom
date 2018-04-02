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
import com.cbt.main.model.IndexFeedModel;
import com.cbt.main.model.MomentMode;
import com.cbt.main.utils.OnRcvScrollListener;
import com.cbt.main.utils.ToastUtils;
import com.cbt.main.utils.net.ApiClient;
import com.cbt.main.view.piaoquan.MessagePicturesLayout;
import com.cbt.main.view.piaoquan.SpaceItemDecoration;

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
    private int mPage;
    private boolean mIsLoading;
    private boolean mHasMore = true;

    public static MomentsFragment getInstance(MomentMode mode){
        MomentsFragment fragment = new MomentsFragment();
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
        vRecycler.setAdapter(adapter = new MessageAdapter(getActivity()).setPictureClickCallback(callback));

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

        getData();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments() != null){
            MomentMode mode = (MomentMode) getArguments().getSerializable("mode");
            ToastUtils.show(getContext(), "mode --" + mode);
        }
    }

    @Override
    protected void lazyLoad() {

    }

    private void getData() {
        mIsLoading = true;
        ApiClient.getInstance().getBasicService(getContext()).getIndexFeed(mPage).enqueue(new Callback<List<IndexFeedModel>>() {
            @Override
            public void onResponse(Call<List<IndexFeedModel>> call, Response<List<IndexFeedModel>> response) {
                List<IndexFeedModel> dataList = response.body();

                mIsLoading = false;
                mPage ++;

                if(dataList.size() > 0){
                    List<Data> goodList = new ArrayList<>();
                    for(int i = 0; i< dataList.size(); i ++){
                        goodList.add(IndexFeedModel.convert(dataList.get(i)));
                    }
                    adapter.set(goodList);
                    adapter.notifyDataSetChanged();


                    mHasMore =true;
                }else{
                    mHasMore = false;
                }
            }

            @Override
            public void onFailure(Call<List<IndexFeedModel>> call, Throwable t) {

                mIsLoading = false;
            }
        });
    }
}
