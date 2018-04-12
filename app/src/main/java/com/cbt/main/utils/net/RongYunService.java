package com.cbt.main.utils.net;

import com.cbt.main.model.BaseModel;
import com.cbt.main.model.RtokenRsp;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by vigorous on 16/11/4.
 */

public interface RongYunService {
    String mVersion = "";

    @FormUrlEncoded
    @POST("user/getToken.json")
    Call<RtokenRsp> getToken(@Field("userId") String userId, @Field("name") String name, @Field("portraitUri") String portraitUri, @HeaderMap Map<String,String> headers);



}
