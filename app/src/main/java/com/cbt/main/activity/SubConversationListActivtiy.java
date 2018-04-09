package com.cbt.main.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cbt.main.R;

/**
 * Created by vigorous on 17/12/19.
 * 融云聊天
 */

public class SubConversationListActivtiy extends BaseActivity {

    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.subconversationlist);
    }

    @Override
    public void initUI() {
        mTvTitle.setText("所有聊天");
    }
}
