package com.zthx.wandroid_kotlin_demo.core.mvp

import java.lang.ref.WeakReference

/**
 * 类名称: .java<p>
 * 类描述: <p>
 * Company: 江苏智体互享科技有限公司<p>
 * @author  darryrzhong
 * @since 2019/12/6 15:08
 */
open class BasePresenter<V : IView> : IPresenter<V> {
    private lateinit var viewReference: WeakReference<V>



    override fun attachView(view: V) {
       viewReference = WeakReference(view)
    }

    override fun detachView() {
       viewReference.clear()
    }

    override fun isViewAtached(): Boolean {
        return viewReference.get() != null
    }

    override fun getView(): V? {
        return viewReference.get()
    }
}