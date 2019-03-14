package com.liangwei.kugouxia.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.beans.AdBean;

/**
 * Created by-liangWei on 2018/8/19 12:55
 */
public class AdScroolbarAdapter extends RecyclerView.Adapter<AdScroolbarAdapter.AdScroolbarViewHolder> {
    private Context context;
    private List<AdBean> adModels ;
    private ItemOnclickListener itemOnclickListener;
    private View itemView;

    public AdScroolbarAdapter(Context context, List<AdBean> adModels) {
        this.context = context;
        this.adModels = adModels;
    }
    public void setContext(Context context) {
        this.context = context;
    }

    public void setAdModels(List<AdBean> adModels) {
        this.adModels = adModels;
    }

    public void setItemOnclickListener(ItemOnclickListener itemOnclickListener) {
        this.itemOnclickListener = itemOnclickListener;
    }

    @Override
    public AdScroolbarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       itemView = LayoutInflater.from(context).inflate(R.layout.item_ad_scroolbar,null);
        return new AdScroolbarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdScroolbarViewHolder holder, final int position) {
        if(adModels.size()!=0){
            final AdBean data = adModels.get(position);
            Spanned spanned_title = Html.fromHtml(data.getTitle());
            Log.i("kgx"+position,":"+data.getTitle());
            holder.tv_title.setText(spanned_title);
            Spanned spanned_description = Html.fromHtml(data.getDescription());
            holder.tv_description.setText(spanned_description);
            try{
                Glide.with(context).load(data.getImg()).into(holder.iv_img);

            }catch (Exception e){

            }

            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemOnclickListener!=null){
                        itemOnclickListener.onClick(position,data);
                    }

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return adModels.size();
    }

    public class AdScroolbarViewHolder extends RecyclerView.ViewHolder{
        public View layout;
        public TextView tv_title,tv_description;
        public ImageView iv_img;
        public AdScroolbarViewHolder(View itemView){
            super(itemView);
            layout = itemView;
            tv_title = itemView.findViewById(R.id.item_ad_scroolbar_tv_title);
            tv_description = itemView.findViewById(R.id.item_ad_scroolbar_tv_description);
            iv_img = itemView.findViewById(R.id.item_ad_scroolbar_iv);
        }

    }
    /**
     * item点击监听接口
     */
    public interface ItemOnclickListener{
        /**
         * 回调点击position 和data
         * @param position
         * @param data
         */
        public void onClick(int position,AdBean data);
    }
}
