package com.winton.demo;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.winton.demo.BaseActivity;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Writer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by winton on 2017/7/20.
 */

public class KeyCodeToPyCodeActivity extends BaseActivity {

    @BindView(R.id.tv_code)
    TextView mTVCode;
    StringBuffer stringBuffer;
    boolean update = true;

    private static final int UPDATE_CODE = 0x0001;
    Handler mUIHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_CODE:
                    mTVCode.setText(stringBuffer.toString());
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keycode_py);
        ButterKnife.bind(this);
        initData();
    }
    private void initData(){
        stringBuffer = new StringBuffer();
        new Thread(new UpdateCodeRunnable()).start();
        writeCodeHead();
    }

    @OnClick(R.id.bt_save)
    public void clickSave(View view){
        saveCode();
        this.finish();
    }

    /**
     * 保存代码
     */
    private void saveCode(){
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/code/";
        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.mkdir();
        }
        File file = new File(fileDir.getAbsolutePath()+"/"+System.currentTimeMillis()+"keycode.py");
        if(!file.exists()){
            try {
                file.createNewFile();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(stringBuffer.toString().getBytes());

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                fos.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        update = false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        writeKeyCode(KeyEvent.keyCodeToString(keyCode));
        return true;
    }

    private void writeCodeHead(){
        stringBuffer.append("from com.android.monkeyrunner import MonkeyRunner, MonkeyDevice\n");
        stringBuffer.append("device = MonkeyRunner.waitForConnection()\n");
    }
    private void writeKeyCode(String code){
        stringBuffer.append("device.press('"+code+"', MonkeyDevice.DOWN_AND_UP)\n");
        stringBuffer.append("time.sleep(0.5)\n");
    }


    class UpdateCodeRunnable implements Runnable{

        @Override
        public void run() {
            while (update){
                try {
                    Thread.sleep(20);
                    mUIHandler.sendEmptyMessage(UPDATE_CODE);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
