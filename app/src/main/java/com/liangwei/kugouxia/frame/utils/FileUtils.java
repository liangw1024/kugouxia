package com.liangwei.kugouxia.frame.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件操作集合类
 * Created by weibao on 2018/6/17.
 */

public class FileUtils {

    public static void writerTxt(String text, String target) throws IOException {
        File file = new File(target);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(text);
        bufferedWriter.flush();
        bufferedWriter.close();
    }
    public static String readTxtFormAssets(Context context, String path) throws IOException {
        InputStream inputStream = context.getAssets().open(path);
        int length = inputStream.available();
        byte[] buffer = new byte[length];
        inputStream.read(buffer);
        return new String(buffer);
    }
    public static String readTxt(String filePath){
        File file = new File(filePath);
        byte[] result =null;
        try {
            InputStream inputStream = new FileInputStream(file);
            int length = inputStream.available();
            result = new byte[length];
            inputStream.read(result);
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("input",e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("input",e.toString());
        }
        return new String(result);
    }
}
