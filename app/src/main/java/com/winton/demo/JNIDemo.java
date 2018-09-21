package com.winton.demo;

import android.util.Log;

/**
 * @author: winton
 * @time: 2018/9/20 下午3:39
 * @desc: 描述
 */
public class JNIDemo {

    static {
        System.loadLibrary("JNITest");
    }

    public static native String doNative(int i,String name);


    public void test(String str){
        Log.d("winton",str);
    }

    public static void doSomeThing(){
        Log.d("winton","doomesdasdas");
    }
}
