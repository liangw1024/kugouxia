package com.liangwei.kugouxia.adapter;


import android.content.ClipboardManager;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.frame.AppConfig;
import com.liangwei.kugouxia.frame.ToastUtils;

/**
 *藏头诗适配器
 * Created by weibao on 2018/7/20.
 */

public class AdapterAcrostic extends RecyclerView.Adapter<AdapterAcrostic.ViewHolder> {
    private Context context;
    //数据
    private List<String> acrostics = new ArrayList<>();

    /**
     *
     * @param context
     * @param acrostics
     */
    public AdapterAcrostic(Context context, List<String> acrostics) {
        this.context = context;
        this.acrostics = acrostics;
    }

    public AdapterAcrostic() {

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_acrostic, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String acrostic = acrostics.get(position).replace("，","\n").replace("。","\n");
       holder.tv_acrostic.setText(acrostic);
       //复制
       holder.linearLayout_copy.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
               clipboardManager.setText(acrostics.get(position));
               ToastUtils.ShowToast(context,"复制成功");
           }
       });
       //收藏
        holder.linearLayout_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.ShowToast(context,"你的伟，正在开发本功能...");
            }
        });
        //分享
       holder.linearLayout_share.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               com.liangwei.kugouxia.frame.utils.ShareUtils.shareText(context,acrostic+"\n来自酷狗侠\t"+ AppConfig.app_url+"app.html");
           }
       });

    }

    @Override
    public int getItemCount() {
        return acrostics.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_acrostic;
        LinearLayout linearLayout_copy,linearLayout_collection,linearLayout_share;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_acrostic = itemView.findViewById(R.id.item_acrostic_text);
            linearLayout_copy = itemView.findViewById(R.id.item_acrostic_linearLayout_copy);
            linearLayout_collection = itemView.findViewById(R.id.item_acrostic_linearLayout_collection);
            linearLayout_share = itemView.findViewById(R.id.item_acrostic_linearLayout_share);

        }
    }

}
