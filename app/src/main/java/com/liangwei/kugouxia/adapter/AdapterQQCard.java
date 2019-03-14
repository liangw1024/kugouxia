package com.liangwei.kugouxia.adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.beans.HlxXianBaoBean;
import com.liangwei.kugouxia.beans.QQCard;

import com.liangwei.kugouxia.frame.CustomView.CircleImageView;
import com.liangwei.kugouxia.frame.loading.WbLoadingDialog;
import com.liangwei.kugouxia.frame.utils.BitmapUtils;
import com.tencent.qq.widget.QQDialog;
import com.tencent.qq.widget.QQToast;

import java.util.ArrayList;
import java.util.List;

/**
 * qq套图adapter
 */
public class AdapterQQCard extends RecyclerView.Adapter<AdapterQQCard.ViewHolderQQcard> {
    private Context context;
    //cards data
    private List<QQCard> qqCards = new ArrayList<>();

    public AdapterQQCard(Context context, List<QQCard> qqCards) {
        this.context = context;
        this.qqCards = qqCards;
    }

    @NonNull
    @Override
    public ViewHolderQQcard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_qq_card, null);
        return new ViewHolderQQcard(view);
    }

    @Override
    public void onBindViewHolder(final AdapterQQCard.ViewHolderQQcard holder, final int position) {
        final QQCard qqCard = qqCards.get(position);
        Glide.with(context).load(qqCard.getHeadUrl()).into(holder.img_head);
        Glide.with(context).load(qqCard.getBackgroundUrl()).into(holder.img_background);
        holder.tv_name.setText(qqCard.getName());
        holder.tv_signatrue.setText(qqCard.getSignatrue());
        holder.tv_time.setText(HlxXianBaoBean.stampToTime(qqCard.getTime()));
        holder.cv_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QQDialog qqDialog = new QQDialog(context);
                qqDialog.setCancelable(true);
                qqDialog.setCanceledOnTouchOutside(true);
                qqDialog.setViewLine(QQDialog.setLineColor.BLUE);
                qqDialog.setTitle("请选择操作");
                qqDialog.setNegativeButton("下载图片", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final WbLoadingDialog wbLoadingDialog = new WbLoadingDialog(context, "下载中", false);
                        wbLoadingDialog.show();
                        Glide.with(context).load(qqCards.get(position).getHeadUrl()).asBitmap().into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                BitmapUtils.SaveBitmap(resource, context);
                                wbLoadingDialog.dismiss();
                                wbLoadingDialog.show();
                                Glide.with(context).load(qqCards.get(position).getBackgroundUrl()).asBitmap().into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        BitmapUtils.SaveBitmap(resource, context);
                                        wbLoadingDialog.dismiss();
                                        QQToast.makeText(context, "下载成功", QQToast.setBackgroundColors.DEFAULT).show();
                                    }
                                });
                            }
                        });

                    }
                });
                qqDialog.setNeutralButton("复制签名", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        clipboardManager.setText(qqCards.get(position).getSignatrue());
                        QQToast.makeText(context, "复制签名成功，请粘贴使用", QQToast.setBackgroundColors.DEFAULT).show();
                    }
                });
                qqDialog.setPositiveButton("复制网名", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        clipboardManager.setText(qqCards.get(position).getName());
                        QQToast.makeText(context, "复制网名成功，请粘贴使用", QQToast.setBackgroundColors.DEFAULT).show();
                    }
                });
                qqDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return qqCards.size();
    }

    class ViewHolderQQcard extends RecyclerView.ViewHolder {
        CardView cv_root;
        //q背景墙 喜欢
        ImageView img_background;
        //q头像
        CircleImageView img_head;
        //q 昵称 签名 时间
        TextView tv_name, tv_signatrue, tv_time;
        public ViewHolderQQcard(View itemView) {
            super(itemView);
            cv_root = itemView.findViewById(R.id.item_qq_card_cv);
            img_background = itemView.findViewById(R.id.item_qq_card_img_background);
            img_head = itemView.findViewById(R.id.item_qq_card_img_head);
            tv_name = itemView.findViewById(R.id.item_qq_card_textview_name);
            tv_signatrue = itemView.findViewById(R.id.item_qq_card_textview_signature);
            tv_time = itemView.findViewById(R.id.item_qq_card_textview_time);
        }
    }
}


