package com.zthx.wandroid_kotlin_demo.core.mvp

/**
 * 类名称: .java<p>
 * 类描述: <p>
 * Company: 江苏智体互享科技有限公司<p>
 * @author  darryrzhong
 * @since 2019/12/6 15:03
 */
interface IPresenter<V : IView>{

    fun attachView(view: V)

    fun detachView()

    fun isViewAtached():Boolean

    fun getView():V?
}