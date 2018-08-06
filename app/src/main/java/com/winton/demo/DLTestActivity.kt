package com.winton.demo

import android.os.Bundle
import android.view.View
import android.widget.Button

/**
 * @author: winton
 * @time: 2018/2/6 11:52
 * @package: com.winton.demo
 * @project: Demo
 * @mail:
 * @describe: 一句话描述
 */
class DLTestActivity :BaseActivity(){

    lateinit  var  button : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button = Button(this)
        button.text="加载插件"
        setContentView(button)
        initListener();
    }

    private fun initListener(){
        button.setOnClickListener(View.OnClickListener {
            
        })
    }

}