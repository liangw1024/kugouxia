package com.liangwei.kugouxia.frame;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.liangwei.studio.ui.image.PictureViewer;

import butterknife.BindView;
import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.Temp;

/**
 * 显示图片 Create 2018-4-27 14:19:32 by  伟宝
 * 通过修改Temp 里面的 imagesUrl来改变数据
 * intent 传递bitmap过大导致崩溃  通过BitmapUtils.bitmapTemp来缓存bitmap
 */
public class ShowImageActivity extends BaseActivity {

    @BindView(R.id.aaa)
    PictureViewer<String> pictureViewer;

    @Override
    public int getContentView() {
        return R.layout.activity_show_image;
    }

    @Override
    public void mOncreate() {
        pictureViewer.setImageLoader(new PictureViewer.ImageLoader() {
            @Override
            public void loader(ImageView imageView, Object string) {
                Glide.with(ShowImageActivity.this).load((String) string).into(imageView);
            }
        });
        pictureViewer.setDatas(Temp.imagesUrl);
        pictureViewer.setShowImageIndex(Temp.index);
    }

    @Override
    public void initToolbar() {

    }

    }



