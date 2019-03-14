package com.liangwei.kugouxia.frame.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;

import java.io.IOException;

public class MediaUils {
    /**
     *
     * @param context
     * @param mName
     * @throws IOException
     */
    public void playMusicFormAssets(Context context,String mName) throws IOException {
        AssetManager assetManager = context.getAssets();
        AssetFileDescriptor assetFileDescriptor = assetManager.openFd(mName);
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),assetFileDescriptor.getStartOffset(),assetFileDescriptor.getLength());
        mediaPlayer.prepare();
        mediaPlayer.start();
    }
}
