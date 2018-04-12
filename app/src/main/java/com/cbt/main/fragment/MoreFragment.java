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
import com.cbt.main.activity.PerfactAccountActivity;
import com.cbt.main.activity.SelectMapLocationActivity;
import com.cbt.main.activity.SettingActivity;
import com.cbt.main.app.GlobalApplication;

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
    }

    @Override
    protected void lazyLoad() {
    }
}
