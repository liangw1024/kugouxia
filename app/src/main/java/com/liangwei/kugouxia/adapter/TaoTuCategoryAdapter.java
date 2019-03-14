package com.liangwei.kugouxia.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.beans.TaoTuCategoryBean;

/**
 * 套图分类adapter
 */
public class TaoTuCategoryAdapter extends BaseAdapter {
    private Context context;
    private List<TaoTuCategoryBean> beans;
    private int selected;

    public TaoTuCategoryAdapter(Context context, List<TaoTuCategoryBean> beans) {
        this.context = context;
        this.beans = beans;
    }

    @Override
    public int getCount() {
        return beans != null ? beans.size():0;
    }

    @Override
    public Object getItem(int position) {
        return beans != null ? beans.get(position):null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.item_taotu_category,null);
        TextView btn = convertView.findViewById(R.id.item_taotu_category_Btn);
        TaoTuCategoryBean taoTuCategoryBean = beans.get(position);
        btn.setText(taoTuCategoryBean.getName());
        //如果当前的position 等于传过来的点击的position
        if(selected == position){
            btn.setBackground(context.getResources().getDrawable(R.drawable.btn_taotu_category_selected));

            btn.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }else{
            btn.setBackground(context.getResources().getDrawable(R.drawable.btn_taotu_category));
        }
        return convertView;
    }
    public void changeCurrentSelected(int position){
        selected = position;
        notifyDataSetChanged();
    }
    public List<TaoTuCategoryBean> getBeans(){
        return beans;
    }


}
