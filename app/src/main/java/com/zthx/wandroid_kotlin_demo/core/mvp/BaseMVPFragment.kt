package com.zthx.wandroid_kotlin_demo.core.mvp

import com.zthx.wandroid_kotlin_demo.core.BaseFragment

/**
 * 类名称: .java<p>
 * 类描述: <p>
 * Company: 江苏智体互享科技有限公司<p>
 * @author  darryrzhong
 * @since 2019/12/6 15:26
 */
abstract class BaseMVPFragment<in V :IView,P : IPresenter<in V>> : BaseFragment(),IView {
    protected lateinit var presenter: P

    override fun initData() {
        presenter = createPresenter()
        presenter.attachView(this as V)
    }

    abstract fun createPresenter(): P

}