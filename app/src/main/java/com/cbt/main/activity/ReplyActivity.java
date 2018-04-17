package com.cbt.main.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.cbt.main.R;
import com.cbt.main.utils.ToastUtils;
import com.cbt.main.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vigorous on 18/4/10.
 */

public class ReplyActivity extends BaseActivity2 {

    EditText mEtInput;

    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_reply);

    }

    @Override
    public void initUI() {
        mEtInput = (EditText) findViewById(R.id.et_reply);
        mIvFinish.setImageResource(R.drawable.icon_complete);
        mIvFinish.setVisibility(View.VISIBLE);
        mIvFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = mEtInput.getText().toString();
                sendReply(msg);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Utils.hideInputMethod(this,mEtInput);
        super.onBackPressed();
    }

    private void sendReply(String msg){
        // 这里对接发送回复接口
        ToastUtils.show(this, "回复消息：" + msg);
        Utils.hideInputMethod(this,mEtInput);
    }
}
