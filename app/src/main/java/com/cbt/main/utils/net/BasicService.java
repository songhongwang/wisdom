package com.cbt.main.utils.net;

import com.cbt.main.model.AgriculturalModel;
import com.cbt.main.model.BaseModel;
import com.cbt.main.model.ClientFarm;
import com.cbt.main.model.Data;
import com.cbt.main.model.Dictionaries;
import com.cbt.main.model.Friend;
import com.cbt.main.model.IndexFeedModel;
import com.cbt.main.model.IndexModel;
import com.cbt.main.model.IndexProductModel;
import com.cbt.main.model.MarketinformationDetailView;
import com.cbt.main.model.MarketinformationView;
import com.cbt.main.model.MyproblemView;
import com.cbt.main.model.UpdateModel;
import com.cbt.main.model.User;
import com.cbt.main.model.Weather7DaysForcast;
import com.cbt.main.model.WentiModel;
import com.cbt.main.model.ZaiqingModel;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @GET("Forgotcheckcode")
    Call<BaseModel<Object>> wjgetCode(@Query("telphone") String telphone);

    @GET("Changepassword")
    Call<BaseModel<Object>> cgpassword(@Query("password") String password);

    @GET("Forgotpassword")
    Call<BaseModel<Object>> wjMmtijiao(@Query("password") String password);

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

    @GET("findType")
    Call<List<Dictionaries>> findType(@Query("upid") String upid, @Query("type") int type);
//    @GET("findfarminteract")
//    Call<Object> getMineFarmerTab1(@Query("page") int page);

//    @GET("findmyfarmfarming")
//    Call<Object> getMineFarmerTab2(@Query("page") int page);

    @GET("findIndexProduct")
    Call<List<IndexProductModel>> getIndexProduct(@Query("hello") int hello);

    // 朋友圈
    @GET("findfarminteract")
    Call<List<IndexFeedModel>> getIndexFeed(@Query("page") int page);

    @GET("findZaiqingForFm")
    Call<List<ZaiqingModel>> getZaiqingForFm(@Query("page") int page);

    @GET("findMySendZaiqing")
    Call<List<ZaiqingModel>> myZaiqingForFm(@Query("page") int page);

    @GET("findMyscZaiqing")
    Call<List<ZaiqingModel>> scZaiqingForFm(@Query("page") int page);

    @GET("findmyfarmfarming")
    Call<List<AgriculturalModel>> getMyfarmfarming(@Query("page") int page);

    @GET("moremypublishAgricultural")
    Call<List<AgriculturalModel>> myMyfarmfarming(@Query("page") int page);

    @GET("moremycollectfarming")
    Call<List<AgriculturalModel>> scMyfarmfarming(@Query("page") int page);

    @GET("findexperthot")
    Call<List<WentiModel>> getExperthot(@Query("page") int page);

    @GET("myproblem")
    Call<MyproblemView> getmyproblem(@Query("page") int page, @Query("iid") String iid);

    @GET("shichangxiangqing")
    Call<MarketinformationDetailView> getshichangxiangqing(@Query("page") int page, @Query("iid") String iid);

    @GET("findexpertnew")
    Call<List<WentiModel>> getExpertnew(@Query("page") int page);

    @GET("findexpertmine")
    Call<List<WentiModel>> getExpertmine(@Query("page") int page);

    @GET("findMyscexpertmine")
    Call<List<WentiModel>> scExpertmine(@Query("page") int page);

    @GET("findmarketnew")
    Call<List<MarketinformationView>> getmarketnew(@Query("page") int page);

    @GET("findmarketmypublish")
    Call<List<MarketinformationView>> getmarketmypublish(@Query("page") int page);

    @GET("findMyscmarket")
    Call<List<MarketinformationView>> scmarketmypublish(@Query("page") int page);


    @GET("farmlandfarm")
    Call<ClientFarm> farmlandfarm();

    @GET("adduserdetail")
    Call<User> adduserdetail(@Query("provincename") String provincename,
                             @Query("cityname") String cityname,
                             @Query("countryname") String countryname,
                             @Query("sex") String leixingid,
                             @Query("birthday") String zuowuid,
                             @Query("disname") String zaizhongid,
                             @Body RequestBody imgs);
    
// 朋友圈 回复
    @GET("replymyfarmfarming")
    Call<Object> replyFeed(@Query("iid") String iid,@Query("commentid") String commentId,@Query("content") String content );

    @GET("huidaquestion")
    Call<Object> replyanswer(@Query("iid") String iid,@Query("content") String content );

    @GET("shichanghuifu")
    Call<Object> replyShichang(@Query("iid") String iid,@Query("commentid") String commentId,@Query("content") String content );

    @GET("zaiqingReplay")
    Call<Object> replyZaiqing(@Query("iid") String iid,@Query("commentid") String commentId,@Query("content") String content );
   


    @GET("farmlandfarmUpdate")
    Call<Object> perfactAccount(
            @Query("fname") String fname,
            @Query("points") String points,
            @Query("zuowulist") String zuowulist,
            @Query("provincename") String provincename,
            @Query("cityname") String cityname,
            @Query("countryname") String countryname,
            @Query("mianji") String mianji);

//    provincename(省名称）
//            cityname(市名称）
//            countryname(县名称）
//            content(内容）
//            img1-img10(图片1-10）

    @POST("askexpert")
    Call<Object> uploadFarmZixunState(@Query("provincename") String provincename,
                                 @Query("cityname") String cityname,
                                 @Query("countryname") String countryname,
                                      @Query("ctid") String leixingid,
                                      @Query("cid") String zuowuid,
                                 @Query("qtid") String zaizhongid,
                                 @Query("content") String content,
                                 @Body RequestBody imgs );

    @POST("publishmarketnews")
    Call<Object> uploadFarmGongqState(@Query("provincename") String provincename,
                                      @Query("cityname") String cityname,
                                      @Query("countryname") String countryname,
                                      @Query("ctid") String leixingid,
                                      @Query("cid") String zuowuid,
                                      @Query("nid") String zaizhongid,
                                      @Query("type") String gongqiuid,
                                      @Query("ititle") String ititle,
                                      @Query("content") String content,
                                      @Body RequestBody imgs );

    @POST("fabuZaiqing")
    Call<Object> uploadFarmState(@Query("provincename") String provincename,
                                 @Query("cityname") String cityname,
                                 @Query("countryname") String countryname,
                                 @Query("leixingid") String leixingid,
                                 @Query("zuowuid") String zuowuid,
                                 @Query("zaizhongid") String zaizhongid,
                                 @Query("content") String content,
                                 @Body RequestBody imgs );

    @POST("publishmyfarmfarming")
    Call<Object> uploadFarmNongState(@Query("provincename") String provincename,
                                 @Query("cityname") String cityname,
                                 @Query("countryname") String countryname,
                                 @Query("ctid") String leixingid,
                                 @Query("cid") String zuowuid,
                                 @Query("content") String content,
                                 @Body RequestBody imgs );

    // 关注列表
    @GET("guanzhuyonghulist")
    Call<List<Friend>> getAttentionList(@Query("page") int page);

    // 获取用户
    @GET("findOtherUser")
    Call<User> getUser(@Query("otheruserid") String otheruserid);

    // 检查更新版本
    @GET("checkUpdate")
    Call<UpdateModel> checkUpdate();
}
