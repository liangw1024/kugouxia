package com.liangwei.kugouxia.frame;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by weibao on 2018/4/26.
 */

public class CommonAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    //数据利用泛型
    private List<T> datas;
    //布局直接在构造里面传递
    private int layoutid;

    /**
     * 通用adapter构造方法
     * @param context
     * @param datas
     * @param layoutid
     */
    public CommonAdapter(Context context, List<T> datas, int layoutid) {
        this.context = context;
        this.datas = datas;
        this.layoutid = layoutid;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
