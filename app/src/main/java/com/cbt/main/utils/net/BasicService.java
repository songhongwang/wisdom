package com.cbt.main.utils.net;

import com.cbt.main.model.BaseModel;
import com.cbt.main.model.RtokenRsp;
import com.cbt.main.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by vigorous on 16/11/4.
 * 基础服务接口
 */

public interface BasicService {

//    String mVersion = "api.php?";

    @GET("login")
    Call<User> login(@Query("telphone") String telphone, @Query("password") String password);

    @GET("findcheckcode")
    Call<BaseModel<Object>> getCode(@Query("telphone") String telphone);

    @GET("adduser")
    Call<BaseModel<Object>> regist(@Query("telphone") String telphone, @Query("password") String password, @Query("randoms") String code);

    @GET("Forgotpassword")
    Call<BaseModel<Object>> forgotPwd(@Query("telphone") String telphone, @Query("password") String password, @Query("randoms") String code);

}
