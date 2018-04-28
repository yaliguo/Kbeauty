#include <jni.h>
#include <stdio.h>
#include <sys/types.h>
 #include <sys/stat.h>
 #include <fcntl.h>
#include "android/log.h"
#include "android/asset_manager.h"
#include "android/asset_manager_jni.h"
 #include <unistd.h>
 #include <string.h>
#include <sys/mman.h>
#include <sys/inotify.h>
JNIEXPORT jstring JNICALL
Java_phoenix_com_kbeauty_JinUtils_readText(JNIEnv *env, jclass type, jstring path_) {
    const char *path = (*env)->GetStringUTFChars(env, path_, 0);
    int fd  = -1;
    fd =   open(path,O_RDWR);
    char buf[10];
    if (-1 == fd)        // 有时候也写成： (fd < 0)
    {
        __android_log_print(ANDROID_LOG_INFO,"jni","文件打开错误\n");
    }
    else
    {
        __android_log_print(ANDROID_LOG_INFO,"jni","文件打开成功");
        read(fd,buf,10);
    }

    int  i =  inotify_init();
    if(i ==-1){
        __android_log_print(ANDROID_LOG_INFO,"jni","inotify 初始化失败");

    } else{
        __android_log_print(ANDROID_LOG_INFO,"jni","inotify 初始化成功");

        inotify_add_watch(i,path,IN_MODIFY);
        char event_buf[512];
        int ret;


        pid_t   t = fork();

        if(t<0){

        } else if(t>0){

            ret = read(i, event_buf, sizeof(event_buf));
            //阻塞操作
            __android_log_print(ANDROID_LOG_INFO,"jni","inotify 开始监测文件...");


            __android_log_print(ANDROID_LOG_INFO,"jni","inotify 检测文件变化");


        }



    }




    return (*env)->NewStringUTF(env,buf);
}