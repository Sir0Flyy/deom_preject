package com.example.mvp_1.mvp.model;

import android.text.TextUtils;

import com.example.mvp_1.app.App;
import com.example.mvp_1.callback.IDataCallBack;
import com.example.mvp_1.callback.ProgressDataCallback;
import com.example.mvp_1.di.component.DaggerRxOperateComponent;
import com.example.mvp_1.mvp.model.api.ApiService;
import com.example.mvp_1.util.LogUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * RxJava和OkHttp关联的封装类
 */
public class RxOperateImpl {
    @Inject
    ApiService mApiService;

    public RxOperateImpl() {
        //将apiService注入到RxOperateImpl里面
        DaggerRxOperateComponent.builder().
                appComponent(App.daggerAppComponent()).build().
                inject(this);
    }

    /**
     * @param url          get请求的URL地址
     * @param dataCallBack 结果回调(实际上是接口回调)
     * @param <T>          接口回调获取的值
     */
    public <T> void requestData(String url, IDataCallBack<T> dataCallBack) {
        RxSchedulersOperator.retryWhenOperator
                (mApiService.requestData(url)).
                subscribe(getObserver(dataCallBack));
    }


    /**
     * @param url          get请求的url地址
     * @param params       get请求的参数
     * @param dataCallBack get请求的结果回调
     * @param <T>
     */
    public <T> void requestData(String url, Map<String, T> params, IDataCallBack<T> dataCallBack) {
        if (params == null || params.size() == 0)
            requestData(url, dataCallBack);
        else
            RxSchedulersOperator.retryWhenOperator
                    (mApiService.requestData(url, params)).
                    subscribe(getObserver(dataCallBack));
    }

    /**
     * 没有参数的post请求
     *
     * @param url
     * @param dataCallBack
     * @param <T>
     */
    public <T> void requestFormData(String url, IDataCallBack<T> dataCallBack) {
        RxSchedulersOperator.retryWhenOperator(mApiService.requestFormData(url)).
                subscribe(getObserver(dataCallBack));

    }
    /**
     * @param url                  下载的文件路径
     * @param progressDataCallback 下载结果回调
     * @param isProcess            是否带进度
     * @param <T>
     */
    public <T> void downloadFile(String url, boolean isProcess,
                                 ProgressDataCallback<T> progressDataCallback) {
        if (isProcess) {
            //TODO 需封装带进度的RxJava+Retrofit代码  作业

        } else
            RxSchedulersOperator.retryWhenOperator(mApiService.downloadFile(url)).
                    subscribe(getObserver(progressDataCallback));
    }

    /**
     * 有参数的post请求
     *
     * @param url
     * @param params
     * @param dataCallBack
     * @param <T>
     */
    public <T> void requestFormData(String url, Map<String, T> params, IDataCallBack<T> dataCallBack) {
        if (params == null || params.size() == 0)
            requestFormData(url, dataCallBack);
        else
            RxSchedulersOperator.retryWhenOperator(mApiService.requestFormData(url, params)).
                    subscribe(getObserver(dataCallBack));
    }


    /**
     * 有请求头并且有参数的post请求
     *
     * @param url
     * @param headers
     * @param params
     * @param dataCallBack
     * @param <T>
     */
    public <T> void requestFormData(String url, Map<String, T> headers, Map<String, T> params, IDataCallBack<T> dataCallBack) {
        if (headers == null || headers.size() == 0)  //请求头为空 但是参数不为空
            requestFormData(url, params, dataCallBack);
        else if ((headers == null || headers.size() == 0) && // 请求头和参数都为空
                (params == null || params.size() == 0)) {
            requestFormData(url, dataCallBack);
        } else
            //请求头和参数都不为空
            RxSchedulersOperator.retryWhenOperator(mApiService.requestFormData(url, headers, params)).
                    subscribe(getObserver(dataCallBack));
    }


    /** 带JSON串的没有请求头的post请求
     *
     * @param url
     * @param jsonStr
     * @param dataCallBack
     * @param <T>
     */
    public <T> void requestFormData(String url,String jsonStr, IDataCallBack<T> dataCallBack) {
        if (jsonStr.isEmpty()) {
            LogUtils.e("上传的字符串不能为空");
            return;
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);
        RxSchedulersOperator.retryWhenOperator(mApiService.requestFormData(url,requestBody)).
                subscribe(getObserver(dataCallBack));

    }

