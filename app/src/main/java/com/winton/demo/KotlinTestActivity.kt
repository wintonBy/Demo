package com.winton.demo

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.winton.demo.BaseActivity
import com.winton.demo.R
import com.winton.demo.adapter.BaseAdapter
import kotlinx.android.synthetic.main.activity_kotlin.*

/**
 * @author: winton
 * @time: 2018/1/20 23:32
 * @package: com.winton.demo.widget
 * @project: Demo
 * @mail:
 * @describe:kotlin学习
 */
class KotlinTestActivity : BaseActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
    }
}