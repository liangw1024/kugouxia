package com.liangwei.studio.ui.image;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liangwei.studio.R;
import com.liangwei.studio.frame.BitmapUtils;
import com.liangwei.studio.frame.MediaUtils;
import com.liangwei.studio.ui.dialog.IOSDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 *用于查看多个图片 需要传入List作为显示数据
 * 支持左右滑动 显示index
 * 可以自定义image loader
 * ---------------------------------------- Create by  良伟
 */
public class PictureViewer<T> extends RelativeLayout {
    private Context context = null;
    private RelativeLayout layoutRoot = null;
    private ViewPager viewPager = null;
    //index
    private TextView tv_index = null;
    private PagerAdapter pagerAdapter = null;
    // iamges data
    private List<T> datas = null;
    private List<ImageView> imageViews = new ArrayList<>();
    //图片加载器接口
    private ImageLoader imageLoader = null;
    public PictureViewer(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public PictureViewer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init(){
        View view = LayoutInflater.from(context).inflate(R.layout.view_picture_viewer,this,true);
        viewPager = view.findViewById(R.id.view_picture_viewer_ViewPager);
        tv_index = view.findViewById(R.id.view_picture_viewer_tv_index);
        layoutRoot = view.findViewById(R.id.view_picture_viewer_rl_root);
        pagerAdapter = new ViewPagerAdapterImage(imageViews);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tv_index.setText(position+1+"/"+datas.size());

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    public PictureViewer setDatas(List<T> datas) {
        this.datas = datas;
        for (T data:this.datas){
            ImageView imageView = new ImageView(context);
            if (imageLoader!=null){
                imageLoader.loader(imageView,data);
            }
            imageViews.add(imageView);
            pagerAdapter.notifyDataSetChanged();
        }
        return this;
    }

    public PictureViewer setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
        return this;
    }
    /**切换显示图片**/
    public void setShowImageIndex(int index){
         final ImageView imageview = imageViews.get(index);
        //解决获取到的View  width height 为0
//        ViewTreeObserver viewTreeObserver = imageview.getViewTreeObserver();
//        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                Bitmap blurBitmap = BitmapUtils.viewToVBitmap(imageview);
//               layoutRoot.setBackground(new BitmapDrawable(BitmapUtils.blur(blurBitmap,30,true)));
//                return true;
//            }
//        });


        viewPager.setCurrentItem(index);


    }
    private class ViewPagerAdapterImage extends PagerAdapter {
        private List<ImageView> listViews;
        public ViewPagerAdapterImage(List<ImageView> listViews) {
            this.listViews = listViews;
        }

        @Override
        public int getCount() {
            return listViews == null ? 0 : listViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final ImageView imageView = listViews.get(position);
            imageView.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                   final IOSDialog iosDialog = new IOSDialog(context).builder();
                   iosDialog.setTitle("是否保存图片");
                    iosDialog.setBtnLeftText("取消");
                    iosDialog.setBtnRightText("保存");
                    iosDialog.setiOnLeftBtnClick(new IOSDialog.IOnLeftBtnClick() {
                        @Override
                        public void click(View v) {
                            iosDialog.dismiss();
                        }
                    });
                    iosDialog.setiOnRightBtnClick(new IOSDialog.IOnRightBtnClick() {
                        @Override
                        public void click(View v) {
                           if(MediaUtils.saveBitmapToAlbum(context,BitmapUtils.drawableToBitmap(imageView.getDrawable()),"/sdcard/kugouxia/img/",System.currentTimeMillis()+"png")){
                                Toast.makeText(context, "保存到相册成功", Toast.LENGTH_SHORT).show();
                                iosDialog.dismiss();
                            }else{
                                Toast.makeText(context, "保存到相册失败", Toast.LENGTH_SHORT).show();
                                iosDialog.dismiss();
                            }

                        }
                    });
                   iosDialog.show();
                    return true;
                }
            });
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(listViews.get(position));
        }
    }
    public interface ImageLoader{
        public void loader(ImageView imageView, Object data);
    }
}
