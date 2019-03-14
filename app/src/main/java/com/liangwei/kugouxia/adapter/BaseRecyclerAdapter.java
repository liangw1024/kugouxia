package com.liangwei.kugouxia.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by weibao on 2018/6/10.
 */

public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter <BaseRecyclerAdapter.ViewHolder>{
    protected Context mContext;
    //数据怎么办？利用泛型
    protected List<Object> mDatas;
    // 布局怎么办？直接从构造里面传递
    private int mLayoutId;

    public BaseRecyclerAdapter(Context context, List<Object> datas, int layoutId) {
        this.mContext = context;
        this.mDatas = datas;
        this.mLayoutId = layoutId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 先inflate数据
        View itemView = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);
        // 返回ViewHolder
        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }
    /**
     * 利用抽象方法回传出去，每个不一样的Adapter去设置
     * @param item 当前的数据
     */
    public abstract void convert(ViewHolder holder, Object item);
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        // 用来存放子View减少findViewById的次数
        private SparseArray<View> mViews;
        public ViewHolder(View itemView) {
            super(itemView);
            mViews = new SparseArray<>();
        }
        /**
         * 设置TextView文本
         */
        public ViewHolder setText(int viewId, CharSequence text) {
            TextView tv = getView(viewId);
            tv.setText(text);
            return this;
        }

        /**
         * 通过id获取view
         */
        public <T extends View> T getView(int viewId) {
            // 先从缓存中找
            View view = mViews.get(viewId);
            if (view == null) {
                // 直接从ItemView中找
                view = itemView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        /**
         * 设置View的Visibility
         */
        public ViewHolder setViewVisibility(int viewId, int visibility) {
            getView(viewId).setVisibility(visibility);
            return this;
        }

        /**
         * 设置ImageView的资源
         */
        public ViewHolder setImageResource(int viewId, int resourceId) {
            ImageView imageView = getView(viewId);
            imageView.setImageResource(resourceId);
            return this;
        }

        /**
         * 设置条目点击事件
         */
        public void setOnIntemClickListener(View.OnClickListener listener) {
            itemView.setOnClickListener(listener);
        }

        /**
         * 设置条目长按事件
         */
        public void setOnIntemLongClickListener(View.OnLongClickListener listener) {
            itemView.setOnLongClickListener(listener);
        }

        /**
         * 设置图片通过路径,这里稍微处理得复杂一些，因为考虑加载图片的第三方可能不太一样
         * 也可以直接写死
         */
        public ViewHolder setImageByUrl(Context con,int viewId, String url) {
            ImageView imageView = getView(viewId);
            Glide.with(con).load(url).into(imageView);
            return this;
        }


    }
}
