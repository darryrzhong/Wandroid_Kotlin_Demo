package com.zthx.wandroid_kotlin_demo.core

/**
 * 类名称: .java<p>
 * 类描述: <p>
 * Company: 江苏智体互享科技有限公司<p>
 * @author  darryrzhong
 * @since 2019/12/6 15:49
 */
data class BaseResponse<T>(
    var data: T?,
    var  results: T?,
    val errorMsg: String? =null,
    var  errorCode:Int? = -1,
    var error: Boolean? = true

)