package com.winton.demo.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * @author: winton
 * @time: 2018/9/5 下午3:48
 * @desc: 描述
 */
class DBHelper:SQLiteOpenHelper {
    companion object {

        const val name = "com.winton.demo"
        const val version = 1
    }

    constructor(context: Context?):super(context, name, null, version)


    override fun onCreate(p0: SQLiteDatabase?) {
        // 只能支持基本数据类型:varchar int long float boolean text blob clob
        var createUserTable = "CREATE TABLE User(id integer primary key autoincrement,name varchar(64),sex int(1))"
        p0?.execSQL(createUserTable)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}