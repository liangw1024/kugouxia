package com.liangwei.kugouxia.frame;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import cn.jzvd.JZVideoPlayer;
import com.liangwei.kugouxia.R;

public class PlayerActivity  extends BaseActivity {
    @BindView(R.id.activity_player_jz_video) JZVideoPlayer videoView;
    @BindView(R.id.activity_player_toolbar) Toolbar toolbar;

    @Override
    public void onBackPressed() {
        if (videoView.backPress()) {

            return;
        }
        super.onBackPressed();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_player;
    }

    @Override
    public void mOncreate() {
        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("url");
        String title = bundle.getString("title");
        videoView.setUp(url, JZVideoPlayer.SCREEN_WINDOW_NORMAL, title);
        videoView.startButton.performClick();//
        // videoView.startFullscreen(this,JCVideoPlayerStandard.class,url,title);
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

    @Override
    protected void onPause() {
        super.onPause();
        videoView.releaseAllVideos();
    }


}