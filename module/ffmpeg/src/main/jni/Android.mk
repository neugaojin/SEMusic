//定义 LOCAL_PATH 变量
//此变量表示源文件在开发树中的位置。在上述命令中，构建系统提供的宏函数 my-dir 将返回当前目录（Android.mk 文件本身所在的目录）的路径。
LOCAL_PATH := $(call my-dir)

//在描述每个模块之前，必须声明（重新声明）此变量。
include $(CLEAR_VARS)
LOCAL_MODULE    := libavcodec
LOCAL_SRC_FILES := ffmpeg/android-libs/$(TARGET_ARCH_ABI)/$(LOCAL_MODULE).so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := libswresample
LOCAL_SRC_FILES := ffmpeg/android-libs/$(TARGET_ARCH_ABI)/$(LOCAL_MODULE).so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := libavutil
LOCAL_SRC_FILES := ffmpeg/android-libs/$(TARGET_ARCH_ABI)/$(LOCAL_MODULE).so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := ffmpeg
LOCAL_SRC_FILES := ffmpeg_jni.cc
LOCAL_C_INCLUDES := ffmpeg
LOCAL_LDLIBS := -Lffmpeg/android-libs/$(TARGET_ARCH_ABI) -llog
include $(BUILD_SHARED_LIBRARY)