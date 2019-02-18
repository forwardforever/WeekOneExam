package com.bw.weekoneexam.model;

import android.os.Handler;
import android.os.Message;

import com.bw.weekoneexam.utils.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @Auther: jiangnengbao
 * @Date: 2019/2/18 15:46:55
 * @Description:
 */
public class NewsModel {
    private String url = "http://365jia.cn/news/api3/365jia/news/headline";

    //在外部类中定义接口回调
    public interface OnNewsLisenter {
        void onNews(JSONArray datas);
    }

    private OnNewsLisenter lisenter;

    public void setOnNewsLisenter(OnNewsLisenter lisenter) {
        this.lisenter = lisenter;
    }


    //创建
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 0:
                    String json = (String) msg.obj;
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        JSONObject data = jsonObject.getJSONObject("data");
                        JSONArray datas = data.getJSONArray("data");
                        //判空
                        if (lisenter != null) {
                            //回调数据
                            lisenter.onNews(datas);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
            }

        }
    };

    //获取网络数据
    public void getNewsData(int page) {

        OkHttpUtils.getInstance().doGet(url + "?page=" + page, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();

                //发消息
                Message message = new Message();
                message.what = 0;
                message.obj = json;
                handler.sendMessage(message);

            }
        });


    }
}
