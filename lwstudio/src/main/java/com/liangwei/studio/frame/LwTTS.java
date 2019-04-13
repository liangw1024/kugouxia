package com.liangwei.studio.frame;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

/**
 * 封装的text to speech
 */
public class LwTTS {

    private static LwTTS single;
    public static TextToSpeech textToSpeech;
    public Context context;
    public Locale language;
    public LwTTS(Context context,Locale language) {
        this.context = context;
        this.language = language;
        initEngine(context,language);
    }

    public static LwTTS getInstance(final Context context, final Locale language){
        if(textToSpeech==null){
            single = new LwTTS(context,language);
        }
        return single;
    }
    /**
     * 用来初始化TextToSpeech引擎
     * status:SUCCESS或ERROR这2个值
     * setLanguage设置语言，帮助文档里面写了有22种
     * TextToSpeech.LANG_MISSING_DATA：表示语言的数据丢失。
     * TextToSpeech.LANG_NOT_SUPPORTED:不支持
     */
    private void initEngine(final Context context, final Locale language){
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                switch (status) {
                    case TextToSpeech.SUCCESS:
                        int result = textToSpeech.setLanguage(language);
                        if(result==TextToSpeech.LANG_MISSING_DATA){
                            Toast.makeText(context,"错误:"+"missing language data",Toast.LENGTH_SHORT).show();
                        }else if (result==TextToSpeech.LANG_NOT_SUPPORTED){
                            Toast.makeText(context,"错误:"+"system not supported",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case TextToSpeech.ERROR:
                        Toast.makeText(context,"错误:"+"system not speech engine",Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });
    }
    public  void read(String content) {
        if(!textToSpeech.isSpeaking()){
            // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
            textToSpeech.setPitch(2.0f);
            //设定语速 ，默认1.0正常语速
            textToSpeech.setSpeechRate(1.0f);
            //朗读，注意这里三个参数的added in API level 4   四个参数的added in API level 21
            textToSpeech.speak(content, TextToSpeech.QUEUE_FLUSH,null);

        }else{
            Toast.makeText(context,"请勿重复播报",Toast.LENGTH_SHORT).show();
        }
    }
    public void closeTTS(){
        textToSpeech.stop();
        textToSpeech.shutdown();
    }

}

