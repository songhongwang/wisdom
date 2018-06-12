package com.cbt.main.activity;

import android.content.Intent;
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
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vigorous on 17/12/19.
 * 融云聊天
 */

public class ConversationListActivity extends BaseActivity {
    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_chat);
    }

    @Override
    public void initUI() {


//        connectServer();
    }

    private void connectServer(){

        Map<String, String> headers = RongYunTokenUtil.getHeaderMap();

        String uid = null;
        String uname = null;
        String logo = null;
        try{
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
                        connect(response.body().getToken());
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

    /**
     * <p>连接服务器，在整个应用程序全局，只需要调用一次，需在 {@link #} 之后调用。</p>
     * <p>如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。</p>
     *
     * @param token    从服务端获取的用户身份令牌（Token）。
     * @return RongIM  客户端核心类的实例。
     */
    private void connect(String token) {

        RongIM.connect(token, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {

            }

            /**
             * 连接融云成功
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(String userid) {
                Log.d("LoginActivity", "--onSuccess" + userid);
//                startActivity(new Intent(ChatActivity.this, MainActivity.class));
//                finish();
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }
}
