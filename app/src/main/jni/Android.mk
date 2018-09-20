LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := JNITest

LOCAL_SRC_FILES := 	com_winton_demo_JNIDemo.cpp com_winton_demo_JNIUtils.cpp

LOCAL_LDLIBS := -lm -llog
include $(BUILD_SHARED_LIBRARY)

