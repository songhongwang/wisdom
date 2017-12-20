package com.cbt.main.utils.net;

import com.cbt.main.model.BaseModel;
import com.cbt.main.model.RtokenRsp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by vigorous on 16/11/4.
 */

public interface BasicService {

    String mVersion = "api.php?";

    @GET("http://api.cn.ronghub.com/user/getToken.json")
    Call<BaseModel<RtokenRsp>> search(@Query("key") String key);

}
