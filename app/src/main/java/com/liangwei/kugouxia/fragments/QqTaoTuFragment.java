package com.liangwei.kugouxia.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.adapter.AdapterQQCard;
import com.liangwei.kugouxia.adapter.TaoTuCategoryAdapter;
import com.liangwei.kugouxia.beans.QQCard;
import com.liangwei.kugouxia.beans.TaoTuCategoryBean;
import com.liangwei.kugouxia.frame.BaseActivity;
import com.liangwei.kugouxia.frame.ToastUtils;
import com.liangwei.kugouxia.model.IModelView;
import com.liangwei.kugouxia.model.ModelParent;
import com.liangwei.kugouxia.model.QQTaoTuModel;

import com.liangwei.kugouxia.ui.activity.ChoicePicAcitvity;
import com.liangwei.studio.ui.ExpandView;
import com.tencent.qq.widget.QQToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class QqTaoTuFragment extends Fragment {
    public static int LOAD_TYPE_REFRESH = 0;
    public static int LOAD_TYPE_MORE = 1;
    private int pageNum = 1;
    private int category = 1;
    public String getUrl(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(domain);
        stringBuilder.append("/bbs/newUgc/category/info/2/");
        stringBuilder.append(category+"/");
        stringBuilder.append(String.valueOf(pageNum*20)+"/"+String.valueOf(pageNum*20+19));
        return stringBuilder.toString();
    }
    @BindView(R.id.fragment_qqTaoTu_lRecyclerView) LRecyclerView lRecyclerView;
    @BindView(R.id.fragment_qqTaoTu_textView_rightTitle) TextView btn_title;
    @BindView(R.id.fragment_qqTaoTu__toolbar) Toolbar toolbar;
    @BindView(R.id.fragment_taotu_category) ImageView iv_category;
    @BindView(R.id.fragment_qqTaoTu_btn_reload) Button btn_reload;
    @BindView(R.id.fragment_qqtaotu_expandview) ExpandView expandView;

    @OnClick(R.id.fragment_qqTaoTu_textView_rightTitle) public void startChoice() {
        startActivity(new Intent(getActivity(), ChoicePicAcitvity.class));
    }
    @OnClick(R.id.fragment_qqTaoTu_btn_reload) public void reload() {
        lRecyclerView.setVisibility(View.VISIBLE);
        lRecyclerView.forceToRefresh();

    }
    private String domain = "http://spare.maibaapp.com/";
    //展开 关闭category
    @OnClick(R.id.fragment_taotu_category) public void categoty() {
        switch (expandView.getState()) {
            case ExpandView.STATE_EXPAND:
                expandView.collapse();
                RotateAnimation rotateAnimation = new RotateAnimation(180, 360, 25, 25);
                rotateAnimation.setDuration(300);
                rotateAnimation.setFillAfter(true);
                iv_category.setAnimation(rotateAnimation);
                iv_category.startAnimation(rotateAnimation);
                break;
            case ExpandView.STATE_COLLAPSE:
                RotateAnimation rotateAnimationCollapse = new RotateAnimation(0, 180, 25, 25);
                rotateAnimationCollapse.setDuration(300);
                rotateAnimationCollapse.setFillAfter(true);
                iv_category.setAnimation(rotateAnimationCollapse);
                iv_category.startAnimation(rotateAnimationCollapse);
                expandView.expand();
                break;
        }

    }
    private GridView gridView_categoty;
    private List<QQCard> qqCards = new ArrayList<>();
    public AdapterQQCard adapter = null;
    LRecyclerViewAdapter lRecyclerViewAdapter;
    private TaoTuCategoryAdapter taoTuCategoryAdapter = null;
    QQTaoTuModel qqTaoTuModel = new QQTaoTuModel();

    public QqTaoTuFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qq_tao_tu, container, false);
        ButterKnife.bind(this, view);
        expandView.setLayout(R.layout.expand_test);
        initLRecyclerView();
        BaseActivity activity = (BaseActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        /*init grid*/
        taoTuCategoryAdapter = new TaoTuCategoryAdapter(getActivity(), TaoTuCategoryBean.getBeans());
        expandView.setiViewParse(new ExpandView.IViewParse() {
            @Override
            public void parse(View view) {
                WindowManager manager = getActivity().getWindowManager();
                DisplayMetrics outMetrics = new DisplayMetrics();
                manager.getDefaultDisplay().getMetrics(outMetrics);
                int width = outMetrics.widthPixels;
                int height = outMetrics.heightPixels;
                gridView_categoty = view.findViewById(R.id.expand_taotu_category_gridView);
                gridView_categoty.setColumnWidth((int) width / 5);
                //GridView中 left right 可用空间均分
                gridView_categoty.setStretchMode(GridView.STRETCH_SPACING);
                Log.d("gridview::::", "space:" + gridView_categoty.getHorizontalSpacing());
                gridView_categoty.setAdapter(taoTuCategoryAdapter);
                gridView_categoty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        taoTuCategoryAdapter.changeCurrentSelected(position);
                        category = Integer.valueOf(taoTuCategoryAdapter.getBeans().get(position).getId());
                        loadData(LOAD_TYPE_REFRESH);
                    }
                });
            }
        });

        return view;
    }


    private void initLRecyclerView() {
        adapter = new AdapterQQCard(getActivity(), qqCards);
        lRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        lRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        lRecyclerView.setAdapter(lRecyclerViewAdapter);
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {

            }
        });
        //无网络
        lRecyclerView.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
            @Override
            public void reload() {
                lRecyclerView.setVisibility(View.GONE);
                btn_reload.setVisibility(View.VISIBLE);
                Log.d("kgx", "not net");
                ToastUtils.ShowToast(getActivity(), "not net");
            }
        });
        //刷新
        lRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData(LOAD_TYPE_REFRESH);
            }
        });

        //加载更多
        lRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadData(LOAD_TYPE_MORE);
            }
        });
        lRecyclerView.forceToRefresh();
    }

    /**
     * load data
     * @param type load type
     */
    private void loadData(int type){
        if (type==LOAD_TYPE_REFRESH){
            clearData();
        }
        qqTaoTuModel.requestByGet(getActivity() , getUrl(),new IModelView<QQCard>(){
            @Override
            public void success(ArrayList<QQCard> obj) {
                QqTaoTuFragment.this.qqCards.addAll(obj);
                adapter.notifyDataSetChanged();
                lRecyclerView.refreshComplete(qqCards.size());
                QQToast.makeText(getActivity(),"kkk" ,QQToast.setBackgroundColors.WHITE ).show();
                pageNum+=1;
            }
            @Override
            public void fail(Exception e) {
                lRecyclerView.refreshComplete(0);
                lRecyclerView.setVisibility(View.GONE);
                btn_reload.setVisibility(View.VISIBLE);
                QQToast.makeText(getActivity(),"刷新失败" , QQToast.setBackgroundColors.BLUE).show();
            }

            @Override
            public void loading(int progress) {

            }
        }, new ModelParent.IParse() {
            @Override
            public void success() {

            }

            @Override
            public void fail(Exception e) {
                QQToast.makeText(getActivity(),e.toString(),QQToast.setBackgroundColors.WHITE ).show();
            }
        });

    }
    /**
     * 清楚list数据
     */
    private void clearData() {
        if (qqCards.size() > 0) {
            pageNum = 1;
            qqCards.clear();
            adapter.notifyDataSetChanged();
        }

    }


}
