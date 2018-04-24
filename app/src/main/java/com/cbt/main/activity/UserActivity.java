package com.cbt.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbt.main.R;
import com.cbt.main.model.Data;
import com.cbt.main.model.Friend;
import com.cbt.main.utils.net.ApiClient;
import com.cbt.main.utils.net.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.rong.imkit.RongIM;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vigorous on 18/4/12.
 */

public class UserActivity extends BaseActivity {
//    Data mData;
    TextView mTvName, mTvDes;
    ImageView mIvAvatar;
    String mUid;

    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_user);
    }

    @Override
    public void initUI() {
//        mData = (Data) getIntent().getSerializableExtra("model");
        mIvFinish.setVisibility(View.GONE);
        mIvAvatar = (ImageView) findViewById(R.id.iv_avatar);
        mTvName = (TextView) findViewById(R.id.tv_user_name);
        mTvDes = (TextView) findViewById(R.id.tv_user_des);

//        Picasso.with(this).load(Constants.getBaseUrl() + mData.getAvatar()).placeholder(R.drawable.default_image_error)
//                .transform(new CropCircleTransformation())
//                .into(mIvAvatar);
//        mTvName.setText(mData.getNickname());

        findViewById(R.id.rl_ta_nongqing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, MyAttentionActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.rl_ta_zaiqing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, MyAttentionActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_send_msg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                RongIM.getInstance().startPrivateChat(UserActivity.this, mData.getIid(), mData.getNickname());
            }
        });
        findViewById(R.id.btn_attention).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 添加关注
            }
        });

        getData();
    }

    private void getData(){
        ApiClient.getInstance().getBasicService(this).getUser(mUid).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }
}
