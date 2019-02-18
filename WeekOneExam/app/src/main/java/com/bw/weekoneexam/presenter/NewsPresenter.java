package com.bw.weekoneexam.presenter;

import com.bw.weekoneexam.MainActivity;
import com.bw.weekoneexam.model.NewsModel;
import com.bw.weekoneexam.view.NewsView;

import org.json.JSONArray;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * @Auther: jiangnengbao
 * @Date: 2019/2/18 15:45:09
 * @Description:
 */
public class NewsPresenter<T> {


    private final NewsModel model;
    private final NewsView newsView;
    //声明Reference引用
    private Reference<T> reference;

    //构造方法中实例m和v
    public NewsPresenter(NewsView view) {

        newsView = view;

        model = new NewsModel();

    }
    //用弱引用管理view

    public void attachView(T t) {
        //实例
        reference = new WeakReference<T>(t);
    }

    // p关联m
    public void relecte(int page) {

        model.getNewsData(page);

        //设置监听

        model.setOnNewsLisenter(new NewsModel.OnNewsLisenter() {
            @Override
            public void onNews(JSONArray datas) {
                //接收数据
                newsView.onView(datas);

            }
        });


    }

    //解除弱引用中的view
    public void detachView() {
        if (reference.get() != null) {
            reference.clear();
            reference = null;
        }
    }

}
