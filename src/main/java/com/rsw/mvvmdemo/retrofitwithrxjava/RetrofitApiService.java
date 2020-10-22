package com.rsw.mvvmdemo.retrofitwithrxjava;


import com.google.gson.JsonObject;
import com.rsw.mvvmdemo.bean.BannerBean;
import com.rsw.mvvmdemo.bean.BaseBean;
import com.rsw.mvvmdemo.bean.CpClassBean;
import com.rsw.mvvmdemo.bean.CpTwoClassBean;
import com.rsw.mvvmdemo.bean.HotBean;
import com.rsw.mvvmdemo.bean.ImageUploadRes;
import com.rsw.mvvmdemo.bean.ShequBean;
import com.rsw.mvvmdemo.bean.SplashBean;
import com.rsw.mvvmdemo.bean.User;
import com.rsw.mvvmdemo.bean.UserInfo;
import com.rsw.mvvmdemo.bean.YinshiBean;
import com.rsw.mvvmdemo.bean.basebean.HomeFatherBean;
import com.rsw.mvvmdemo.bean.basebean.ResponModel;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by leo
 * on 2019/8/14.
 * Retrofit 接口请求配置都在这
 */
public interface RetrofitApiService {
    @POST("login")
    Observable<BaseBean> login(@Query("loginName") String loginName, @Query("pwd") String pwd);

    @POST("register")
    Observable<BaseBean> register(@Query("loginName") String loginName, @Query("pwd") String pwd);

    @POST("adduser")
    Observable<BaseBean> adduser(@Query("loginName") String loginName, @Query("pwd") String pwd);

    @GET("getYunfuyinshiList")
    Observable<ResponModel<List<YinshiBean.ListBean>>> getYunfuyinshiList(@Query("page") int page, @Query("size") int size, @Query("class_id") int class_id);

    @GET("getYunfuyinshiListBykeyWord")
    Observable<ResponModel<List<YinshiBean.ListBean>>> getYunfuyinshiListBykeyWord(@Query("page") int page, @Query("size") int size, @Query("keyword") String keyword);

    @GET("getBanners")
    Observable<ResponModel<List<YinshiBean.ListBean>>> getBanners(@Query("size") int size);

    @GET("getCaipuList")
    Observable<YinshiBean> getCaipuList(@Query("keyWords") String keyWords, @Query("page") int page, @Query("size") int size);

    @GET("getCaipuClass")
    Observable<CpClassBean> getCaipuClass(@Query("type") int type);

    @GET("getCaipuTwoClass")
    Observable<CpTwoClassBean> getCaipuTwoClass(@Query("classid") int classid);

    @GET("getVersion")
    Observable<JSONObject> getVersion();

    @GET("getHotKeys")
    Observable<HotBean> getHotKeys();

    @GET("getCaipuByClass")
    Observable<YinshiBean> getCaipuByClass(@Query("classid") int classid, @Query("two_classid") int two_classid, @Query("page") int page, @Query("size") int size);

    //    @Multipart
    //    @POST("headImgUpload")
    //    Observable<ImageUploadRes> headImgUpload(@Part("") RequestBody file, @Query("path") String path, @Query("user_id") String user_id);

    @Multipart
    @POST("headImgUpload")
    Observable<ImageUploadRes> headImgUpload(@Part("file\"; filename=\"img.jpg\"") RequestBody img, @Query("path") String path, @Query("user_id") String user_id, @Query("type") int type);


    @GET("getUserInfo")
    Observable<UserInfo> getUserInfo(@Query("user_id") String user_id);

    @POST("saveCollection")
    Observable<JsonObject> saveCollection(@Query("user_id") String user_id, @Query("collect_id") String collect_id);

    @GET("getCollection")
    Observable<YinshiBean> getCollection(@Query("user_id") String user_id, @Query("page") int page, @Query("size") int size);

    @POST("deleteCollection")
    Observable<JsonObject> deleteCollection(@Query("user_id") String user_id, @Query("collect_id") String collect_id);

    @GET("getCollectionState")
    Observable<JsonObject> getCollectionState(@Query("user_id") String user_id, @Query("collect_id") String collect_id);

    @POST("updateNick")
    Observable<JsonObject> updateNick(@Query("user_id") String user_id, @Query("nickName") String nickName);

    @Multipart
    @POST("addShihua")
    Observable<JsonObject> addShihua(@Part("file\"; filename=\"img.jpg\"") RequestBody img, @Query("fileName") String fileName,
                                     @Query("path") String path, @Query("user_id") String user_id, @Query("content") String content);

    @POST("addFeedBack")
    Observable<JsonObject> addFeedBack(@Query("user_id") String user_id, @Query("content") String content, @Query("phone") String phone, @Query("email") String email);


    @GET("getSplashImage")
    Observable<SplashBean> getSplashImage();


    @GET("getShihua")
    Observable<ShequBean> getShihua(@Query("page") int page, @Query("size") int size);

    @GET("getShihuaByuserId")
    Observable<ShequBean> getShihuaByuserId(@Query("user_id") String user_id, @Query("page") int page, @Query("size") int size);


    //wanAndroid的，轮播banner的接口
    @GET("banner/json")
    Observable<ResponModel<List<BannerBean>>> getBanner();

    //首页文章,curPage拼接。从0开始
    @GET("article/list/{curPage}/json")
    Observable<ResponModel<HomeFatherBean>> getHomeArticles(@Path("curPage") int curPage);

    //收藏文章列表
    @GET("lg/collect/list/{curPage}/json")
    Observable<ResponModel<HomeFatherBean>> getCollectArticles(@Path("curPage") int curPage);

    //收藏站内文章
    @POST("lg/collect/{id}/json")
    Observable<ResponModel<String>> collectArticle(@Path("id") int id);

    //收藏站外文章
    @FormUrlEncoded
    @POST("lg/collect/add/json")
    Observable<ResponModel<String>> collectOutArticle(@Field("title") String title, @Field("author") String author, @Field("link") String link);

    //取消收藏 -- 首页文章列表
    @POST("lg/uncollect_originId/{id}/json")
    Observable<ResponModel<String>> unCollectByHome(@Path("id") int id);

    //取消收藏 -- 我的收藏列表
    @FormUrlEncoded
    @POST("lg/uncollect/{id}/json")
    Observable<ResponModel<String>> unCollectByMe(@Path("id") int id, @Field("originId") int originId);


    //退出登录
    @GET("user/logout/json")
    Observable<ResponModel<String>> loginOut();

    @POST("user/login")
    @FormUrlEncoded
    Observable<ResponModel<User>> login(@FieldMap HashMap<String, Object> map);

    //Retrofit get请求
    @GET("xiandu/category/wow")
    Observable<String> getGank(@Query("en_name") String en_name);


    //Retrofit post请求
    @POST("add2gank")
    @FormUrlEncoded
    Observable<ResponseBody> postAddGank(@FieldMap HashMap<String, String> map);

    //单张图片上传
    @POST("upload/pic")
    @Multipart
    Observable<ResponModel<String>> uploadPic(@Part("type") RequestBody type, @Part MultipartBody.Part file);


    //单张图片上传
    @POST("upload/picss")
    @Multipart
    Observable<ResponModel<String>> uploadPicss(@Part("type") RequestBody type, @PartMap Map<String, RequestBody> maps);

    //Retrofit下载文件
    @GET
    @Streaming
    //10以上用@streaming。不会造成oom，反正你用就是了
    Observable<ResponseBody> downloadFile(@Url String url);

    @GET
    @Streaming
    Observable<ResponseBody> downloadFile(@Url String url, @Header("RANGE") String range);

}
