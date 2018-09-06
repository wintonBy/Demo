package com.winton.demo

import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.util.Log

/**
 * @author: winton
 * @time: 2018/9/5 下午5:51
 * @desc: 描述
 */
class ContentProviderActivity:BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addUser()
        queryUser()
        addUser()
        queryUser()

    }

    private fun addUser(){
        var uri =Uri.parse("content://com.winton.demo.provider/User")
        var values = ContentValues()
        values.put("name","zww")
        values.put("sex",1)
        var contentResolver = applicationContext.contentResolver
        contentResolver.insert(uri,values)
    }






    private fun queryUser(){
        var uri =Uri.parse("content://com.winton.demo.provider/User")
        var contentResolver = applicationContext.contentResolver
        val consor = contentResolver.query(uri,null,null,null,null)
        while(consor.moveToNext()){
            Log.d("winton","查到了："+consor.getString(1))

        }
        consor.close()

    }



}