package com.zthx.wandroid_kotlin_demo.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle

/**
 * 类名称: .java<p>
 * 类描述: <p>
 * Company: 江苏智体互享科技有限公司<p>
 * @author  darryrzhong
 * @since 2019/12/6 17:44
 */

fun gotoActivity(activity: Activity,clazz: Class<Any>){
    val intent = Intent(activity,clazz)
    activity.startActivity(intent)
}

fun gotoActivity(activity: Activity, clazz: Class<Any>, bundle: Bundle) {
    val intent = Intent(activity, clazz)
    intent.putExtras(bundle)
    activity.startActivity(intent)
}