package com.gxun.mynews.util;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.gxun.mynews.entity.History;
import com.gxun.mynews.entity.UserInfo;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
    public static Object goodsWithOkhttp;

    public static void loginWithOkHttp(String address, UserInfo u, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("u",JSON.toJSONString(u))
                .build();
        Request request = new Request.Builder()
                .url(address)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
    //注册
    public static void registerWithOkHttp(String address,UserInfo u,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("u",JSON.toJSONString(u))
                .build();
        Request request = new Request.Builder()
                .url(address)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
    //忘记密码
    public static void forgetPasswordWithOkHttp(String address,UserInfo u,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("u",JSON.toJSONString(u))
                .build();
        Request request = new Request.Builder()
                .url(address)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    //获取历史记录商品
    public static void getHistoryWithOkhttp(String address, History h, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("History",JSON.toJSONString(h))
                .build();
        Request request = new Request.Builder()
                .url(address)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
    public static void deleteAllHistoryWithOkhttp(String address ,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().build();
        Request request = new Request.Builder()
                .url(address)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
    public static void deleteHistoryWithOkhttp(String addrsss,History h,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("h",JSON.toJSONString(h))
                .build();
        Request request = new Request.Builder()
                .url(addrsss)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
    public static void inputWithOkHttp(String url,String inputSome,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("inputSome",inputSome)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
    public static void saveOrderWithOkhttp(String url, String orderid, String rece, String ads, String phonenum, String price,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("orderId",orderid)
                .add("receiver",rece)
                .add("address",ads)
                .add("phone",phonenum)
                .add("price",price)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
    public static void allOrderWithOkhttp(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().build();
        Request request = new Request.Builder()
                .url(address)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
    public static void deleteOrderWithOkhttp(String url,String orderId,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("orderid",orderId)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
