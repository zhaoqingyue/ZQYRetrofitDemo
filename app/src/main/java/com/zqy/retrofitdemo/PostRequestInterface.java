package com.zqy.retrofitdemo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhaoqy on 2018/5/14.
 */

public interface PostRequestInterface {

    /**
     * 采用@Post表示Post方法进行请求（传入部分url地址）
     * 采用@FormUrlEncoded注解的原因:API规定采用请求格式x-www-form-urlencoded,即表单形式
     * 需要配合@Field使用
     */
    @POST("translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen=&ssid=&network=&abtest=")
    @FormUrlEncoded
    Call<PostTranslation> getCall(@Field("i") String targetSentence);

}