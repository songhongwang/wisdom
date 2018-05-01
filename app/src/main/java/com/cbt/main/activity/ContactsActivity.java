package com.cbt.main.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbt.main.R;
import com.cbt.main.fragment.ContactsFragment;
import com.cbt.main.fragment.MyMineFragment;
import com.cbt.main.model.MomentMode;
import com.cbt.main.utils.net.ApiClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vigorous on 17/12/19.
 * 融云聊天 (不能继承BaseActivity window.getDecorView().setSystemUiVisibilitychogn)
 */

public class ContactsActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);

        initUI();
    }

    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_profile);
    }

    public void initUI() {
        mTvTitle.setText("关注列表");
        mIvFinish.setVisibility(View.GONE);
        mIvBack.setVisibility(View.VISIBLE);
        final ContactsFragment contactsFragment = new ContactsFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_container,contactsFragment);
        transaction.commitAllowingStateLoss();

    }



}
