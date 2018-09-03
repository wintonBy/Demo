package com.winton.demo;

/**
 * @author: winton
 * @time: 2018/8/31 下午8:22
 * @desc: 描述
 */
public class JNIUtils {

    static{
        System.loadLibrary("JNITest");
    }

    public native String getStringFromNative();

    public native String getStringFromC();
}
