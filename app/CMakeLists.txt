cmake_minimum_required(VERSION 3.4.1)
add_library( native-lib
        SHARED
        src/main/cpp/native-lib.cpp
        )
add_library( FfmpegMusic
        SHARED
        src/main/cpp/FfmpegMusic.cpp
        )

include_directories(libs/include)
set(DIR ../../../../libs)
MESSAGE("打印")
MESSAGE("路径 " ${DIR})
add_library(libavcodec
        SHARED
        IMPORTED)
set_target_properties(libavcodec
        PROPERTIES IMPORTED_LOCATION
        ${DIR}/armeabi-v7a/libavcodec.so)
add_library(libavdevice
        SHARED
        IMPORTED)
set_target_properties(libavdevice
        PROPERTIES IMPORTED_LOCATION
        ${DIR}/armeabi-v7a/libavdevice.so)
add_library(libavformat
        SHARED
        IMPORTED)
set_target_properties(libavformat
        PROPERTIES IMPORTED_LOCATION
        ${DIR}/armeabi-v7a/libavformat.so)
add_library(libavutil
        SHARED
        IMPORTED)
set_target_properties(libavutil
        PROPERTIES IMPORTED_LOCATION
        ${DIR}/armeabi-v7a/libavutil.so)
add_library(libswresample
        SHARED
        IMPORTED)
set_target_properties(libswresample
        PROPERTIES IMPORTED_LOCATION
        ${DIR}/armeabi-v7a/libswresample.so)
add_library(libswscale
        SHARED
        IMPORTED)
set_target_properties(libswscale
        PROPERTIES IMPORTED_LOCATION
        ${DIR}/armeabi-v7a/libswscale.so)
add_library(libavfilter
        SHARED
        IMPORTED)
set_target_properties(libavfilter
        PROPERTIES IMPORTED_LOCATION
        ${DIR}/armeabi-v7a/libavfilter.so)

find_library( log-lib
        log )
target_link_libraries( native-lib
        libavcodec
        libavdevice
        libavformat
        libavutil
        libswresample
        libswscale
        libavfilter
        FfmpegMusic
        -landroid # Add this.
        ${log-lib} )
