package com.winton.demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.Nullable;

import com.winton.demo.widget.CustomProgressBar;

/**
 * Created by winton on 2017/3/21.
 */

public class WidgetDisplayActivity extends BaseActivity {

    private CustomProgressBar mCPB;
    private Handler mUIHanldr = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UIEvent.UPDATE_PROGRESS:
                    int progress = (int)msg.obj;
                    mCPB.setProgress(progress);
                    break;
                case UIEvent.UPDATE_LOAD_STATE:
                    mCPB.setLoading((boolean)msg.obj);
                    break;
            }
        }
    };
    static class UIEvent{
        static final int UPDATE_PROGRESS = 0x0001;
        static final int UPDATE_LOAD_STATE = 0x0002;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);
        mCPB = (CustomProgressBar)findViewById(R.id.cpb);
        new Thread(new Runnable() {
            @Override
            public void run() {
               int i=-10;
                while (i<=100){
                    if(i==-10){
                        Message msg = mUIHanldr.obtainMessage();
                        msg.what = UIEvent.UPDATE_LOAD_STATE;
                        msg.obj = true;
                        mUIHanldr.sendMessage(msg);
                    }else if(i==0){
                        Message msg = mUIHanldr.obtainMessage();
                        msg.what = UIEvent.UPDATE_LOAD_STATE;
                        msg.obj = false;
                        mUIHanldr.sendMessage(msg);
                    }else {
                        Message msg = mUIHanldr.obtainMessage();
                        msg.what = UIEvent.UPDATE_PROGRESS;
                        msg.obj = i;
                        mUIHanldr.sendMessage(msg);
                    }
                    i++;
                    try {
                        Thread.sleep(500);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
