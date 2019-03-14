package com.liangwei.kugouxia.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.liangwei.studio.frame.MediaUtils;
import com.liangwei.studio.net.HttpRequestUtils;
import com.liangwei.studio.net.INetCallback;
import com.liangwei.studio.ui.dialog.IOSDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.adapter.AdapterImage;
import com.liangwei.kugouxia.frame.AppConfig;
import com.liangwei.kugouxia.frame.ToastUtils;

/**
 * 图片分类
 */
public class FragmentPic extends Fragment {
    @BindView(R.id.fragment_pic_rv) LRecyclerView recyclerView;
    public static String urlInterface ="http://elf.static.maibaapp.com/content/json/avatars/li";
    private int type =15;
    public  int page =0;
    public List<String> images = new ArrayList<>();
    public AdapterImage adapterImage = null;

    public  void setType(int type) {
        this.type = type;
        clearData();
        recyclerView.refresh();
    }

    /**
     * 清除数据
     */
    private void clearData(){
        images.clear();
        if(adapterImage==null){
            return;
        }
        adapterImage.notifyDataSetChanged();
        query();
    }

    public FragmentPic() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pic, container, false);
        ButterKnife.bind(this,view);
        initRecyclerView();
        return view;
    }

    /**
     * 初始化recyclerview
     */
    private void initRecyclerView(){
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        //recyclerview滑动时让Glide停止加载图片 当滑动结束的时候Glide继续加载

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int state) {
                super.onScrollStateChanged(recyclerView, state);
                //空闲状态
                if (state==RecyclerView.SCROLL_STATE_IDLE){
                    Glide.with(getActivity()).resumeRequests();
                }else{
                    Glide.with(getActivity()).pauseRequests();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });


        adapterImage = new AdapterImage(getActivity(),images,AdapterImage.MODE_SQUARE);
        adapterImage.setImageLoader(new AdapterImage.ImageLoader<String>() {
            @Override
            public void load(String url,ImageView v) {
                Glide.with(getActivity()) .load(url)
                        .thumbnail(0.05f)
                        .fitCenter()
                        .into((ImageView) v);
            }
        });

        //保存图片啦
        adapterImage.setiClickImage(new AdapterImage.IClickImage<String>() {
            @Override
            public void click(List<String> urls,int position) {
                Glide.with(getActivity()).load(urls.get(position)).asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap currentBmp, GlideAnimation<? super Bitmap> glideAnimation) {
                        IOSDialog iosDialog = new IOSDialog(getActivity()).builder();
                        iosDialog.setTitle("是否保存图片");
                        iosDialog.setImage(currentBmp);
                        iosDialog.setBtnLeftText("取消");
                        iosDialog.setiOnLeftBtnClick(new IOSDialog.IOnLeftBtnClick() {
                            @Override
                            public void click(View v) {
                                iosDialog.dismiss();
                            }
                        });
                        iosDialog.setBtnRightText("保存");
                        iosDialog.setiOnRightBtnClick(new IOSDialog.IOnRightBtnClick() {
                            @Override
                            public void click(View v) {
                                if (MediaUtils.saveBitmapToAlbum(getActivity(), currentBmp, AppConfig.path, System.currentTimeMillis() + ".png")) {
                                    Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "保存失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        iosDialog.show();
                    }
                });

            }


        });
        final LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapterImage);
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        recyclerView.setAdapter(lRecyclerViewAdapter);
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=0;
                clearData();
                query();
            }
        });

        //加载更多
        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                query();
            }
        });

    }

    /**
     * 查询数据
     */
    public void query(){
        String url= urlInterface+"-"+type+"-"+page+".json";
        HttpRequestUtils.getNeedUi(getActivity(),url, new INetCallback() {
            @Override
            public void success(String body) {
                page+=1;
                parse(body);
            }

            @Override
            public void fail(Exception e, String detail) {
                recyclerView.refreshComplete(0);
                ToastUtils.ShowToast(getActivity(),"加载失败"+"");
            }



        });
    }

    /**
     * 解析数据
     * @param text
     */
    private void parse(String text){
        try
        {
            JSONObject jsonObject = new JSONObject(text);
            String urlHead = jsonObject.getString("bu");
            JSONArray jsonArray_data = jsonObject.getJSONArray("data");
            for(int i=0;i<jsonArray_data.length();i++){
                JSONObject data =jsonArray_data.getJSONObject(i);
                String location = data.getString("in");
                images.add(urlHead+location+"@!avatar.preview");
            }
            adapterImage.notifyDataSetChanged();
            recyclerView.refreshComplete(jsonArray_data.length());
            Glide.with(getActivity()).resumeRequests();
        }
        catch (JSONException e)
        {}
    }




}
