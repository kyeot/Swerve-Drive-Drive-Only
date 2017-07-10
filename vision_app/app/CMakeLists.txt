cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_VERBOSE_MAKEFILE on)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=gnu++11")

add_library( # Specifies the name of the library.
             JNIPart

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/jni/jni.c
             src/main/jni/image_processor.cpp)

include_directories(src/main/jni/)

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

add_library( lib_opencv
             SHARED IMPORTED )

set_target_properties(lib_opencv
                      PROPERTIES
                      IMPORTED_LOCATION
                      ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libopencv_java3.so)

target_link_libraries(# Specifies the target library.
                      JNIPart

                      # Links the target library to the log library
                      # included in the NDK.
                      $\{log-lib} )

target_link_libraries(JNIPart
                      android
                      log
                      EGL
                      GLESv2)

target_link_libraries(JNIPart
                      $\{log-lib}
                      lib_opencv)