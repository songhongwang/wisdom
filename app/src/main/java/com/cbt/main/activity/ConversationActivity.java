package com.cbt.main.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.cbt.main.R;
import com.cbt.main.model.RtokenRsp;
import com.cbt.main.utils.net.ApiClient;
import com.cbt.main.utils.net.RongYunTokenUtil;

import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vigorous on 17/12/19.
 * 融云聊天
 */

public class ConversationActivity extends BaseActivity {
    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.conversation);
    }

    @Override
    public void initUI() {


    }
}
