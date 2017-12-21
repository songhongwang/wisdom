package com.cbt.main.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.cbt.main.R;
import com.cbt.main.adapter.ConversationListAdapterEx;
import com.cbt.main.model.RtokenRsp;
import com.cbt.main.utils.net.ApiClient;
import com.cbt.main.utils.net.RongYunTokenUtil;

import java.net.URLEncoder;
import java.util.Map;

import io.rong.imkit.RongContext;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vigorous on 17/12/19.
 * 融云聊天
 */

public class ChatActivity extends BaseActivity {
    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_chat);
    }

    @Override
    public void initUI() {

        boolean isDebug = true;
        Conversation.ConversationType[] mConversationsTypes = null;


        ConversationListFragment listFragment = new ConversationListFragment();
        listFragment.setAdapter(new ConversationListAdapterEx(RongContext.getInstance()));
        Uri uri;
        if (isDebug) {
            uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "true") //设置私聊会话是否聚合显示
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//群组
                    .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                    .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                    .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "true")
                    .build();
            mConversationsTypes = new Conversation.ConversationType[]{Conversation.ConversationType.PRIVATE,
                    Conversation.ConversationType.GROUP,
                    Conversation.ConversationType.PUBLIC_SERVICE,
                    Conversation.ConversationType.APP_PUBLIC_SERVICE,
                    Conversation.ConversationType.SYSTEM,
                    Conversation.ConversationType.DISCUSSION
            };

        } else {
            uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
                    .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                    .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                    .build();
            mConversationsTypes = new Conversation.ConversationType[]{Conversation.ConversationType.PRIVATE,
                    Conversation.ConversationType.GROUP,
                    Conversation.ConversationType.PUBLIC_SERVICE,
                    Conversation.ConversationType.APP_PUBLIC_SERVICE,
                    Conversation.ConversationType.SYSTEM
            };
        }
        listFragment.setUri(uri);





        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ll_chat_fragment_container, listFragment).commitAllowingStateLoss();

        connectServer();
    }

    private void connectServer(){

        Map<String, String> headers = RongYunTokenUtil.getHeaderMap();

        String uid = null;
        String uname = null;
        String logo = null;
        try{
//           uid = URLEncoder.encode("18600211553", "UTF-8");
//           uname = URLEncoder.encode("vigorous", "UTF-8");
//           logo = URLEncoder.encode("https://www.baidu.com/img/bd_logo1.png", "UTF-8");

            uid = "18600211553";
            uname = "vigorous";
            logo = "http://www.baidu.com/logo.png";
        }catch (Exception e){
            e.printStackTrace();
        }

        ApiClient.getInstance().getRongYunService().getToken(uid, uname, logo,headers).enqueue(new Callback<RtokenRsp>() {
            @Override
            public void onResponse(Call<RtokenRsp> call, Response<RtokenRsp> response) {
                if(response != null){
                    if(response.body() != null){
                        Log.d("resp_json", response.body().getToken());

                    }else{
                        Log.d("resp_json", "错误");
                    }
                }
            }

            @Override
            public void onFailure(Call<RtokenRsp> call, Throwable t) {
                Log.d("resp_json", t.getMessage());
            }
        });
    }
}
