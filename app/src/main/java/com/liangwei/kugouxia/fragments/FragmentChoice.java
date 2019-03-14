package com.liangwei.kugouxia.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jdsjlzx.interfaces.OnItemClickListener;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.liangwei.studio.net.HttpRequestUtils;
import com.liangwei.studio.net.INetCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.adapter.AdapterPicChoice;
import com.liangwei.kugouxia.beans.ChoicePicBean;
import com.liangwei.kugouxia.frame.ToastUtils;


public class FragmentChoice extends Fragment {

    public AdapterPicChoice adapterPicChoice = null;
    public List<ChoicePicBean> datas = new ArrayList<>();
    public int queryPage = 1;
    @BindView(R.id.fragment_choice_rv)
    LRecyclerView lRecyclerView;
    public FragmentChoice() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choice, container, false);
        ButterKnife.bind(this,view);
        initRecyclerView();
        lRecyclerView.refresh();
        return view;
    }

    private void initRecyclerView(){
        lRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        lRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        adapterPicChoice = new AdapterPicChoice(getActivity(),datas);
        final LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapterPicChoice);
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        lRecyclerView.setAdapter(lRecyclerViewAdapter);
        lRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                datas.clear();
                adapterPicChoice.notifyDataSetChanged();
                queryPage=1;
                query();
            }
        });

        //加载更多
        lRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                query();
            }
        });
    }


    private void query(){
        String url = "http://elf.static.maibaapp.com/content/json/mixed/feature-"+queryPage+".json";
        HttpRequestUtils.getNeedUi(getActivity(),url, new INetCallback() {
            @Override
            public void success(String body) {
                parse(body);
                queryPage+=1;
            }
            @Override
            public void fail(Exception e, String detail) {
                lRecyclerView.refreshComplete(0);
                ToastUtils.ShowToast(getActivity(),"加载失败"+"");
            }

        });
    }
    private void parse(String text){
        ToastUtils.ShowToast(getActivity(),"parse");
        try {
            JSONObject jsonObject = new JSONObject(text);
            JSONArray jsonArray_data = jsonObject.getJSONArray("data");
            for(int i=0;i<jsonArray_data.length();i++){
                ChoicePicBean choicePicData = new ChoicePicBean();
                List<String> images = new ArrayList<>();
                JSONObject data =jsonArray_data.getJSONObject(i);
                String title = data.getString("title");
                //评论
                String remark = data.getString("remark");
                choicePicData.setTitle(title);
                choicePicData.setDescribe(remark);

                JSONArray jsonArray_image = data.getJSONArray("imgs");
                for(int n=0;n<jsonArray_image.length();n++){
                    JSONObject img =jsonArray_image.getJSONObject(n);
                    String name ="http://webimg.maibaapp.com/content/img/images/"+img.getString("name")+"@!wallpaper.preview";
                    images.add(name);
                    choicePicData.setImages(images);
                }
                datas.add(choicePicData);
            }
            lRecyclerView.refreshComplete(jsonArray_data.length());
            adapterPicChoice.notifyDataSetChanged();
        } catch (JSONException e) {
            ToastUtils.ShowToast(getActivity(),e.toString());
        }
    }


}
