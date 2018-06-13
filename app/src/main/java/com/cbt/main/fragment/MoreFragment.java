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

import com.bumptech.glide.Glide;
import com.cbt.main.R;
import com.cbt.main.activity.ContactsActivity;
import com.cbt.main.activity.MyFabuActivity;
import com.cbt.main.activity.MyProfileActivity;
import com.cbt.main.activity.MyShoucangActivity;
import com.cbt.main.activity.PerfactAccountActivity;
import com.cbt.main.activity.SettingActivity;
import com.cbt.main.app.GlobalApplication;
import com.cbt.main.model.User;
import com.cbt.main.model.ZaiqingModel;
import com.cbt.main.model.event.EventUpdateUser;
import com.cbt.main.model.event.OnBackPressedEvent;
import com.cbt.main.utils.SharedPreferencUtil;
import com.cbt.main.utils.net.ApiClient;
import com.cbt.main.utils.net.Constants;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.rong.eventbus.EventBus;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vigorous on 16/1/4.
 * 更多页面
 */
public class MoreFragment extends BaseFragment {

    private TextView tvzuowu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab5, container, false);
        mRootView = view;
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public void initUI() {
        EventBus.getDefault().register(this);
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
         updateUserUI();

        tvzuowu = (TextView)mRootView.findViewById(R.id.tv_zuowustr);

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
        updateUserUI();
    }

    private void updateUserUI(){
        final User login = SharedPreferencUtil.getLogin(GlobalApplication.mApp);
        if(login != null){
            ((TextView)mRootView.findViewById(R.id.tv_user_name)).setText(login.getUname());
            ((TextView)mRootView.findViewById(R.id.tv_user_des)).setText("查看或编辑个人资料");

            ImageView ivAvatar = (ImageView) mRootView.findViewById(R.id.iv_crops);
            if(!TextUtils.isEmpty(login.getIcon())){
                Glide.with(getActivity()).load(Constants.getBaseUrl() + login.getIcon()).into(ivAvatar);
            }
        }
    }

    public void getData() {
        ApiClient.getInstance().getBasicService(GlobalApplication.mApp).finduserzuowu().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                List<String> dataList = response.body();
                if (dataList.size() > 0)
                {
                    tvzuowu.setText(dataList.get(0));
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventUpdateUser eventUpdateUser) {
        updateUserUI();
    }
}
