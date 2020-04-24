LOCAL_PATH := $(call my-dir)

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
LOCAL_SHARED_LIBRARIES := libavcodec libswresample libavutil
LOCAL_LDLIBS := -Lffmpeg/android-libs/$(TARGET_ARCH_ABI) -llog
include $(BUILD_SHARED_LIBRARY)
