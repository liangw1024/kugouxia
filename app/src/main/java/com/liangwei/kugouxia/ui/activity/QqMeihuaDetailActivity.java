package com.liangwei.kugouxia.ui.activity;

import android.content.Intent;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.Temp;
import com.liangwei.kugouxia.adapter.AdapterImage;
import com.liangwei.kugouxia.frame.BaseActivity;
import com.liangwei.kugouxia.frame.CustomView.CircleImageView;
import com.liangwei.kugouxia.frame.ShowImageActivity;

public class QqMeihuaDetailActivity extends BaseActivity{
    @BindView(R.id.activity_qq_meihua_detail_toolbar) Toolbar toolbar;
    @BindView(R.id.activity_qq_meihua_detail_circlerView_head) CircleImageView iv_head;
    @BindView(R.id.activity_qq_meihua_detail_textView_name) TextView tv_name;
    @BindView(R.id.activity_qq_meihua_detail_textview_title) TextView tv_title;
    @BindView(R.id.activity_qq_meihua_detail_textview_content) TextView tv_content;
    @BindView(R.id.activity_qq_meihua_detail_recyclerview_images) RecyclerView rv_images;
    String title,content,headUrl,name,time;
    List<String> list_images = null;

    @Override
    public int getContentView() {
        return R.layout.activity_qq_meihua_detail;
    }

    @Override
    public void mOncreate() {
        headUrl = getIntent().getStringExtra("headUrl");
        name = getIntent().getStringExtra("name");
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        time = getIntent().getStringExtra("time");
        list_images = getIntent().getStringArrayListExtra("images");
        Glide.with(getApplicationContext()).load(headUrl).into(iv_head);
        tv_name.setText(name);
        tv_title.setText(title);
        tv_content.setText(content);
        //tv_time.setText(time);
        configRecyclerView();
    }

    @Override
    public void initToolbar() {
        toolbar.setTitle(getIntent().getStringExtra("title"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void configRecyclerView(){
        GridLayoutManager layoutManager = new GridLayoutManager(context,3);
        rv_images.setLayoutManager(layoutManager);
        AdapterImage adapterImage = new AdapterImage(QqMeihuaDetailActivity.this,list_images,AdapterImage.MODE_SQUARE);

        adapterImage.setiClickImage(new AdapterImage.IClickImage<String>() {


            @Override
            public void click(List<String> urls,int position) {
                //ShowImageAct初始位置
                Temp.index = position;
                //修改ShowImageAct的数据
                Temp.imagesUrl = (List<String>) urls;
                startActivity(new Intent(QqMeihuaDetailActivity.this,ShowImageActivity.class));
            }
        });
        rv_images.setAdapter(adapterImage);
        adapterImage.notifyDataSetChanged();
        //解决scroll箱套 recyclerview 滚动没有惯性效果
        rv_images.setNestedScrollingEnabled(false);
        rv_images.setFocusable(false);
    }
}