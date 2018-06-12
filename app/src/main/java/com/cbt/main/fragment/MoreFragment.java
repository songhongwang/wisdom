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

import com.cbt.main.R;
import com.cbt.main.activity.ContactsActivity;
import com.cbt.main.activity.MyFabuActivity;
import com.cbt.main.activity.MyProfileActivity;
import com.cbt.main.activity.MyShoucangActivity;
import com.cbt.main.activity.PerfactAccountActivity;
import com.cbt.main.activity.SettingActivity;
import com.cbt.main.app.GlobalApplication;
import com.cbt.main.model.User;
import com.cbt.main.utils.SharedPreferencUtil;
import com.cbt.main.utils.net.Constants;
import com.squareup.picasso.Picasso;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by vigorous on 16/1/4.
 * 更多页面
 */
public class MoreFragment extends BaseFragment {
    private CropCircleTransformation mCropCircleTransformation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab5, container, false);
        mRootView = view;
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public void initUI() {
        mTvTitle.setText("更多");
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setImageResource(R.drawable.nav_icon_message);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RongIM.getInstance().startSubConversationList(getActivity(), Conversation.ConversationType.PRIVATE);
            }
        });
        mIvComplete.setVisibility(View.GONE);

        final User login = SharedPreferencUtil.getLogin(GlobalApplication.mApp);
        if(login != null){
            ((TextView)mRootView.findViewById(R.id.tv_user_name)).setText(login.getUname());
            ((TextView)mRootView.findViewById(R.id.tv_user_des)).setText("查看或编辑个人资料");

            ImageView ivAvatar = (ImageView) mRootView.findViewById(R.id.iv_crops);
            if(!TextUtils.isEmpty(login.getIcon())){
                mCropCircleTransformation = new CropCircleTransformation();

                Picasso.with(getActivity()).load(Constants.getBaseUrl() + login.getIcon()).placeholder(R.drawable.login_default_icon)
                        .transform(mCropCircleTransformation)
                        .into(ivAvatar);
            }
        }



        mRootView.findViewById(R.id.rl_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyProfileActivity.class);
                intent.putExtra("user", login);
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
                intent.putExtra("from", "MoreFragment");
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
