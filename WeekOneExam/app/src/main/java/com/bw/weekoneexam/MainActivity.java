package com.bw.weekoneexam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.bw.weekoneexam.adapter.NewsAdapter;
import com.bw.weekoneexam.presenter.NewsPresenter;
import com.bw.weekoneexam.view.NewsView;
import com.bw.weekoneexam.widget.MyDecoration;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity implements NewsView {

    private RecyclerView rlv;
    private NewsPresenter presenter;
    private int page = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //找控件
        rlv = findViewById(R.id.rlv);

        //创建布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        rlv.setLayoutManager(linearLayoutManager);

        //这句就是添加我们自定义的分隔线
        rlv.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));

        //实例p
        presenter = new NewsPresenter(this);
        //内部类使用弱引用管理外部类
        presenter.attachView(this);

        //调用p关联m
        presenter.relecte(page);

        //分页
        //下拉刷新的时候page为1
        // presenter.relecte(page);
        //上拉加载 page++
        //presenter.relecte(page);


    }

    @Override
    public void onView(JSONArray datas) {
        //接收数据

        Log.i("xxx", datas.length() + "");


        final NewsAdapter adapter = new NewsAdapter(this, datas);

        rlv.setAdapter(adapter);
        //长按监听
        adapter.setOnRecyclerViewItemLongClickLisenter(new NewsAdapter.OnRecyclerViewItemLongClickLisenter() {
            @Override
            public void onRecyclerViewItemLongClick(int position) {
                //接收数据
                Toast.makeText(MainActivity.this, "长按条目：" + position, Toast.LENGTH_SHORT).show();
                 //删除数据
                adapter.removeItem(position);

            }
        });


    }

    //解决内存泄漏

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
