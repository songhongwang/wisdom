package com.cbt.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cbt.main.R;
import com.cbt.main.activity.ConversationListActivity;
import com.cbt.main.activity.SettingActivity;
import com.cbt.main.app.GlobalApplication;

import io.rong.imkit.RongIM;

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
        GlobalApplication.mApp.updateLocation();
        Toast.makeText(getActivity(), "location" + GlobalApplication.mLocationData.addr, Toast.LENGTH_SHORT).show();


        mRootView.findViewById(R.id.rl_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });

        mRootView.findViewById(R.id.rl_chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), ConversationListActivity.class);
//                startActivity(intent);
                /**
                 * 启动单聊界面。
                 *
                 * @param context      应用上下文。
                 * @param targetUserId 要与之聊天的用户 Id。
                 * @param title        聊天的标题，开发者需要在聊天界面通过 intent.getData().getQueryParameter("title")
                 *                     获取该值, 再手动设置为聊天界面的标题。
                 */
                RongIM.getInstance().startPrivateChat(getActivity(), "9527", "标题");
            }
        });
    }

    @Override
    protected void lazyLoad() {
    }
}
