package com.zthx.wandroid_kotlin_demo.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zthx.wandroid_kotlin_demo.core.mvp.BaseMVPFragment
import com.zthx.wandroid_kotlin_demo.core.mvp.IPresenter
import com.zthx.wandroid_kotlin_demo.core.mvp.IView

/**
 * 类名称: .java<p>
 * 类描述: <p>
 * Company: 江苏智体互享科技有限公司<p>
 * @author  darryrzhong
 * @since 2019/12/6 15:21
 */
abstract class BaseLazyFragment<in V :IView,P :IPresenter<in  V>> :BaseMVPFragment<V,P>() {

    /**
     * Fragment 中的View 是否创建完毕
     * */
    protected var  isViewCreated = false

    /**
     * Fragment 是否对用户可见
     * */
    protected var  isVisibled = false

    /**
     * Fragment 左右切换时,只在第一次显示时请求数据
     * */
    protected var isFirstLoad = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreated = true
        lazyLoad()
    }

    private fun lazyLoad() {
        if (isViewCreated && isVisibled && isFirstLoad){
            loadData()
            isFirstLoad = false
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser){
            isVisibled = true
            onVisible()
        }else{
            isVisibled = false
            onInVisible()
        }
    }

    private fun onVisible() {
        lazyLoad()
    }

    private fun onInVisible() {

    }

    protected  abstract fun loadData()

}