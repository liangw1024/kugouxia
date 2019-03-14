package com.liangwei.kugouxia.ui.activity;

import android.os.Handler;
import android.os.Message;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.github.kevinsawicki.http.HttpRequest;
import com.tencent.qq.widget.QQToast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.adapter.AdapterXianBao;
import com.liangwei.kugouxia.beans.HlxXianBaoBean;
import com.liangwei.kugouxia.frame.BaseActivity;

public class XianBaoActivity extends BaseActivity {
    @BindView(R.id.activity_xian_bao_toolbar) Toolbar toolbar;


    public AdapterXianBao adapterXianBao = null;
    public LRecyclerView lRecyclerView = null;
    public List<HlxXianBaoBean> xianBaos = new ArrayList<>();
    private int numberOfPage = 20;
    private String lastTime = "0";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.obj.equals("fail")) {
                if (msg.what == 0) {
                    QQToast.makeText(getApplicationContext(), "刷新失败", QQToast.setBackgroundColors.DEFAULT).show();
                    lRecyclerView.refreshComplete(0);
                } else if (msg.what == 1) {
                    QQToast.makeText(getApplicationContext(), "加载失败", QQToast.setBackgroundColors.DEFAULT).show();
                    lRecyclerView.refresh();
                }
            } else if (msg.obj.equals("success")) {
                lRecyclerView.refreshComplete(numberOfPage);
                adapterXianBao.notifyDataSetChanged();
            }

        }
    };
    private boolean animatorIsRunning = false;

    @Override
    public int getContentView() {
        return R.layout.activity_xian_bao;
    }

    @Override
    public void mOncreate() {
        initLRecyclerView();
        lRecyclerView.forceToRefresh();
    }

    @Override
    public void initToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }




    private void initLRecyclerView() {
        lRecyclerView = findViewById(R.id.activity_xian_bao_lRecyclerView);
        lRecyclerView.setLayoutManager(new LinearLayoutManager(XianBaoActivity.this));
        lRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);

        adapterXianBao = new AdapterXianBao(XianBaoActivity.this, xianBaos);
        final LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapterXianBao);
        lRecyclerView.setAdapter(lRecyclerViewAdapter);
        lRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                lastTime = "0";
                queryData(0, String.valueOf(numberOfPage), lastTime);
            }
        });

        //加载更多
        lRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                queryData(1, String.valueOf(numberOfPage), lastTime);
            }
        });

    }


    /**
     * @param type  0 刷新 1加载
     * @param count
     * @param time
     */
    private void queryData(final int type, final String count, String time) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://floor.huluxia.com/post/list/ANDROID/2.0?platform=2&gkey=000000&app_version=3.5.1.76.3&versioncode=217&market_id=tool_tencent&_key=B249EACDEAAD12B6B0E6BD81E98808BC3803B9CE8FD672F95ECE20112C1C0CFBCB6EEE70A7EBF8CADDAA26D0E6CA51A9DE1583B79E8A65B3&device_code=%5Bw%5D02%3A00%3A00%3A00%3A00%3A00-%5Bi%5D863092038252360-%5Bs%5D89860117881028005886&start=" + lastTime + "&count=" + count + "&cat_id=70&tag_id=0&sort_by=1";
                HttpRequest httpRequest = HttpRequest.get(url);
                if (type == 0) {
                    clearDate();
                }
                Message message = new Message();
                if (httpRequest.code() == 200) {//查询成功
                    parseData(httpRequest.body());
                } else {//queryFail
                    message.obj = "fail";
                    message.what = type;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }


    /**
     * 解析数据
     *
     * @param data
     */
    private void parseData(String data) {

        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray posts = jsonObject.getJSONArray("posts");
            for (int i = 0; i < posts.length(); i++) {//遍历post
                JSONObject post = posts.getJSONObject(i);
                HlxXianBaoBean xianBaoData = new HlxXianBaoBean();
                List<String> list_images = new ArrayList();
                JSONArray array_images = post.getJSONArray("images");
                for (int k = 0; k < array_images.length(); k++) {//遍历image
                    String imageUrl = (String) array_images.get(k);
                    list_images.add(imageUrl);
                }
                String title = post.getString("title");
                String content = post.getString("detail");
                String postId = post.getString("postID");
                String createTime = post.getString("createTime");

                xianBaoData.setTitle(title);
                xianBaoData.setDetail(content);
                xianBaoData.setPostID(postId);
                xianBaoData.setCreateTime(createTime);
                xianBaoData.setImages(list_images);
                xianBaos.add(xianBaoData);
            }
            //得到最后一个item的createTime
            lastTime = xianBaos.get(xianBaos.size() - 1).getCreateTime();
            Message message = new Message();
            message.obj = "success";
            handler.sendMessage(message);
        } catch (Exception e) {
            Log.e("kgx", e.toString());
        }


    }

    private void clearDate() {
        if (xianBaos.size() != 0) {
            xianBaos.clear();
            adapterXianBao.notifyDataSetChanged();
        }
    }
}