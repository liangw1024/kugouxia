package com.liangwei.kugouxia.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.List;

import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.frame.ScreenManager;


/**
 * Created by liang wei on 2018/4/27.
 *  多模式的图片适配器 一般用于列表中
 */
public class AdapterImage<T> extends RecyclerView.Adapter<ViewHolderImage>{
    /**图片默认显示模式**/
    public final static int MODE_DEFAULT= 0;
    /**大图片模式**/
    public final static int MODE_BIG= 1;
    /**自适配模式**/
    public final static int MODE_WRAP_CONTENT = 2;
    /**square模式**/
    public final static int MODE_SQUARE = 3;
    /**显示模式**/
    private int showMode = 0;
    private Context context;

    //存放图片的list
    private List<T> images;
    //点击回调
    IClickImage iClickImage;
    //自定义图片加载器
    private ImageLoader<T> imageLoader;


    /**设置图片点击接口**/
    public void setiClickImage(IClickImage iClickImage) {
        this.iClickImage = iClickImage;
    }

    /**
     * 构造器
     * @param context
     * @param images 图片数据
     * @param showMode 图片的显示模式
     */
    public AdapterImage(Context context, List<T> images,int showMode) {
        this.context = context;
        this.images = images;
        this.showMode = showMode;
    }


    @Override
    public ViewHolderImage onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        //根据不同的显示模式创建root view
        switch (showMode){
            case MODE_DEFAULT:
                view = LayoutInflater.from(context).inflate(R.layout.item_image,parent,false);
                break;
            case MODE_BIG:
                view = LayoutInflater.from(context).inflate(R.layout.item_image_big,parent,false);
                break;
            case MODE_WRAP_CONTENT:
                view = LayoutInflater.from(context).inflate(R.layout.item_image_wrap_content,parent,false);
                break;
            case MODE_SQUARE:
                view = LayoutInflater.from(context).inflate(R.layout.item_image_square,parent,false);
                break;
        }
        return new ViewHolderImage(view,showMode);
    }

    @Override
    public void onBindViewHolder(ViewHolderImage holder, final int position) {
        int screenWidth = ScreenManager.getScreenWidth(context);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.iv_image.getLayoutParams();
        layoutParams.width = screenWidth;
        holder.iv_image.setLayoutParams(layoutParams);
        if(imageLoader!=null){
            imageLoader.load( images.get(position),holder.iv_image);
        }else{

            Glide.with(context) .load(images.get(position))
                    .thumbnail(0.1f)
                    .into(holder.iv_image);
        }



        holder.iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iClickImage!=null){
                    iClickImage.click( images,position);
                }

            }
        });

    }

    @Override
    public int getItemCount() {

        return images.size();
    }

    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    /**
     * 图片点击接口
     */
    public interface IClickImage<T>{
        public void click(List<T> urls,int position);
    }

    /**
     * 自定义图片加载器
     */
    public interface ImageLoader<Type>{
        void load(Type imageType,ImageView view);
    }

}

class ViewHolderImage extends RecyclerView.ViewHolder{
    ImageView iv_image;
    public ViewHolderImage(View itemView,int showMode) {
        super(itemView);
        //根据不同的显示模式 寻找id
        switch (showMode){
            case 0:
                iv_image = itemView.findViewById(R.id.item_image_iv);
                break;
            case 1:
                iv_image = itemView.findViewById(R.id.item_image_big_iv);
                break;
            case 2:
                iv_image = itemView.findViewById(R.id.item_image_wrap_content_iv);
                break;
                case 3:
                iv_image = itemView.findViewById(R.id.item_image_square_iv);
                break;
        }

    }
}