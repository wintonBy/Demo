package com.winton.demo.launchermode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.winton.demo.BaseActivity;
import com.winton.demo.R;

import java.net.Inet4Address;

/**
 * @author: winton
 * @time: 2018/5/6 17:05
 * @package: com.winton.demo.launchermode
 * @project: Demo
 * @mail:
 * @describe: 一句话描述
 */
public class ModeListActivty extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mode_list);
        initListener();
    }
    void initListener(){
        findViewById(R.id.bt_standard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModeListActivty.this,StandardActivity.class);
                startActivity(intent);
                startActivity(intent);
            }
        });
    }


}
