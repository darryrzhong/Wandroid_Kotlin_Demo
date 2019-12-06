package com.zthx.wandroid_kotlin_demo.core.mvp

import com.zthx.wandroid_kotlin_demo.core.BaseActivity

/**
 * 类名称: .java<p>
 * 类描述: <p>
 * Company: 江苏智体互享科技有限公司<p>
 * @author  darryrzhong
 * @since 2019/12/6 15:39
 */
abstract class BaseMVPActivity<in V : IView,P : IPresenter<in  V>> : BaseActivity(),IView {
    protected lateinit var presenter: P

    override fun initData() {
        super.initData()
        presenter = createPresenter()
        presenter.attachView(this as V)
    }

    abstract fun createPresenter(): P
}