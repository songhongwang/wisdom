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
import com.cbt.main.adapter.ExpertConsultActAdapter;
import com.cbt.main.adapter.MarketDetailActAdapter;
import com.cbt.main.utils.ToastUtils;
import com.cbt.main.view.piaoquan.MessagePicturesLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vigorous on 18/4/10.
 * 专家咨询
 */

public class ExpertConsultActivity extends BaseActivity2 {
    private ExpertConsultActAdapter mExpertConsultActAdapter;

    ListView mListView;
    RelativeLayout mTvToAnswer;

    // 这里替换成接口返回的model
    List datas  = new ArrayList();
    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_expert_consult);

    }

    @SuppressLint("WrongViewCast")
    @Override
    public void initUI() {
        mTvTitle.setText("专家咨询");
        mIvFinish.setVisibility(View.GONE);

        mTvToAnswer = (RelativeLayout) findViewById(R.id.rl_to_answer);

        mListView = (ListView) findViewById(R.id.lv_disaster);

        mExpertConsultActAdapter = new ExpertConsultActAdapter(this,datas);
        mListView.setAdapter(mExpertConsultActAdapter);


        mTvToAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpertConsultActivity.this, ReplyActivity.class);
                startActivity(intent);
            }
        });

        getData();
    }

    // 加载详情接口
    private void getData(){

        datas.add("aaaa");
        datas.add("aaaa");
        datas.add("aaaa");
        datas.add("aaaa");
        datas.add("aaaa");
        datas.add("aaaa");
        datas.add("aaaa");

    }

    private void toAnswer(String msg){
        // 这里对接发送回复接口
        ToastUtils.show(this, "发送消息：" + msg);
    }
}
