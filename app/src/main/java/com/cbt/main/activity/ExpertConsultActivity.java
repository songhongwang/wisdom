package com.cbt.main.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cbt.main.R;
import com.cbt.main.adapter.MarketDetailActAdapter;
import com.cbt.main.model.MyproblemView;
import com.cbt.main.model.WentiModel;
import com.cbt.main.utils.ToastUtils;
import com.cbt.main.utils.net.ApiClient;
import com.cbt.main.view.piaoquan.MessagePicturesLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vigorous on 18/4/10.
 * 专家咨询
 */

public class ExpertConsultActivity extends BaseActivity2 {
    private MarketDetailActAdapter mMarketDetailActAdapter;

    MessagePicturesLayout lPictures;
    TextView mTvContentTitle, mTvContent,mTvAuthor, mTvTime, mTvSend,tv_replaycount;

    ListView mListView;
    EditText mEtInput;
    private boolean mIsLoading;
    private int mPage;
    private boolean mHasMore = true;
    List datas  = new ArrayList();
    private String iidf;
    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_market_detail);

    }

    @Override
    public void initUI() {
        mTvTitle.setText("咨询详情");

        mIvFinish.setVisibility(View.GONE);

        mEtInput = (EditText) findViewById(R.id.rc_edit_text);

        mListView = (ListView) findViewById(R.id.lv_disaster);
        View headerView = View.inflate(this, R.layout.header_zixun_detail_list, null);
        mListView.addHeaderView(headerView);

        mMarketDetailActAdapter = new MarketDetailActAdapter(this,datas);
        mListView.setAdapter(mMarketDetailActAdapter);

        lPictures = (MessagePicturesLayout) headerView.findViewById(R.id.l_pictures);
        mTvContentTitle = (TextView) headerView.findViewById(R.id.t_content_title);
        mTvAuthor = (TextView) headerView.findViewById(R.id.tv_author);
        mTvTime= (TextView) headerView.findViewById(R.id.tv_time);
        tv_replaycount = (TextView) headerView.findViewById(R.id.tv_replaycount);
        mTvSend = (TextView) findViewById(R.id.tv_send);
        mTvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = mEtInput.getText().toString();
                sendReply(msg,iidf);
                mEtInput.setText("");
            }
        });

        getData();
    }

    // 加载详情接口
    private void getData(){

        String iid = getIntent().getStringExtra("iid");
        iidf = iid;
        ApiClient.getInstance().getBasicService(this).getmyproblem(mPage,iid).enqueue(new Callback<MyproblemView>() {
            @Override
            public void onResponse(Call<MyproblemView> call, Response<MyproblemView> response) {
                MyproblemView dataList = response.body();

                mIsLoading = false;

                if(dataList != null){
                    mTvContentTitle.setText(dataList.getContent());
                    mTvAuthor.setText("发布人："+dataList.getUname());
                    mTvTime.setText("发布时间："+dataList.getTime());
                    tv_replaycount.setText(dataList.getMessagecount());
                    lPictures.set(dataList.getImglist(), dataList.getImglist());
                    if (dataList.getList().size() > 0)
                    {
                        if(mPage == 0){
                            datas.clear();
                        }

                        datas.addAll(dataList.getList());
                        mMarketDetailActAdapter.resetData(datas);
                        mMarketDetailActAdapter.notifyDataSetChanged();
                        mHasMore =true;
                        mPage ++;
                    }
                    else
                    {
                        mHasMore = false;
                    }
                }else{
                    mHasMore = false;
                }
            }

            @Override
            public void onFailure(Call<MyproblemView> call, Throwable t) {

                mIsLoading = false;
            }
        });

    }

    private void sendReply(String msg,String iid){
        ApiClient.getInstance().getBasicService(this).replyanswer(iid, msg).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(mPage > 0){
                    mPage --;
                }
                getData();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }
}
