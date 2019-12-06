package com.zthx.wandroid_kotlin_demo.core

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * 类名称: .java<p>
 * 类描述: <p>
 * Company: 江苏智体互享科技有限公司<p>
 * @author  darryrzhong
 * @since 2019/12/6 15:15
 */
abstract class BaseActivity : AppCompatActivity(){
    protected lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())
        mContext = this
        initView()
        initData()
    }

    open fun initData() {

    }

    abstract fun initView()



    abstract fun getLayoutRes(): Int

    override fun onDestroy() {
        super.onDestroy()
    }


}