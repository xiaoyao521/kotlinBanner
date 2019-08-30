package com.xiaoyao.banner

import android.app.Application
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration

/**
 *@作者 luckly
 *
 *@创建日期 2019/8/29 20:01
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //创建全局的配置来初始化ImageLoader
        val configuration = ImageLoaderConfiguration.createDefault(this)
        ImageLoader.getInstance().init(configuration)
    }
}