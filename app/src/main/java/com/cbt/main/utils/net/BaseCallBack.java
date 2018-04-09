package com.cbt.main.utils.net;


import com.cbt.main.model.BaseModel;

import retrofit2.Callback;

/**
 * Created by vigorous on 16/11/4.
 * <p>
 * 依赖工程中不做实现，异常的处理都在主工程中处理
 */

public abstract class BaseCallBack<T> implements Callback<BaseModel<T>> {
    /**
     * do nothing
     */
}
