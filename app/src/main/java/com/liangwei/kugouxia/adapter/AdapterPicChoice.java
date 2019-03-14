package com.liangwei.kugouxia.adapter;

        import android.content.Context;
        import androidx.recyclerview.widget.RecyclerView;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import java.util.ArrayList;
        import java.util.List;

        import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.beans.ChoicePicBean;
        import com.liangwei.kugouxia.frame.CustomView.ImagesRecyclerView;

/**
 * Created by weibao on 2018/4/14.
 */

public class AdapterPicChoice extends RecyclerView.Adapter<AdapterPicChoice.ViewHolderXianBao> {
    private Context context;
    private List<ChoicePicBean> datas = new ArrayList<>();
    public AdapterPicChoice(Context context, List<ChoicePicBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    public AdapterPicChoice() {

    }


    @Override
    public ViewHolderXianBao onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.item_choice_pic,null);
        return new ViewHolderXianBao(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderXianBao holder, final int position) {

        //set title
        holder.tv_title.setText(datas.get(position).getTitle());
        //set content
        holder.tv_describe.setText(datas.get(position).getDescribe());
        holder.rv_images.setList_images(datas.get(position).getImages());
        holder.rv_images.updata();


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolderXianBao extends RecyclerView.ViewHolder{
        TextView tv_title,tv_describe;
        ImagesRecyclerView rv_images;

        public ViewHolderXianBao(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.item_choice_pic_tv_title);
            tv_describe = itemView.findViewById(R.id.item_choice_pic_tv_describe);
            rv_images = itemView.findViewById(R.id.item_choice_pic_rv);
        }
    }

}
