package com.cbt.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cbt.main.R;
import com.cbt.main.adapter.IndexProductAdapter;
import com.cbt.main.adapter.MoreProductAdapter;
import com.cbt.main.app.GlobalApplication;
import com.cbt.main.model.IndexProductModel;
import com.cbt.main.model.User;
import com.cbt.main.utils.SharedPreferencUtil;
import com.cbt.main.utils.ToastUtils;
import com.cbt.main.utils.net.ApiClient;
import com.cbt.main.view.ClearWriteEditText;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AMing on 16/1/15.
 * Company RongCloud
 */
public class MoreProductActivity extends BaseActivity{
    private MoreProductAdapter mMoreProductAdapter;
    List<IndexProductModel> mDataList;
    @BindView(R.id.listView)
    ListView mLv;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout mTwinklingRefreshLayout;
    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_more_product);

    }

    @Override
    public void initUI() {
        mTvTitle.setText("更多农气产品");
        mIvFinish.setVisibility(View.GONE);
        mDataList = new ArrayList<>();
        mMoreProductAdapter = new MoreProductAdapter(mDataList, this);
        mLv.setAdapter(mMoreProductAdapter);
        getProductList();

        ProgressLayout headerView = new ProgressLayout(this);
        mTwinklingRefreshLayout.setHeaderView(headerView);
        mTwinklingRefreshLayout.setEnableLoadmore(false);
        mTwinklingRefreshLayout.setOverScrollBottomShow(false);
        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                refreshLayout.finishRefreshing();
                getProductList();
            }
        });
    }


    private void getProductList(){
        ApiClient.getInstance().getBasicService(GlobalApplication.mApp).getIndexProduct(0).enqueue(new Callback<List<IndexProductModel>>() {
            @Override
            public void onResponse(Call<List<IndexProductModel>> call, Response<List<IndexProductModel>> response) {

                List<IndexProductModel> dataList = response.body();
                mDataList.addAll(dataList);
                mMoreProductAdapter.resetData(mDataList);
                mMoreProductAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<IndexProductModel>> call, Throwable t) {

            }
        });
    }
}
