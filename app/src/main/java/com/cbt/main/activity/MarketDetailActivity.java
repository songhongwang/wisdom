package com.cbt.main.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.cbt.main.R;
import com.cbt.main.adapter.MarketDetailActAdapter;
import com.cbt.main.adapter.MarketDetailActNewAdapter;
import com.cbt.main.model.MarketinformationDetailView;
import com.cbt.main.model.MyproblemView;
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
 * 市场详情
 */

public class MarketDetailActivity extends BaseActivity2 {
    private MarketDetailActNewAdapter mMarketDetailActAdapter;

    MessagePicturesLayout lPictures;
    TextView mTvContentTitle, mTvContent,mTvAuthor, mTvTime, mTvSend;

    ListView mListView;
    EditText mEtInput;
    private boolean mIsLoading;
    private int mPage;
    private boolean mHasMore = true;
    private String iidf;
    List datas  = new ArrayList();
    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_market_detail);

    }

    @Override
    public void initUI() {
        mTvTitle.setText("市场详情");

        mIvFinish.setVisibility(View.GONE);

        mEtInput = (EditText) findViewById(R.id.rc_edit_text);

        mListView = (ListView) findViewById(R.id.lv_disaster);
        View headerView = View.inflate(this, R.layout.header_market_detail_list, null);
        mListView.addHeaderView(headerView);

        mMarketDetailActAdapter = new MarketDetailActNewAdapter(this,datas);
        mListView.setAdapter(mMarketDetailActAdapter);

        lPictures = (MessagePicturesLayout) headerView.findViewById(R.id.l_pictures);
        mTvContentTitle = (TextView) headerView.findViewById(R.id.t_content_title);
        mTvContent = (TextView) headerView.findViewById(R.id.t_content);
        mTvAuthor = (TextView) headerView.findViewById(R.id.tv_author);
        mTvTime= (TextView) headerView.findViewById(R.id.tv_time);

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
//
//    // 加载详情接口
//    private void getData(){
//        String kkkk = getIntent().getStringExtra("amsg");
//        ToastUtils.show(this, "发送消息：" + kkkk);
//        mTvContentTitle.setText("优质大米出售");
//        mTvAuthor.setText("发布人：张三");
//        mTvTime.setText("发布时间：2017-02-02");
//        mTvContent.setText("内容xxxxxxxxxxxxxx");
//
//
//        datas.add("https://avatar.csdn.net/D/A/2/3_bigdog_1027.jpg");
//        datas.add("https://avatar.csdn.net/D/A/2/3_bigdog_1027.jpg");
//        datas.add("https://avatar.csdn.net/D/A/2/3_bigdog_1027.jpg");
//        datas.add("https://avatar.csdn.net/D/A/2/3_bigdog_1027.jpg");
//        datas.add("https://avatar.csdn.net/D/A/2/3_bigdog_1027.jpg");
//        datas.add("https://avatar.csdn.net/D/A/2/3_bigdog_1027.jpg");
//        datas.add("https://avatar.csdn.net/D/A/2/3_bigdog_1027.jpg");
//
//        lPictures.set(datas, datas);
//    }

    private void getData(){

        String iid = getIntent().getStringExtra("iid");
        iidf = iid;
        ApiClient.getInstance().getBasicService(this).getshichangxiangqing(0,iid).enqueue(new Callback<MarketinformationDetailView>() {
            @Override
            public void onResponse(Call<MarketinformationDetailView> call, Response<MarketinformationDetailView> response) {
                MarketinformationDetailView dataList = response.body();

                mIsLoading = false;


                if(dataList != null){
                    mTvContentTitle.setText(dataList.getItitle());
                    mTvContent.setText(dataList.getContent());
                    mTvAuthor.setText(dataList.getFaburen());
                    mTvTime.setText(dataList.getFabushijian());
                    lPictures.set(dataList.getImglist(), dataList.getImglist());
                    if (dataList.getRlist().size() > 0)
                    {
                        if(mPage == 0){
                            datas.clear();
                        }
                        datas.addAll(dataList.getRlist());
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
            public void onFailure(Call<MarketinformationDetailView> call, Throwable t) {

                mIsLoading = false;
            }
        });

    }

    private void sendReply(String msg,String iid){
        // 这里对接发送回复接口
        ApiClient.getInstance().getBasicService(this).replyShichang(iid, null, msg).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                mPage = 0;
                getData();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }
}
