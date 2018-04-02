package com.cbt.main.utils.net;

import com.cbt.main.model.BaseModel;
import com.cbt.main.model.IndexFeedModel;
import com.cbt.main.model.IndexModel;
import com.cbt.main.model.IndexProductModel;
import com.cbt.main.model.User;
import com.cbt.main.model.Weather7DaysForcast;

import java.util.List;

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

    @GET("Forgotcheckcode")
    Call<BaseModel<Object>> forgotPwd(@Query("telphone") String telphone);

    @GET("findIndexData")
    Call<IndexModel> getIndex(@Query("provincename") String provincename, @Query("cityname") String cityname, @Query("countryname") String countryname);

    @GET("find7Day")
    Call<Weather7DaysForcast> getWeatherForcast(@Query("provincename") String provincename, @Query("cityname") String cityname, @Query("countryname") String countryname);

//    @GET("findfarminteract")
//    Call<Object> getMineFarmerTab1(@Query("page") int page);

//    @GET("findmyfarmfarming")
//    Call<Object> getMineFarmerTab2(@Query("page") int page);

    @GET("findIndexProduct")
    Call<List<IndexProductModel>> getIndexProduct(@Query("hello") int hello);

    // 朋友圈
    @GET("findfarminteract")
    Call<List<IndexFeedModel>> getIndexFeed(@Query("page") int page);

    // 朋友圈 回复
    @GET("replymyfarminteract")
    Call<Object> replyFeed(@Query("iid") String iid,@Query("commentid") String commentId,@Query("content") String content );


    @GET("changefarm")
    Call<Weather7DaysForcast> perfactAccount(
            @Query("fname") String fname,
            @Query("points") String points,
            @Query("zuowulist") String zuowulist,
            @Query("provincename") String provincename,
            @Query("cityname") String cityname,
            @Query("countryname") String countryname);

}
