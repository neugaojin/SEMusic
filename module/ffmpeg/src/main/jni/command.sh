#!/usr/bin/env bash
FFMPEG_EXT_PATH="$(pwd)"
echo ${FFMPEG_EXT_PATH}
NDK_PATH="/Users/gaojin/android/ndk/android-ndk"
echo ${NDK_PATH}
HOST_PLATFORM="darwin-x86_64"
echo ${HOST_PLATFORM}
ENABLED_DECODERS=(vorbis opus flac alac mp3 aac ac3 eac3)
echo ${ENABLED_DECODERS[*]}

#chmod +x build_ffmpeg.sh
#./build_ffmpeg.sh \
#  "${FFMPEG_EXT_PATH}" "${NDK_PATH}" "${HOST_PLATFORM}" "${ENABLED_DECODERS[@]}"

${NDK_PATH}/ndk-build APP_ABI="armeabi-v7a arm64-v8a x86 x86_64" -j4