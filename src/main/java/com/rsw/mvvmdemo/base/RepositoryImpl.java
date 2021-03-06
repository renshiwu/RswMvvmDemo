package com.rsw.mvvmdemo.base;

import com.rsw.mvvmdemo.bean.BannerBean;
import com.rsw.mvvmdemo.bean.User;
import com.rsw.mvvmdemo.bean.YinshiBean;
import com.rsw.mvvmdemo.bean.basebean.HomeFatherBean;
import com.rsw.mvvmdemo.bean.basebean.ParamsBuilder;
import com.rsw.mvvmdemo.bean.basebean.Resource;
import com.rsw.mvvmdemo.common.Constant;
import com.rsw.mvvmdemo.common.PARAMS;
import com.rsw.mvvmdemo.retrofitwithrxjava.uploadutils.UploadFileRequestBody;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by RSW
 * on 2020/10/12
 * 这是所有的网络请求所在的位置
 */
public class RepositoryImpl extends BaseModel {

    public MutableLiveData<Resource<List<YinshiBean.ListBean>>> getYunfuyinshiList(int page, int size, int class_id, ParamsBuilder paramsBuilder) {
        MutableLiveData<Resource<List<YinshiBean.ListBean>>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().getYunfuyinshiList(page, size, class_id), liveData, paramsBuilder);
    }


    public MutableLiveData<Resource<List<YinshiBean.ListBean>>> getYunfuyinshiListBykeyWord(int page, int size, String keyword, ParamsBuilder paramsBuilder) {
        MutableLiveData<Resource<List<YinshiBean.ListBean>>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().getYunfuyinshiListBykeyWord(page, size, keyword), liveData, paramsBuilder);
    }

    public MutableLiveData<Resource<List<YinshiBean.ListBean>>> getBanners( int size, ParamsBuilder paramsBuilder) {
        MutableLiveData<Resource<List<YinshiBean.ListBean>>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().getBanners(size), liveData, paramsBuilder);
    }






    //获取 banner列表
    public MutableLiveData<Resource<List<BannerBean>>> getBannerList() {
        MutableLiveData<Resource<List<BannerBean>>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().getBanner(), liveData);
    }

    //获取首页文章
    public MutableLiveData<Resource<HomeFatherBean>> getHomeArticles(int curPage, ParamsBuilder paramsBuilder) {

        MutableLiveData<Resource<HomeFatherBean>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().getHomeArticles(curPage), liveData, paramsBuilder);
    }

    //获取收藏列表
    public MutableLiveData<Resource<HomeFatherBean>> getCollectArticles(int curPage, ParamsBuilder paramsBuilder) {
        MutableLiveData<Resource<HomeFatherBean>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().getCollectArticles(curPage), liveData, paramsBuilder);
    }

    //站内收藏文章
    public MutableLiveData<Resource<String>> collectArticle(int id) {
        MutableLiveData<Resource<String>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().collectArticle(id), liveData, ParamsBuilder.build().isShowDialog(false));//不显示加载logo
    }

    //站外收藏文章
    public MutableLiveData<Resource<String>> collectOutArticle(String title, String author, String link) {
        MutableLiveData<Resource<String>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().collectOutArticle(title, author, link), liveData, ParamsBuilder.build().isShowDialog(false));
    }

    //取消收藏 -- 首页列表
    public MutableLiveData<Resource<String>> unCollectByHome(int id) {
        MutableLiveData<Resource<String>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().unCollectByHome(id), liveData, ParamsBuilder.build().isShowDialog(false));
    }


    public MutableLiveData<Resource<String>> unCollectByMe(int id, int originId) {
        MutableLiveData<Resource<String>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().unCollectByMe(id, originId), liveData, null);
    }


    //退出登录
    public MutableLiveData<Resource<String>> LoginOut() {
        MutableLiveData<Resource<String>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().loginOut(), liveData);
    }


    //登录
    public MutableLiveData<Resource<User>> login(HashMap<String, Object> map, ParamsBuilder paramsBuilder) {
        MutableLiveData<Resource<User>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().login(map), liveData, paramsBuilder);
    }


    //正常下载，
    public MutableLiveData<Resource<File>> downFile(String destDir, String fileName) {
        MutableLiveData<Resource<File>> liveData = new MutableLiveData<>();
        return downLoadFile(getApiService().downloadFile(Constant.QQ_APK), liveData, destDir, fileName);
    }

    //断点下载
    public MutableLiveData<Resource<File>> downFile(String destDir, String fileName, long currentLength) {
        String range = "bytes=" + currentLength + "-";
        MutableLiveData<Resource<File>> liveData = new MutableLiveData<>();
        return downLoadFile(getApiService().downloadFile(Constant.QQ_APK, range), liveData, destDir, fileName, currentLength);
    }

    //上传文件(进度监听)
    public MutableLiveData<Resource<String>> upLoadPic(String type, String key, File file) {
        MutableLiveData<Resource<String>> liveData = new MutableLiveData<>();

        UploadFileRequestBody uploadFileRequestBody = new UploadFileRequestBody(file, liveData);
        //"file"  是key
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), uploadFileRequestBody);
        return upLoadFile(getApiService().uploadPic(PARAMS.changeToRquestBody(type), body), liveData);
    }


    //上传多张图片(进度监听)  多张图片进度监听，图片一张一张上传 所以用到了PictureProgressUtil工具类。用之前init初始数据，setProgress即可
    public MutableLiveData<Resource<String>> upLoadPicss(String type, HashMap<String, File> files) {
        MutableLiveData<Resource<String>> liveData = new MutableLiveData<>();

        Map<String, RequestBody> bodyMap = new HashMap<>();
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            UploadFileRequestBody uploadFileRequestBody = new UploadFileRequestBody(file, liveData);
            bodyMap.put("files" + "\"; filename=\"" + file.getName(), uploadFileRequestBody);
        }

        //如果是传统的不带进度监听 只需要
//        bodyMap=PARAMS.manyFileToPartBody(files);
        return upLoadFile(getApiService().uploadPicss(PARAMS.changeToRquestBody(type), bodyMap), liveData);
    }


}
