//
// Created by 赵文文 on 2018/9/20.
//

# include "com_winton_demo_JNIDemo.h"

#include <android/log.h>
#define LOG_TAG  "wintonC"
#define LOGD(...)  ((void)__android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__))



//JNIEXPORT jstring JNICALL Java_com_winton_demo_JNIDemo_doNative
//        (JNIEnv * env, jobject){
//
//    LOGD("jni log");
//    return (*env).NewStringUTF("demo");
//  }

JNIEXPORT jstring JNICALL Java_com_winton_demo_JNIDemo_doNative
        (JNIEnv * env, jclass, jint, jstring){

    LOGD("comming");
    jclass  cls_JNIDemo = env->FindClass("com/winton/demo/JNIDemo");

    jmethodID met_java = env->GetMethodID(cls_JNIDemo,"test","(Ljava/lang/String;)V");

    jmethodID met_do = env->GetStaticMethodID(cls_JNIDemo,"doSomeThing","()V");

    env->CallStaticVoidMethod(cls_JNIDemo,met_do,NULL);


    LOGD("method");
    jmethodID mth_construct = env->GetMethodID(cls_JNIDemo,"<init>","()V");

    LOGD("contrst");

    jobject obj_JNIDemo = env->NewObject(cls_JNIDemo,mth_construct,NULL);
    LOGD("object");

    jstring data = env->NewStringUTF("called by jni");

    if(met_java != NULL && data != NULL){
        env->CallVoidMethod(obj_JNIDemo,met_java,data);
        env->DeleteLocalRef(data);
    }
    env->DeleteLocalRef(obj_JNIDemo);
    LOGD("release");
    return (*env).NewStringUTF("demo java be call ok");
}

