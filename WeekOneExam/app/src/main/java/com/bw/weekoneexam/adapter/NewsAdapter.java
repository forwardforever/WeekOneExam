package com.bw.weekoneexam.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.weekoneexam.MainActivity;
import com.bw.weekoneexam.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @Auther: jiangnengbao
 * @Date: 2019/2/18 16:37:27
 * @Description:
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnRecyclerViewItemLongClickLisenter {

        void onRecyclerViewItemLongClick(int position);
    }

    private OnRecyclerViewItemLongClickLisenter longClickLisenter;

    public void setOnRecyclerViewItemLongClickLisenter(OnRecyclerViewItemLongClickLisenter longClickLisenter) {
        this.longClickLisenter = longClickLisenter;
    }

    private static int TYPE_ONE = 0;
    private static int TYPE_TWO = 1;

    Context context;
    JSONArray datas;

    public NewsAdapter(Context context, JSONArray datas) {
        this.context = context;
        this.datas = datas;
    }

    //返回条目类型
    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return TYPE_ONE;
        } else {
            return TYPE_TWO;
        }

    }

    //创建ViewHolder
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        //根据类型判断条目布局样式
        if (type == TYPE_ONE) {
            View view1 = LayoutInflater.from(context).inflate(R.layout.item_one, null, false);


            final ViewHolder1 viewHolder1 = new ViewHolder1(view1);

            //长按系统view的监听
            view1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                   //获取view对应的位置
                    int layoutPosition = viewHolder1.getLayoutPosition();
                    //接口回调数据
                    if (longClickLisenter != null) {
                        longClickLisenter.onRecyclerViewItemLongClick(layoutPosition);
                    }

                    return true;
                }
            });
            return viewHolder1;
        } else {
            View view2 = LayoutInflater.from(context).inflate(R.layout.item_two, null, false);
            final ViewHolder2 viewHolder2 = new ViewHolder2(view2);
            //长按系统view的监听
            view2.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    //获取view对应的位置
                    int layoutPosition = viewHolder2.getLayoutPosition();

                    if (longClickLisenter != null) {
                        //回调条目对应的位置
                        longClickLisenter.onRecyclerViewItemLongClick(layoutPosition);
                    }

                    return true;
                }
            });
            return viewHolder2;

        }


    }

    //给控件赋值
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        int itemViewType = getItemViewType(i);
        if (itemViewType == TYPE_ONE) {

            ViewHolder1 viewHolder1 = (ViewHolder1) viewHolder;

            try {
                JSONObject jsonObject = datas.getJSONObject(i);
                viewHolder1.tv_title1.setText(jsonObject.getString("title"));
                JSONArray pics = jsonObject.getJSONArray("pics");
                String imageUrl = (String) pics.get(0);
                Glide.with(context).load("http://365jia.cn/uploads/" + imageUrl).into(viewHolder1.iv1);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else {
            ViewHolder2 viewHolder2 = (ViewHolder2) viewHolder;
            try {
                JSONObject jsonObject = datas.getJSONObject(i);
                viewHolder2.tv_title2.setText(jsonObject.getString("title"));
                JSONArray pics = jsonObject.getJSONArray("pics");
                String imageUrl = (String) pics.get(0);
                Glide.with(context).load("http://365jia.cn/uploads/" + imageUrl).into(viewHolder2.iv2);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public int getItemCount() {
        return datas.length();

    }


   //删除对应条目数据
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void removeItem(int position){
       //操作数据源
        datas.remove(position);
        //刷新
        notifyDataSetChanged();

    }

    //自定义ViewHolde
    public class ViewHolder1 extends RecyclerView.ViewHolder {

        private final TextView tv_title1;
        private final ImageView iv1;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            tv_title1 = itemView.findViewById(R.id.tv_title1);
            iv1 = itemView.findViewById(R.id.iv1);
        }
    }


    public class ViewHolder2 extends RecyclerView.ViewHolder {
        private final TextView tv_title2;
        private final ImageView iv2;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            tv_title2 = itemView.findViewById(R.id.tv_title2);
            iv2 = itemView.findViewById(R.id.iv2);
        }
    }
}