    /**
     *  带请求头的上传json串的post请求
     * @param url
     * @param haders
     * @param jsonStr
     * @param dataCallBack
     * @param <T>
     */
    public <T> void requestFormData(String url, Map<String,T> haders,String jsonStr,IDataCallBack<T> dataCallBack){
        if (haders == null || jsonStr == null)
            requestFormData(url,jsonStr,dataCallBack);
        else {
            if (jsonStr.isEmpty())
                return;
            // 有请求头并且上传json串的post请求
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);
            RxSchedulersOperator
                    .retryWhenOperator(mApiService.requestFormData(url, haders, requestBody))
                    .subscribe(getObserver(dataCallBack));
        }

    }

    /**
     * 单个文件上传
     * @param url
     * @param file
     * @param dataCallBack
     * @param <T>
     */
    private <T> void uploadSingleFile(String url,File file,IDataCallBack<T> dataCallBack) {
        //  注意1：6.0之后动态权限  file
        RequestBody requestBoy = RequestBody.create(MediaType.parse("multipart/form-data"),file) ;
        //注意2：第一个参数 ： 有些情况下 前端要和后台的key名一致
        MultipartBody.Part part = MultipartBody.Part.createFormData("aFile",file.getName(),requestBoy);
        RxSchedulersOperator.retryWhenOperator(mApiService.uploadSingleFile(url,part))
                .subscribe(getObserver(dataCallBack));
    }

    /**
     * 单个文件+ 参数上传  一般用于发朋友圈 图片+文字
     * @param url
     * @param file
     * @param str
     * @param dataCallBack
     * @param <T>
     */
    private <T> void uploadStrFile(String url,File file,String str,IDataCallBack<T> dataCallBack) {
        // 注意：6.0之后动态权限  file
        if (TextUtils.isEmpty(str))
            uploadSingleFile(url, file,dataCallBack);
        else {
            RequestBody requestBoy = RequestBody.create(MediaType.parse("multipart/form-data"),file) ;
            MultipartBody.Part part = MultipartBody.Part.createFormData("aFile",file.getName(),requestBoy);
            RequestBody strBody = RequestBody.create(MediaType.parse("multipart/form-data"),str);
            RxSchedulersOperator.retryWhenOperator(mApiService.uploadStrFile(url,strBody,part))
                    .subscribe(getObserver(dataCallBack));
        }
    }

    //多文件上传的方法 1
    public <T> void uploadMultipleFiles(IDataCallBack<T> dataCallBack,String url,File... files){
        MultipartBody.Part[] parts = new MultipartBody.Part[files.length];
        if (files != null && files.length > 0){
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                RequestBody requestBoy = RequestBody.create(MediaType.parse("multipart/form-data"),file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("file",file.getName(),requestBoy);
                parts[i] =part;
            }
        }
        RxSchedulersOperator.retryWhenOperator(mApiService.uploadMultipleFiles(url,parts))
                .subscribe(getObserver(dataCallBack));
    }

    //多个文件上传的方法 2
    public <T> void  uploadMultipleFiles(String url, IDataCallBack<T> dataCallBack, File... files){
         if (files != null && files.length>0) {
             Map<String, RequestBody> fileMap = new HashMap<String, RequestBody>();
             for (File file : files) {
                 RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                 fileMap.put("file;filename=" + file.getName(), body);
             }
             RxSchedulersOperator.retryWhenOperator(mApiService.uploadMultipleFiles(url,fileMap))
                     .subscribe(getObserver(dataCallBack));
         }
    }

    /**
     *  多文件 + 多个键值对上传
     * @param url
     * @param params
     * @param dataCallBack
     * @param files
     * @param <T>
     */
    public <T> void uploadStrFiles(String url, Map<String,String> params,IDataCallBack<T> dataCallBack,File... files){
        if (files != null && files.length>0){
            if (params == null || params.size() == 0){
                // 不带参数的多文件上传
                uploadMultipleFiles(url,dataCallBack,files);
            }else {
                FormBody.Builder builder=new FormBody.Builder();
                for (int i = 0; i < params.size(); i++) {
                    Set<String> keys = params.keySet();
                    for (String key : keys) {
                        String value = params.get(key);
                        //上传多个键值对
                        builder.add(key,value);
                    }
                }
                Map<String,RequestBody> filesMap = new HashMap<String,RequestBody>();
                for (File file : files) {
                    RequestBody requestBody= RequestBody.create(MediaType.parse("multipart/form-data"),file);
                    //上传多个文件
                    filesMap.put("file;fileName="+file.getName(),requestBody);
                }
                RxSchedulersOperator.retryWhenOperator(mApiService.uploadStrFiles(url,builder.build(),filesMap))
                        .subscribe(getObserver(dataCallBack));
            }

        }
    }
        /**
         * 抽取出结果回调的方法
         *
         * @param dataCallBack
         * @param <T>          父类： RxObserver<T>     子类：RxObserver<?>
         * @return
         */
        private <T> RxObserver<? extends T> getObserver(IDataCallBack<T> dataCallBack) {
            return new RxObserver<T>(dataCallBack) {
                @Override
                public void onSubscribe(Disposable d) {
                    if (dataCallBack != null)
                        dataCallBack.onResponseDisposable(d);

                }

                @Override
                public void onNext(T t) {
                    if (dataCallBack != null)
                        dataCallBack.onStateSucess(t);
                }
            };
        }

}
