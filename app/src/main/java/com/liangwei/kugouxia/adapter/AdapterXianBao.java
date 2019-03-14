package com.liangwei.kugouxia.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.beans.HlxXianBaoBean;
import com.liangwei.kugouxia.ui.activity.XianBaoDetailActivity;

/**
 * Created by weibao on 2018/4/14.
 */

public class AdapterXianBao extends RecyclerView.Adapter<AdapterXianBao.ViewHolderXianBao> {
    private Context context;
    private List<HlxXianBaoBean> xianBaos = new ArrayList<>();
    public AdapterXianBao(Context context, List<HlxXianBaoBean> xianBaos) {
        this.context = context;
        this.xianBaos = xianBaos;
    }



    @Override
    public ViewHolderXianBao onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.item_xianbao,null);
        return new ViewHolderXianBao(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderXianBao holder, final int position) {
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("title",xianBaos.get(position).getTitle());
                intent.putExtra("detail",xianBaos.get(position).getDetail());
                intent.putStringArrayListExtra("images", (ArrayList<String>) xianBaos.get(position).getImages());
                intent.setClass(context,XianBaoDetailActivity.class);
                context.startActivity(intent);
            }
        });
        //set title
        holder.tv_title.setText(xianBaos.get(position).getTitle());
        //set content
        if(xianBaos.get(position).getDetail().length()>=20){
            holder.tv_content.setText(xianBaos.get(position).getDetail().substring(0,20).replace("\n","    "));
        }else{
            holder.tv_content.setText(xianBaos.get(position).getDetail().replace("\n","    "));
        }
        //set time
        holder.tv_time.setText(HlxXianBaoBean.stampToTime( xianBaos.get(position).getCreateTime()));
        //set user

        //set recyclerView
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,3);
        holder.rv_images.setLayoutManager(gridLayoutManager);
        holder.rv_images.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }
        });
        //只显示3张图片
        List<String> postImages = xianBaos.get(position).getImages();
        if (postImages.size()>3){
            postImages = postImages.subList(0,3);
        }

        AdapterImage adapterImages = new AdapterImage(context,postImages,AdapterImage.MODE_SQUARE);
        holder.rv_images.setAdapter(adapterImages);
        adapterImages.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return xianBaos.size();
    }

    class ViewHolderXianBao extends RecyclerView.ViewHolder{
        TextView tv_title,tv_content,tv_time;
        RecyclerView rv_images;
        RelativeLayout root;

         public ViewHolderXianBao(View itemView) {
             super(itemView);
             root = itemView.findViewById(R.id.item_xianbao_rl_root);
             tv_title = itemView.findViewById(R.id.item_xianbao_tv_title);
             tv_content = itemView.findViewById(R.id.item_xianbao_tv_content);
             tv_time = itemView.findViewById(R.id.item_xianbao_tv_time);
             rv_images = itemView.findViewById(R.id.item_xianbao_rv_images);
         }
     }

}
