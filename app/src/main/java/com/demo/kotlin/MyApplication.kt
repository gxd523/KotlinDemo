package com.demo.kotlin

import android.app.Application

/**
 * Created by guoxiaodong on 2020/9/28 13:03
 */
class MyApplication : Application() {
    companion object {
        var instance: Application? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}