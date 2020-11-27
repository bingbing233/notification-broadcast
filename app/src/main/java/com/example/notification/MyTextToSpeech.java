package com.example.notification;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class MyTextToSpeech implements TextToSpeech.OnInitListener {

    TextToSpeech textToSpeech;
    String content;
    public MyTextToSpeech(Context context,String content){

        this.content = content;
        textToSpeech = new TextToSpeech(context,this);
    }


    @Override
    public void onInit(int i) {
        if(i == textToSpeech.SUCCESS){
            //使用中文进行朗读
            textToSpeech.setLanguage(Locale.CHINESE);
            textToSpeech.setPitch(1.0f);
            textToSpeech.setSpeechRate(0.5f);
            textToSpeech.speak(content,TextToSpeech.QUEUE_FLUSH,null,null);
        }
    }

    void stop(){
        textToSpeech.stop();
        textToSpeech.shutdown();
        textToSpeech = null;
    }
}
