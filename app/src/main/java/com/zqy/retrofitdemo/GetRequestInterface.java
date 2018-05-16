package com.zqy.retrofitdemo;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by zhaoqy on 2018/5/14.
 */

public interface GetRequestInterface {

    /**
     * 注解里传入 网络请求 的部分URL地址
     * getCall()是接受网络请求数据的方法
     */
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Call<GetTranslation> getCall();
}
