package com.cbt.main.utils.net;

import android.util.Log;

import com.cbt.main.model.BaseModel;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by vigorous on 16/11/28.
 * 数据层通用回调
 */

public abstract class CommonCallBack<T> extends BaseCallBack<T> {
    private int index = 0;

    @Override
    public void onResponse(Call<BaseModel<T>> call, Response<BaseModel<T>> response) {
        if (null != response) {
            BaseModel body = response.body();
            if (body != null) {
                if (body.getCode() == 0) {
                    onCResponse(call, body);
                } else {
                    onErrorMessage(body.getMessage());
                }
            } else {
                onCFailure(call, new NullPointerException());
            }
        }
    }

    @Override
    public void onFailure(Call<BaseModel<T>> call, Throwable t) {
        Log.d("CommonCallBack fail", " ==== " + t.getMessage());

    }


    // 网络正常，只是返回的消息内容异常，如果客户端需要处理，请重写本方法
    public void onErrorMessage(String errorMessage) {
    }

    public abstract void onCResponse(Call<BaseModel<T>> call, BaseModel<T> response);

    public abstract void onCFailure(Call<BaseModel<T>> call, Throwable t);

}
