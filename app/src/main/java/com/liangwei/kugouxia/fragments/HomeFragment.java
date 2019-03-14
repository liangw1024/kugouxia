package com.liangwei.kugouxia.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liangwei.kugouxia.model.ModelParent;
import com.liangwei.kugouxia.ui.activity.classify.MakeCelebrityOnlinePictureActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.liangwei.kugouxia.R;
import com.liangwei.kugouxia.beans.BannerBean;
import com.liangwei.kugouxia.frame.AppConfig;
import com.liangwei.kugouxia.frame.BaseActivity;

import com.liangwei.kugouxia.frame.tbs.X5Activity;
import com.liangwei.kugouxia.frame.utils.QqUtils;
import com.liangwei.kugouxia.model.BannerModel;
import com.liangwei.kugouxia.model.IModelView;
import com.liangwei.kugouxia.ui.activity.QqMeiHuaActivity;
import com.liangwei.kugouxia.ui.activity.XianBaoActivity;
import com.liangwei.kugouxia.ui.activity.tools.CoolTranslateActivity;
import com.liangwei.kugouxia.ui.activity.tools.MakeSpecialTextActivity;

/**
 * 主页
 */
public class HomeFragment extends Fragment {
    public List<BannerBean> bannerBeans = new ArrayList<>();
    @BindView(R.id.fragment_home_relativeLayout_toolbar) RelativeLayout relativeLayout_toolbar;
    @BindView(R.id.fragment_home_textView_title) TextView tv_title;
    @BindView(R.id.fragment_home_banner) Banner banner;

    @BindView(R.id.fragment_home_textView_rightTitle) TextView tv_rightTItle;

    /*top right加群click*/
    @OnClick(R.id.fragment_home_textView_rightTitle) public void joinqqgroup() {
        QqUtils.joinQQGroupByKey(getActivity().getApplicationContext(),null);
    }

    /*简报click*/
    @OnClick(R.id.fragment_home_kgxcardview_xianbao) public void startXianBao() {
        startActivity(new Intent(getActivity(), XianBaoActivity.class));
    }

    /*QQ美化click*/
    @OnClick(R.id.fragment_home_kgxcardview_qqmeihua)public void startQqMeiHua() {//QQ美化社区
        startActivity(new Intent(getActivity(), QqMeiHuaActivity.class));
    }

    /*a cool translate*/
    @OnClick(R.id.fragment_home_kgxcardview_translate) public void translate() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), CoolTranslateActivity.class);
        startActivity(intent);

    }

    /*福利videoclick*/
    @OnClick(R.id.fragment_home_kgxcardview_yellowVideo) public void startyellowVideo() {
        startActivity(new Intent(getActivity(), MakeCelebrityOnlinePictureActivity.class));

    }

    /*特殊网名click*/
    @OnClick(R.id.fragment_home_kgxcardview_makeSpecialName) public void startParseAct() {//qq special name
        startActivity(new Intent(getActivity(), MakeSpecialTextActivity.class));
    }

    /*vip video click*/
    @OnClick(R.id.fragment_home_kgxcardview_vipVideo) public void openWeb() {//vip video
        Intent intent = new Intent();
        intent.putExtra("url", AppConfig.WebAddress.FREE_VIDEO);
        intent.setClass(getActivity(), X5Activity.class);
        startActivity(intent);
    }

    /*
     * 开启banner
     */
    private Handler bannerHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            banner.start();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        configBanner();


        return view;
    }

    /**
     * 配置banner
     */
    private void configBanner() {
       BannerModel.getInstance().request(getActivity(), BannerModel.url, new IModelView<BannerBean>() {
           @Override
           public void success(ArrayList<BannerBean> obj) {
               Log.d("size", "|||+" + obj.size());
               if (bannerBeans.size() != 0) {
                   bannerBeans = obj;
                   List<String> imgs = new ArrayList<>();
                   List<String> titles = new ArrayList<>();
                   imgs.clear();
                   titles.clear();
                   banner.setImageLoader(new GlideImageLoader(GlideImageLoader.TYPE_NET_IMG));
                   for (BannerBean data : obj) {
                       imgs.add(data.getImg());
                       titles.add(data.getTitle());
                   }
                   banner.setImages(imgs);
                   bannerHandler.sendMessage(new Message());
               } else {
                   bannerBeans = obj;
                   List<String> imgs = new ArrayList<>();
                   List<String> titles = new ArrayList<>();
                   banner.setImageLoader(new GlideImageLoader(GlideImageLoader.TYPE_NET_IMG));
                   for (BannerBean data : obj) {
                       imgs.add(data.getImg());
                       titles.add(data.getTitle());
                   }
                   banner.setImages(imgs);
                   bannerHandler.sendMessage(new Message());
               }

           }

           @Override
           public void fail(Exception e) {
               //请求失败
               List<Integer> intImgs = new ArrayList<>();
               intImgs.add(R.drawable.banner_painting_quality_update);
               banner.setImages(intImgs);
               banner.setImageLoader(new GlideImageLoader(GlideImageLoader.TYPE_DRAWALE));
               bannerHandler.sendMessage(new Message());
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
        banner.setIndicatorGravity(BannerConfig.CENTER);//指示器位置
        banner.setDelayTime(3000);
        banner.setBannerAnimation(Transformer.ForegroundToBackground);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(final int position) {
                BannerBean bannerBean = bannerBeans.get(position);
                BaseActivity.openUrl(getActivity(), bannerBean.getUrl());
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("kgx", "-------Resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("kgx", "-------Pause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("kgx", "-------Destroy ");
    }
}
