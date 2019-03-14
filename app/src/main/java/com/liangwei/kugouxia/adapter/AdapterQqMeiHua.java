package com.liangwei.kugouxia.adapter;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.beans.QqMeiHuaBean;
import com.liangwei.kugouxia.frame.CustomView.CircleImageView;

/**
 * QQ美化帖子adapter
 * Created by weibao on 2018/5/4.
 */

public class AdapterQqMeiHua extends RecyclerView.Adapter<AdapterQqMeiHua.ViewHolderQqMeiHua>
{
    private Context context = null;
    //数据集合
    private List<QqMeiHuaBean> datas = null;
    public AdapterQqMeiHua(List<QqMeiHuaBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }
    @Override
    public AdapterQqMeiHua.ViewHolderQqMeiHua onCreateViewHolder(ViewGroup p1, int p2) {
        View view = LayoutInflater.from(p1.getContext()).inflate(R.layout.item_qq_meihua,p1,false);
        return new ViewHolderQqMeiHua(view);
    }

    @Override
    public void onBindViewHolder(AdapterQqMeiHua.ViewHolderQqMeiHua p1, int p2) {
        Glide.with(context).load(datas.get(p2).getUserHeadUrl()).into(p1.iv_head);
        p1.tv_name.setText(datas.get(p2).getUserName());
        p1.tv_title.setText(datas.get(p2).getTitle());
        p1.tv_time.setText(datas.get(p2).getTime().substring(0,10));
        //设置图片适配器的属性
        GridLayoutManager layoutManagerImages = new GridLayoutManager(context,3);
        p1.rv_images.setLayoutManager(layoutManagerImages);
        //列表的图片适配器
        List<String> postImages = datas.get(p2).getImages();
        //只显示3张图片
        if(postImages.size()>3){
            postImages = postImages.subList(0,3);
        }
        AdapterImage adapterImages = new AdapterImage(context,postImages,AdapterImage.MODE_SQUARE);
        p1.rv_images.setAdapter(adapterImages);
        adapterImages.notifyDataSetChanged();

    }

    @Override
    public int getItemCount()
    {
        return datas.size();
    }

    class ViewHolderQqMeiHua extends RecyclerView.ViewHolder{
        /**头像**/
        CircleImageView iv_head;
        /**名字 主题 时间**/
        TextView tv_name,tv_title,tv_time;
        /**帖子图片列表**/
        RecyclerView rv_images;
        public ViewHolderQqMeiHua(View itemView) {
            super(itemView);
            iv_head = itemView.findViewById(R.id.item_qqmeihuaCirclerView_head);
            tv_name = itemView.findViewById(R.id.item_qqmeihuaTextView_name);
            tv_title = itemView.findViewById(R.id.item_qqmeihuaTextView_title);
            tv_time = itemView.findViewById(R.id.item_qqmeihuaTextView_time);
            rv_images = itemView.findViewById(R.id.item_qqmeihua_RecyclerView_images);

        }
    }




}