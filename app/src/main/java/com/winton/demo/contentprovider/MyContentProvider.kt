package com.winton.demo.contentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.winton.demo.db.DBHelper

/**
 * @author: winton
 * @time: 2018/9/5 下午3:14
 * @desc: 描述
 */

class MyContentProvider:ContentProvider(){

    lateinit var dbHelper: DBHelper

    //创建一个UriMatch Object
    companion object {
        val sUriMatch:UriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        const val ONE= 1
        const val MORE = 2
        const val authority = "com.winton.demo.provider"

    }

    override fun onCreate(): Boolean {
        dbHelper = DBHelper(context)
        return true
    }

    init {
        sUriMatch.addURI(authority,"User", ONE)
    }

    override fun insert(p0: Uri?, p1: ContentValues?): Uri {
        val type = sUriMatch.match(p0)
        val db = dbHelper.writableDatabase
        var id:Long = -1L
        var resultUri = ""
        when(type){
            ONE -> {
                id = db.insert("User","",p1)
                resultUri = "content://$authority/User/$id"
            }

        }
        return Uri.parse(resultUri)
    }

    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor {
        val db = dbHelper.writableDatabase
        return db.query("User",projection,selection,selectionArgs,null,null,sortOrder)
    }

    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = dbHelper.writableDatabase
        return db.update("User",values,selection,selectionArgs)
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = dbHelper.writableDatabase
        return db.delete("User",selection,selectionArgs)
    }

    override fun getType(uri: Uri?): String {

        val code = sUriMatch.match(uri)
        when(code){
            ONE ->{
                return "vnd.android.cursor.item/vnd.$authority"
            }
            MORE ->{
                return "vnd.android.cursor.dir/vnd.$authority"
            }
        }
        return "vnd.android.cursor.item/vnd.$authority"

    }
}