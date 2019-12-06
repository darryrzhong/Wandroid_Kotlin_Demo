package com.zthx.wandroid_kotlin_demo

import android.app.Application
import android.content.Context
import com.zthx.wandroid_kotlin_demo.utils.setup

/**
 * 类名称: .java<p>
 * 类描述: <p>
 * Company: 江苏智体互享科技有限公司<p>
 * @author  darryrzhong
 * @since 2019/12/6 15:59
 */
class App : Application() {

   companion object{
       private lateinit var mContext: Context

       private lateinit var instance: App

       fun getContext() : Context{
           return mContext.applicationContext
       }

       fun getInstance():App{
           return instance
       }

   }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        instance = this
        setup(this, 375F,0,0)
    }

}