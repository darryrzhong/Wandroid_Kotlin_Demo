package com.zthx.wandroid_kotlin_demo.core

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * 类名称: .java<p>
 * 类描述: <p>
 * Company: 江苏智体互享科技有限公司<p>
 * @author  darryrzhong
 * @since 2019/12/6 15:17
 */
 abstract class BaseFragment :Fragment() {

    protected lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val rootView = inflater.inflate(getLayoutRes(),container,false)
        initView(rootView,savedInstanceState)
        initData()
        return rootView
    }

    abstract fun initView(rootView: View?, savedInstanceState: Bundle?)

    abstract fun initData()

    abstract fun getLayoutRes(): Int
}