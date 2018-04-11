package com.cbt.main.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.cbt.main.R;
import com.cbt.main.adapter.DisasterActAdapter;
import com.cbt.main.utils.ToastUtils;
import com.cbt.main.view.piaoquan.MessagePicturesLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vigorous on 18/4/10.
 */

public class MarketDetailActivity extends BaseActivity2 {
    private DisasterActAdapter mDisasterActAdapter;

    MessagePicturesLayout lPictures;
    TextView mTvContentTitle, mTvContent,mTvAuthor, mTvTime, mTvSend;

    ListView mListView;
    EditText mEtInput;


    List datas  = new ArrayList();
    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_market_detail);

    }

    @Override
    public void initUI() {

        mIvFinish.setVisibility(View.GONE);

        mEtInput = (EditText) findViewById(R.id.rc_edit_text);

        mListView = (ListView) findViewById(R.id.lv_disaster);
        View headerView = View.inflate(this, R.layout.header_market_detail_list, null);
        mListView.addHeaderView(headerView);

        mDisasterActAdapter = new DisasterActAdapter(this,datas);
        mListView.setAdapter(mDisasterActAdapter);

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
                sendReply(msg);
                mEtInput.setText("");
            }
        });

        getData();
    }

    // 加载详情接口
    private void getData(){
        mTvContentTitle.setText("优质大米出售");
        mTvAuthor.setText("发布人：张三");
        mTvTime.setText("发布时间：2017-02-02");
        mTvContent.setText("内容xxxxxxxxxxxxxx");


        datas.add("https://avatar.csdn.net/D/A/2/3_bigdog_1027.jpg");
        datas.add("https://avatar.csdn.net/D/A/2/3_bigdog_1027.jpg");
        datas.add("https://avatar.csdn.net/D/A/2/3_bigdog_1027.jpg");
        datas.add("https://avatar.csdn.net/D/A/2/3_bigdog_1027.jpg");
        datas.add("https://avatar.csdn.net/D/A/2/3_bigdog_1027.jpg");
        datas.add("https://avatar.csdn.net/D/A/2/3_bigdog_1027.jpg");
        datas.add("https://avatar.csdn.net/D/A/2/3_bigdog_1027.jpg");

        lPictures.set(datas, datas);
    }

    private void sendReply(String msg){
        // 这里对接发送回复接口
        ToastUtils.show(this, "发送消息：" + msg);
    }
}
