package com.bw.weekoneexam.utils;

import android.util.Log;

import com.bw.weekoneexam.R;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @Auther: jiangnengbao  单例设计模式封装 懒汉
 * @Date: 2019/2/18 15:48:44
 * @Description:
 */
public class OkHttpUtils {

    //私有的静态的成员变量，只声明不创建
    private static OkHttpUtils okHttpUtils = null;
    //私有的构造方法

    private OkHttpUtils() {

    }

    //返回公共静态的实例方法
    public static OkHttpUtils getInstance() {
        if (okHttpUtils == null) {
            synchronized (OkHttpUtils.class) {

                if (okHttpUtils == null) {
                    okHttpUtils = new OkHttpUtils();
                }
            }
        }


        return okHttpUtils;
    }

    private static OkHttpClient okHttpClient = null;

    //返回ok
    public synchronized static OkHttpClient getOkHttpClient() {

        if (okHttpClient == null) {
            //创建日志拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.i("xxx", message);
                }
            });
            //设置日志拦截器模式
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //创建okHttpClient

            okHttpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
        }


        return okHttpClient;


    }

//get请求
    public void doGet(String url, Callback callback) {
        //创建OkHttpClient
        OkHttpClient okHttpClient = getOkHttpClient();
        //创建Request
        Request request = new Request.Builder().url(url).build();

        Call call = okHttpClient.newCall(request);
        //执行异步
        call.enqueue(callback);


    }
//post请求
    public void doPost(String url, Map<String, String> parmars, Callback callback) {
        //创建OkHttpClient
        OkHttpClient okHttpClient = getOkHttpClient();
        //创建builder
        FormBody.Builder builder = new FormBody.Builder();
        //遍历集合
        for (String key : parmars.keySet()) {
            builder.add(key,parmars.get(key));

        }

        //构建FormBody
        FormBody formBody = builder.build();

        //创建Request
        Request request = new Request.Builder().url(url).post(formBody).build();

        Call call = okHttpClient.newCall(request);
        //执行异步
        call.enqueue(callback);


    }

}
