package com.cbt.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cbt.main.R;
import com.cbt.main.activity.ContactsActivity;
import com.cbt.main.activity.ConversationListActivity;
import com.cbt.main.activity.MyAttentionActivity;
import com.cbt.main.activity.MyFabuActivity;
import com.cbt.main.activity.MyProfileActivity;
import com.cbt.main.activity.MyShoucangActivity;
import com.cbt.main.activity.PerfactAccountActivity;
import com.cbt.main.activity.SelectMapLocationActivity;
import com.cbt.main.activity.SettingActivity;
import com.cbt.main.app.GlobalApplication;
import com.cbt.main.model.User;
import com.cbt.main.utils.SharedPreferencUtil;
import com.cbt.main.utils.net.Constants;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * Created by vigorous on 16/1/4.
 * 更多页面
 */
public class MoreFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab5, container, false);
        mRootView = view;
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public void initUI() {
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setImageResource(R.drawable.nav_icon_message);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RongIM.getInstance().startSubConversationList(getActivity(), Conversation.ConversationType.PRIVATE);
            }
        });
        mIvComplete.setVisibility(View.GONE);

        User login = SharedPreferencUtil.getLogin(GlobalApplication.mApp);
        if(login != null){
            ((TextView)mRootView.findViewById(R.id.tv_user_name)).setText(login.getUname());
            ((TextView)mRootView.findViewById(R.id.tv_user_des)).setText(login.getUsname());

            ImageView ivAvatar = (ImageView) mRootView.findViewById(R.id.iv_crops);
            if(!TextUtils.isEmpty(login.getIcon())){
                Glide.with(getActivity()).load(Constants.getBaseUrl() + login.getIcon()).into(ivAvatar);
            }
        }



        mRootView.findViewById(R.id.rl_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyProfileActivity.class);
                startActivity(intent);
            }
        });
        mRootView.findViewById(R.id.rl_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });
        mRootView.findViewById(R.id.rl_nongzhuang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PerfactAccountActivity.class);
                startActivity(intent);
            }
        });
        mRootView.findViewById(R.id.rl_fabu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyFabuActivity.class);
                startActivity(intent);
            }
        });
        mRootView.findViewById(R.id.rl_shoucang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyShoucangActivity.class);
                startActivity(intent);
            }
        });
        mRootView.findViewById(R.id.rl_guanzhu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ContactsActivity.class);
                startActivity(intent);
            }
        });
        getData();
    }

    @Override
    protected void lazyLoad() {
    }

    public void getData() {

    }
}
