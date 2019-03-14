package com.liangwei.kugouxia.ui.activity;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.Temp;
import com.liangwei.kugouxia.adapter.AdapterImage;
import com.liangwei.kugouxia.frame.BaseActivity;
import com.liangwei.kugouxia.frame.ShowImageActivity;
import com.liangwei.kugouxia.frame.tbs.X5Activity;

public class XianBaoDetailActivity extends BaseActivity {

    @BindView(R.id.activity_xainbao_detail_toolbar) Toolbar toolbar;

    @BindView(R.id.activity_xainbao_detail_tv_title) TextView tv_title;
    @BindView(R.id.activity_xainbao_detail_tv_content) TextView tv_content;
    @BindView(R.id.activity_xainbao_detail_rv_img) RecyclerView rv_images;
    @Override
    public int getContentView() {
        return R.layout.activity_xian_bao_detail;
    }

    @Override
    public void mOncreate() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("detail");
        List<String> images = intent.getStringArrayListExtra("images");
        tv_title.setText(title);
        tv_content.setText((content));
        //tv_content.setText(Html.fromHtml(content));
        textHtmlClick(XianBaoDetailActivity.this,tv_content);
        rv_images.setLayoutManager(new GridLayoutManager(context,3));
        AdapterImage adapterImage = new AdapterImage(XianBaoDetailActivity.this,images, AdapterImage.MODE_SQUARE);
        adapterImage.setiClickImage(new AdapterImage.IClickImage<String>() {
            @Override
            public void click(List<String> urls,int positon) {
                //ShowImageAct初始位置
                Temp.index = positon;
                //修改ShowImageAct的数据
                Temp.imagesUrl = (List<String>) urls;
                startActivity(new Intent(XianBaoDetailActivity.this,ShowImageActivity.class));
            }



        });
        rv_images.setAdapter(adapterImage);
        adapterImage.notifyDataSetChanged();

    }

    @Override
    public void initToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    /**
     * 处理html文本超链接点击事件
     * @param context
     * @param tv
     */
    public static void textHtmlClick(Context context, TextView tv) {
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        CharSequence text = tv.getText();
        if (text instanceof Spannable) {
            int end = text.length();
            Spannable sp = (Spannable) text;
            URLSpan[] urls = sp.getSpans(0, end, URLSpan.class);
            SpannableStringBuilder style = new SpannableStringBuilder(text);
            style.clearSpans();// should clear old spans
            for (URLSpan url : urls) {
                MyURLSpan myURLSpan = new MyURLSpan(url.getURL(), context);
                style.setSpan(myURLSpan, sp.getSpanStart(url),
                        sp.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            tv.setText(style);
        }
    }
    private static class MyURLSpan extends ClickableSpan {
        private String mUrl;
        private Context mContext;

        MyURLSpan(String url, Context context) {
            mContext = context;
            mUrl = url;
        }

        @Override
        public void onClick(View widget) {
            Intent intent = new Intent();
            intent.putExtra("url",mUrl);
            intent.putExtra("isShowMenu",true);
            intent.setClass(mContext, X5Activity.class);
            mContext.startActivity(intent);

        }
    }
}
