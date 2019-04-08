package com.liangwei.kugouxia.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liangwei.kugouxia.adapter.AdScroolbarAdapter;

import com.liangwei.kugouxia.model.ModelParent;
import com.liangwei.studio.ui.AdScroolBar;
import com.liangwei.studio.ui.LwCardButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.beans.AdBean;
import com.liangwei.kugouxia.frame.AppConfig;
import com.liangwei.kugouxia.frame.BaseActivity;
import com.liangwei.kugouxia.frame.utils.QqUtils;
import com.liangwei.kugouxia.model.AdModel;
import com.liangwei.kugouxia.model.IModelView;
import com.liangwei.kugouxia.ui.activity.classify.OnlineCelebrityActivity;
import com.liangwei.kugouxia.ui.activity.classify.qqtools.QqToolActivity;
import com.liangwei.kugouxia.ui.activity.tools.CoolTranslateActivity;
import com.liangwei.kugouxia.ui.activity.tools.MakeAcrosticActivity;
import com.liangwei.kugouxia.ui.activity.tools.MakeHeadActivity;


public class ToolsFragment extends Fragment {
    @BindView(R.id.fragment_tools_cardbtnOnline_celebrity) LwCardButton btn_onlineCelebrity;
    @BindView(R.id.fragment_tools_adbar) AdScroolBar adbar;
    @OnClick(R.id.fragment_tools_tv_contact) public void contact(){
        QqUtils.forceChat(getActivity(),AppConfig.qq);
    }
    @OnClick(R.id.fragment_tools_iv_contact) public void iv_contact(){
        contact();
    }
    //Online celebrity tool
    @OnClick(R.id.fragment_tools_cardbtnOnline_celebrity) public void onlineCelebrity(){
        startActivity(new Intent(getActivity(), OnlineCelebrityActivity.class));
    }
    //qq tool
    @OnClick(R.id.fragment_tools_btn_qqtool) public void tools(){
        startActivity(new Intent(getActivity(), QqToolActivity.class));
    }

    //make web
    @OnClick(R.id.fragment_tools_card_web) public void web(){
        BaseActivity.openUrl(getActivity(),AppConfig.WebAddress.WEBMAKE);
    }

    //qq好友恢复
    @OnClick(R.id.fragment_tools_btn_zhuangbi) public void zhuanbi(){
        BaseActivity.openUrl(getActivity(),AppConfig.WebAddress.ZHUANGBI);
    }
    //免费小说
    @OnClick(R.id.fragment_tools_btn_freeNovel) public void freeNovel(){
        BaseActivity.openUrl(getActivity(),AppConfig.WebAddress.FREE_NOVEL);
    }
    //免费漫画
    @OnClick(R.id.fragment_tools_btn_freeCartoon) public void freeCartoon(){
        BaseActivity.openUrl(getActivity(),AppConfig.WebAddress.FREE_CARDOON);
    }
    //a cool translate
    @OnClick(R.id.fragment_tools_card_translate) public void translate(){
        startActivity(new Intent(getActivity(), CoolTranslateActivity.class));
    }
    //safe hat
    @OnClick(R.id.fragment_tools_card_hat) public void safeHat(){
        startActivity(new Intent(getActivity(), MakeHeadActivity.class));
    }
    //acrostic
    @OnClick(R.id.fragment_tools_card_acrostic) public void acrostic(){
        startActivity(new Intent(getActivity(), MakeAcrosticActivity.class));
    }

    ArrayList<AdBean> adBeans = new ArrayList<>();

    public ToolsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tools, container, false);
        ButterKnife.bind(this,view);
        AdModel.getInstance().request(getActivity(), AdModel.adUrl, new IModelView<AdBean>() {
            @Override
            public void success(ArrayList<AdBean> obj) {
                adBeans.addAll(obj);
                updateAd();
                Log.d("ad", adBeans.size() + "");
            }

            @Override
            public void fail(Exception e) {
                adBeans.add(new AdBean("加载失败", "错误码----->" + e.toString(), "url", "url"));
                adBeans.add(new AdBean("加载失败", "客服qq:----->" + "1947504030", "url", "url"));
                updateAd();
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

            }
        });



        return view;
    }
    private void updateAd(){
        AdScroolbarAdapter adScroolbarAdapter = new AdScroolbarAdapter(getActivity(), adBeans);
        adScroolbarAdapter.setItemOnclickListener(new AdScroolbarAdapter.ItemOnclickListener() {
            @Override
            public void onClick(int position, AdBean data) {
                BaseActivity.openUrl(getActivity(), (String) data.getClickurl());
            }
        });
        adbar.setAdapter(adScroolbarAdapter);
        adScroolbarAdapter.notifyDataSetChanged();
    }
}
