package com.liangwei.kugouxia.ui.activity.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.tencent.qq.widget.QQToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.adapter.AdapterImage;
import com.liangwei.kugouxia.frame.BaseActivity;
import com.liangwei.kugouxia.frame.utils.BitmapUtils;

/**
 * 制作外卖头像
 * Created by 伟宝 on 2018/4/27.
 */
public class MakeHeadActivity extends BaseActivity{
    private Bitmap bitmap = null;//素材bitmap
    private Bitmap makeedBitmap = null;//添加文字制作的bitmap
    @BindView(R.id.activity_make_head_toolbar) Toolbar toolbar;
    @BindView(R.id.activity_make_head_iv_show) ImageView iv_show;
    @BindView(R.id.activity_make_head_et_text) MaterialEditText et_text;
    @BindView(R.id.activity_make_head_et_textsize) MaterialEditText et_textSize;
    @BindView(R.id.activity_make_rv_demo) RecyclerView rv_demo;
    //image data
    private List<Bitmap> images = new ArrayList<>();

    @OnClick(R.id.activity_make_head_btn_save) public void clickSave(){
        if(makeedBitmap==null){
            QQToast.makeText(getApplicationContext(),"请先生成图片", QQToast.setBackgroundColors.DEFAULT).show();
        }else{
            BitmapUtils.SaveBitmap(makeedBitmap,MakeHeadActivity.this);
        }

    }
    @Override
    public int getContentView() {
        return R.layout.activity_make_head;
    }

    @Override
    public void mOncreate() {
        et_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //修改bitmap后 显示到iv_show
                String text = et_text.getText().toString();
                makeedBitmap = BitmapUtils.createWatermark(bitmap,et_text.getText().toString(),Integer.valueOf(et_textSize.getText().toString()));
                iv_show.setImageBitmap(makeedBitmap);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        images.add(BitmapFactory.decodeResource(getResources(),R.mipmap.head_red));
        images.add(BitmapFactory.decodeResource(getResources(),R.mipmap.head_pink));
        images.add(BitmapFactory.decodeResource(getResources(),R.mipmap.head_green));
        images.add(BitmapFactory.decodeResource(getResources(),R.mipmap.head_yellow));
        images.add(BitmapFactory.decodeResource(getResources(),R.mipmap.head_black));
        images.add(BitmapFactory.decodeResource(getResources(),R.mipmap.head_purple));
        bitmap = images.get(0);
        iv_show.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.head_red));
        rv_demo.setLayoutManager(new GridLayoutManager(MakeHeadActivity.this,3));
        AdapterImage adapterImage = new AdapterImage(MakeHeadActivity.this,images,AdapterImage.MODE_DEFAULT);
        adapterImage.setImageLoader(new AdapterImage.ImageLoader<Bitmap>() {
            @Override
            public void load(Bitmap imageType, ImageView view) {
                view.setImageDrawable(new BitmapDrawable(getResources(),imageType));
            }
        });
        adapterImage.setiClickImage(new AdapterImage.IClickImage<Bitmap>() {
            @Override
            public void click(List<Bitmap> bs,int position) {
                iv_show.setImageBitmap(bs.get(position));
                bitmap = bs.get(position);
                makeedBitmap = BitmapUtils.createWatermark(bitmap,et_text.getText().toString(),Integer.valueOf(et_textSize.getText().toString()));
                iv_show.setImageBitmap(makeedBitmap);
            }



//            @Override
//            public void click(Bitmap bit) {
//                iv_show.setImageBitmap(bit);
//                bitmap = bit;
//
//                makeedBitmap = BitmapUtils.createWatermark(bitmap,et_text.getText().toString(),Integer.valueOf(et_textSize.getText().toString()));
//                iv_show.setImageBitmap(makeedBitmap);
//            }
        });
        rv_demo.setAdapter(adapterImage);
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
}