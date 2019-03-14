package com.liangwei.kugouxia.ui.activity;

import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.liangwei.studio.net.HttpRequestUtils;
import com.liangwei.studio.net.INetCallback;
import com.tencent.qq.widget.QQToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.adapter.AdapterQqMeiHua;
import com.liangwei.kugouxia.beans.QqMeiHuaBean;
import com.liangwei.kugouxia.frame.BaseActivity;

public class QqMeiHuaActivity extends BaseActivity {
    @BindView(R.id.activity_mei_hua_toolbar) Toolbar toolbar;
    @BindView(R.id.activity_mei_hua_LRecyclerView) LRecyclerView lRecyclerView;
    int fenqu = 14;
    int once = 0;
    int twice = 19;

    //存放所有帖子
    private List<QqMeiHuaBean> datas = new ArrayList();
    private AdapterQqMeiHua adapter = null;
    @Override
    public int getContentView() {
        return R.layout.activity_qq_mei_hua;
    }

    @Override
    public void mOncreate() {
        initRecyclerView();
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
    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(QqMeiHuaActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        lRecyclerView.setLayoutManager(new LinearLayoutManager(QqMeiHuaActivity.this));
        lRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        adapter = new AdapterQqMeiHua(datas,QqMeiHuaActivity.this);
        final LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("headUrl",datas.get(position).getUserHeadUrl());
                intent.putExtra("name",datas.get(position).getUserName());
                intent.putExtra("title",datas.get(position).getTitle());
                intent.putExtra("content",datas.get(position).getContent());
                intent.putExtra("time",datas.get(position).getTime());
                intent.putStringArrayListExtra("images", (ArrayList<String>) datas.get(position).getImages());
                intent.setClass(QqMeiHuaActivity.this,QqMeihuaDetailActivity.class);
                startActivity(intent);
            }
        });
        lRecyclerView.setAdapter(lRecyclerViewAdapter);
        lRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryData(0);
            }
        });

        //加载更多
        lRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                queryData(1);
            }
        });

    }

    /**
     * 查询数据
     * @param type 0刷新 1加载更多
     */
    private void queryData(int type){
        String url = "http://spare.maibaapp.com/bbs/category/14/"+once+"/"+twice;
        if(type==0){
            clerData();
            HttpRequestUtils.getNeedUi(this,url, new INetCallback() {
                @Override
                public void success(String body) {
                    try {
                        parseData(body);
                        once = once+20;
                        twice = twice+20;
                    } catch (JSONException e) {
                        Log.e("kgxjson",e.toString());
                    }
                }

                @Override
                public void fail(Exception e, String detail) {
                    lRecyclerView.refreshComplete(0);
                    QQToast.makeText(getApplicationContext(),"刷新失败，错误码："+e.toString(), QQToast.setBackgroundColors.DEFAULT).show();
                }

            });
        }else if(type==1){
            HttpRequestUtils.getNeedUi(this,url, new INetCallback() {
                @Override
                public void success(String body) {

                    try {
                        parseData(body);
                        once = once+20;
                        twice = twice+20;
                    } catch (JSONException e) {
                        Log.e("kgxjson",e.toString());
                    }
                }

                @Override
                public void fail(Exception e, String detail) {
                    lRecyclerView.refreshComplete(0);
                    QQToast.makeText(getApplicationContext(),"加载失败，错误码："+e.toString(), QQToast.setBackgroundColors.DEFAULT).show();
                }



            });
        }
    }
    /**
     *解析json数据添加到datas 并且刷新
     */
    int numberOfPage = 0;
    private void parseData(String text) throws JSONException {
        JSONObject jsonObject = new JSONObject(text);
        //存放post的数组
        JSONObject jsonObjectData = jsonObject.getJSONObject("data");
        JSONArray posts = jsonObjectData.getJSONArray("topics");
        numberOfPage = posts.length();
        for(int i=0;i<posts.length();i++){
            QqMeiHuaBean data = new QqMeiHuaBean();
            JSONObject post = posts.getJSONObject(i);
            String thumb = post.getString("thumb");
            //存放Images的集合
            ArrayList<String> images =new ArrayList();
            //分割thumb 添加到images
            String[] mImages = thumb.split(",");
            for(String mImage:mImages){
                images.add(mImage);
            }
            String title = post.getString("title");
            String content = post.getString("content");
            String time = post.getString("timestampISO");
            //解析user info
            JSONObject user = post.getJSONObject("user");
            String userName = user.getString("username");
            String headUrl = user.getString("picture");
            data.setUserName(userName);
            data.setUserHeadUrl(headUrl);
            data.setTitle(title);
            data.setContent(content);
            data.setImages(images);
            data.setTime(time);
            datas.add(data);
        }
        lRecyclerView.refreshComplete(numberOfPage);
        Log.i("kgx","numberOfPage:"+numberOfPage);
        adapter.notifyDataSetChanged();

    }
    /**
     *清除datas数据
     */
    private void clerData(){
        if(datas.size()!=0){
            datas.clear();
            adapter.notifyDataSetChanged();
            once = 0;
            twice = 19;
        }

    }
}
