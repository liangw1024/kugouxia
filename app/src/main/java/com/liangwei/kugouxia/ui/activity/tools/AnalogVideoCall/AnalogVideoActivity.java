package com.liangwei.kugouxia.ui.activity.tools.AnalogVideoCall;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liangwei.kugouxia.beans.AnalogVideoBean;
import com.liangwei.studio.net.HttpRequestUtils;
import com.liangwei.studio.net.INetCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.adapter.AnalogAdapter;
import com.liangwei.kugouxia.frame.BaseActivity;
import com.liangwei.kugouxia.frame.loading.WbLoadingDialog;

public class AnalogVideoActivity extends BaseActivity implements Serializable{
    @BindView(R.id.activity_analog_video_recyclerView)
    RecyclerView recyclerView;
    public AnalogAdapter analogAdapter = null;
    public List<Object> datas  = new ArrayList<>();
    @Override
    public int getContentView() {
        return R.layout.activity_analog_video;
    }

    @Override
    public void mOncreate() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        analogAdapter = new AnalogAdapter(this,datas,R.layout.item_analog_video);
        recyclerView.setAdapter(analogAdapter);
        queryData();

    }
    private void queryData(){
        final WbLoadingDialog wbLoadingDialog = new WbLoadingDialog(this,"加载中",false);
        HttpRequestUtils.postNeedUi(this,"http://kgx.luaapp.cn/kugou/analogVideoData.php", new INetCallback() {
            @Override
            public void success(String body) {
                parseDataToDatas(body);
                wbLoadingDialog.dismiss();
            }

            @Override
            public void fail(Exception e, String detail) {
                wbLoadingDialog.dismiss();
            }


        });
    }
    private void parseDataToDatas(String body){
            JSONArray jsonArray = JSONArray.parseArray(body);
            for (int i=0;i<jsonArray.size();i++) {
                AnalogVideoBean analogVideo = new AnalogVideoBean();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                analogVideo.setTitle(jsonObject.getString("title"));
                analogVideo.setImgUrl(jsonObject.getString("imgUrl"));
                analogVideo.setVideoUrl(jsonObject.getString("videoUrl"));
                datas.add(analogVideo);
            }
            analogAdapter.notifyDataSetChanged();
    }
    @Override
    public void initToolbar() {

    }

}

