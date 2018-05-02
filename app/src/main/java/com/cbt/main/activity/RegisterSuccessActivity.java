package com.cbt.main.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.cbt.main.R;
import com.cbt.main.adapter.MessageAdapter;
import com.cbt.main.moments.ImageWatcher;
import com.cbt.main.utils.Utils;
import com.cbt.main.view.piaoquan.MessagePicturesLayout;
import com.cbt.main.view.piaoquan.SpaceItemDecoration;

import java.util.List;

import butterknife.BindView;

/**
 * Created by vigorous on 17/12/12.
 */

public class RegisterSuccessActivity extends BaseActivity{
    @BindView(R.id.perfact_msg)
    Button btnPerfactMsg;

    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_register_success);

    }

    @Override
    public void initUI() {
        mTvTitle.setText("注册成功");
        mIvFinish.setVisibility(View.GONE);
        mIvBack.setVisibility(View.VISIBLE);


        btnPerfactMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterSuccessActivity.this, PerfactAccountActivity.class));
                finish();
            }
        });
    }

}
