package com.cbt.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbt.main.R;
import com.cbt.main.model.ClientView;
import com.cbt.main.model.Data;
import com.cbt.main.model.Friend;
import com.cbt.main.model.User;
import com.cbt.main.utils.SharedPreferencUtil;
import com.cbt.main.utils.ToastUtils;
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
    Data mData= new Data();
    String userid;
    TextView mTvName, mTvDes;
    ImageView mIvAvatar;
    Button btn_attention;

    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_user);
    }

    @Override
    public void initUI() {
        userid = (String) getIntent().getSerializableExtra("otheruserid");
        mIvFinish.setVisibility(View.GONE);
        mIvAvatar = (ImageView) findViewById(R.id.iv_avatar);
        btn_attention = (Button) findViewById(R.id.btn_attention);
        mTvName = (TextView) findViewById(R.id.tv_user_name);
        mTvDes = (TextView) findViewById(R.id.tv_user_des);

        mTvTitle.setText("用户信息");
        if(!TextUtils.isEmpty(mData.getAvatar())){
            Picasso.with(this).load(Constants.getBaseUrl() + mData.getAvatar()).placeholder(R.drawable.default_image_error)
                    .transform(new CropCircleTransformation())
                    .into(mIvAvatar);
        }else{
            mIvAvatar.setImageResource(R.drawable.login_default_icon);
        }

        if(!TextUtils.isEmpty(mData.getNickname())){
            mTvName.setText(mData.getNickname());
        }else{
            mTvName.setText("匿名");
        }

        findViewById(R.id.rl_ta_nongqing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, MyAttentionActivity.class);
                intent.putExtra("otheruserid", userid);
                intent.putExtra("qufen", "nongqing");
                startActivity(intent);
            }
        });
        findViewById(R.id.rl_ta_zaiqing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, MyAttentionActivity.class);
                intent.putExtra("otheruserid", userid);
                intent.putExtra("qufen", "zaiqing");
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_send_msg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(mData.getIid())){
                    RongIM.getInstance().startPrivateChat(UserActivity.this, mData.getIid(), TextUtils.isEmpty(mData.getNickname()) ? "匿名":mData.getNickname());
//                    RongIM.getInstance().startPrivateChat(UserActivity.this, "cd_18600211554", "汪汪哒");
                }else{
                    ToastUtils.show(UserActivity.this, "用户数据异常");
                }

            }
        });
        findViewById(R.id.btn_attention).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn_attention.getText().equals("取消关注"))
                {
                    ApiClient.getInstance().getBasicService(UserActivity.this).delfollowComm(userid,"", 4).enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {
                            ToastUtils.show(UserActivity.this, "已取消关注");
                            btn_attention.setText("加关注");
                        }

                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {

                        }
                    });
                }
                else
                {
                    ApiClient.getInstance().getBasicService(UserActivity.this).followComm(userid,"", 4).enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {
                            ToastUtils.show(UserActivity.this, "已关注");
                            btn_attention.setText("取消关注");
                        }

                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {

                        }
                    });
                }

            }
        });

        User login = SharedPreferencUtil.getLogin(this);
        if(userid.equals(login.getUid())){
            //ToastUtils.show(UserActivity.this, "这个是你自己！");
            finish();
            return;
        }else{
            //findViewById(R.id.rl_ta_nongqing).setVisibility(View.GONE);
        }
        getData();
    }

    private void refreshUI(){
        if(!TextUtils.isEmpty(mData.getAvatar())){
            Picasso.with(this).load(Constants.getBaseUrl() + mData.getAvatar()).placeholder(R.drawable.default_image_error)
                    .transform(new CropCircleTransformation())
                    .into(mIvAvatar);
        }else{
            mIvAvatar.setImageResource(R.drawable.de_default_portrait);
        }

        if(!TextUtils.isEmpty(mData.getNickname())){
            mTvName.setText(mData.getNickname());
        }else{
            mTvName.setText("匿名");
        }


    }

    private void getData(){
        ApiClient.getInstance().getBasicService(this).getUser(userid).enqueue(new Callback<ClientView>() {
            @Override
            public void onResponse(Call<ClientView> call, Response<ClientView> response) {
                ClientView user = response.body();
                if(user != null && !TextUtils.isEmpty(user.getUid())){
                    if (user.isIsfocus() == false)
                    {
                        btn_attention.setText("加关注");
                    }
                    else
                    {
                        btn_attention.setText("取消关注");
                    }
                    mData.setUid(user.getUid());
                    mData.setIid(user.getUid());
                    mData.setAvatar(user.getUsericon());
                    mData.setNickname(user.getUname());
                    refreshUI();
                }
            }

            @Override
            public void onFailure(Call<ClientView> call, Throwable t) {
                ToastUtils.show(UserActivity.this, "网络异常");
            }
        });
    }
}
