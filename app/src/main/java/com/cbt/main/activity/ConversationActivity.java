package com.cbt.main.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbt.main.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.RongIMClient;

/**
 * Created by vigorous on 17/12/19.
 * 融云聊天 (不能继承BaseActivity window.getDecorView().setSystemUiVisibilitychogn)
 */

public class ConversationActivity extends FragmentActivity {
    @Nullable@BindView(R.id.tv_title)
    TextView mTvTitle;
    @Nullable@BindView(R.id.iv_back)
    ImageView mIvBack;
    @Nullable@BindView(R.id.iv_complete)
    ImageView mIvFinish;

    private String mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
        ButterKnife.bind(this);

        initUI();
    }

    public void initUI() {
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mTitle = getIntent().getData().getQueryParameter("title");
        mTvTitle.setText(mTitle);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
