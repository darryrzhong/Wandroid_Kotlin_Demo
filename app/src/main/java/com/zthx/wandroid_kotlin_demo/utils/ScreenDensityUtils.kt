package com.zthx.wandroid_kotlin_demo.utils

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi

/**
 * 类名称: .java<p>
 * 类描述: <p>
 * Company: 江苏智体互享科技有限公司<p>
 * @author  darryrzhong
 * @since 2019/12/6 18:17
 */


/*
     * 1.先在application中使用setup()方法初始化一下
     * 2.手动在Activity中调用match()方法做适配，必须在setContentView()之前
     * 3.建议使用dp做宽度适配，大多数时候宽度适配才是主流需要
     * 4.个人觉得在写布局的时候，可以多用dp，如果是使用px，建议转化成dp
     * 5.入侵性很低，不需要改动原来的代码
     */


/**
 * 屏幕适配的基准
 */
private val MATCH_BASE_WIDTH = 0
private val MATCH_BASE_HEIGHT = 1
/**
 * 适配单位
 */
private val MATCH_UNIT_DP = 0
private val MATCH_UNIT_PT = 1

// 适配信息
private var sMatchInfo: MatchInfo? = null
// Activity 的生命周期监测
private var mActivityLifecycleCallback: Application.ActivityLifecycleCallbacks? = null


/**
 * 初始化
 *
 * @param application 需要在application中初始化
 */
fun setup(@NonNull application: Application, designSize: Float, matchBase: Int, matchUnit: Int) {

    /*
        //获取屏幕分辨率信息的三种方法
        //第一种
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = activity.getWindowManager().getDefaultDisplay();
        display.getMetrics(metrics);
        //第二种
        DisplayMetrics metrics= activity.getResources().getDisplayMetrics();
        //第三种
        Resources.getSystem().getDisplayMetrics();
        */

    //注意这个是获取系统的displayMetrics
    val displayMetrics = application.resources.displayMetrics
    if (sMatchInfo == null) {
        // 记录系统的原始值
        sMatchInfo = MatchInfo()
        sMatchInfo!!.screenWidth = displayMetrics.widthPixels
        sMatchInfo!!.screenHeight = displayMetrics.heightPixels
        sMatchInfo!!.appDensity = displayMetrics.density
        sMatchInfo!!.appDensityDpi = displayMetrics.densityDpi.toFloat()
        sMatchInfo!!.appScaledDensity = displayMetrics.scaledDensity
        sMatchInfo!!.appXdpi = displayMetrics.xdpi
    }
    // 添加字体变化的监听
    // 调用 Application#registerComponentCallbacks 注册下 onConfigurationChanged 监听即可。
    application.registerComponentCallbacks(object : ComponentCallbacks {
        override fun onConfigurationChanged(newConfig: Configuration) {
            // 字体改变后,将 appScaledDensity 重新赋值
            if (newConfig != null && newConfig.fontScale > 0) {
                val scaledDensity = displayMetrics.scaledDensity
                sMatchInfo!!.appScaledDensity = scaledDensity
            }
        }

        override fun onLowMemory() {

        }
    })
    register(application,designSize,matchBase,matchUnit)
}

/**
 * 在 application 中全局激活适配（也可单独使用 match() 方法在指定页面中配置适配）
 */
@RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
fun register(@NonNull application: Application, designSize: Float, matchBase: Int, matchUnit: Int) {
    if (mActivityLifecycleCallback == null) {
        mActivityLifecycleCallback = object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                if (activity != null) {
                    match(activity, designSize, matchBase, matchUnit)
                }
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }
        }
        application.registerActivityLifecycleCallbacks(mActivityLifecycleCallback)
    }
}

/**
 * 全局取消所有的适配
 */
@RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
fun unregister(@NonNull application: Application, @NonNull vararg matchUnit: Int) {
    if (mActivityLifecycleCallback != null) {
        application.unregisterActivityLifecycleCallbacks(mActivityLifecycleCallback)
        mActivityLifecycleCallback = null
    }
    for (unit in matchUnit) {
        cancelMatch(application, unit)
    }
}


/**
 * 适配屏幕（放在 Activity 的 setContentView() 之前执行）
 *
 * @param context    上下文
 * @param designSize 设计图的尺寸
 */
fun match(@NonNull context: Context, designSize: Float) {
    match(context, designSize, MATCH_BASE_WIDTH, MATCH_UNIT_DP)
}


/**
 * 适配屏幕（放在 Activity 的 setContentView() 之前执行）
 *
 * @param context    上下文
 * @param designSize 设计图的尺寸
 * @param matchBase  适配基准
 */
fun match(@NonNull context: Context, designSize: Float, matchBase: Int) {
    match(context, designSize, matchBase, MATCH_UNIT_DP)
}

/**
 * 适配屏幕（放在 Activity 的 setContentView() 之前执行）
 *
 * @param context    上下文
 * @param designSize 设计图的尺寸
 * @param matchBase  适配基准
 * @param matchUnit  使用的适配单位
 */
private fun match(@NonNull context: Context, designSize: Float, matchBase: Int, matchUnit: Int) {
    if (designSize == 0f) {
        throw UnsupportedOperationException("The designSize cannot be equal to 0")
    }
    if (matchUnit == MATCH_UNIT_DP) {
        matchByDP(context, designSize, matchBase)
    } else if (matchUnit == MATCH_UNIT_PT) {
        matchByPT(context, designSize, matchBase)
    }
}

/**
 * 重置适配信息，取消适配
 */
fun cancelMatch(@NonNull context: Context) {
    cancelMatch(context, MATCH_UNIT_DP)
    cancelMatch(context, MATCH_UNIT_PT)
}

/**
 * 重置适配信息，取消适配
 *
 * @param context   上下文
 * @param matchUnit 需要取消适配的单位
 */
private fun cancelMatch(@NonNull context: Context, matchUnit: Int) {
    if (sMatchInfo != null) {
        val displayMetrics = context.resources.displayMetrics
        if (matchUnit == MATCH_UNIT_DP) {
            if (displayMetrics.density != sMatchInfo!!.appDensity) {
                displayMetrics.density = sMatchInfo!!.appDensity
            }
            if (displayMetrics.densityDpi.toFloat() != sMatchInfo!!.appDensityDpi) {
                displayMetrics.densityDpi = sMatchInfo!!.appDensityDpi.toInt()
            }
            if (displayMetrics.scaledDensity != sMatchInfo!!.appScaledDensity) {
                displayMetrics.scaledDensity = sMatchInfo!!.appScaledDensity
            }
        } else if (matchUnit == MATCH_UNIT_PT) {
            if (displayMetrics.xdpi != sMatchInfo!!.appXdpi) {
                displayMetrics.xdpi = sMatchInfo!!.appXdpi
            }
        }
    }
}


fun getMatchInfo(): MatchInfo? {
    return sMatchInfo
}


/**
 * 使用 dp 作为适配单位（适合在新项目中使用，在老项目中使用会对原来既有的 dp 值产生影响）
 * <br></br>
 *
 * dp 与 px 之间的换算:
 *  *  px = density * dp
 *  *  density = dpi / 160
 *  *  px = dp * (dpi / 160)
 *
 *
 * @param context    上下文
 * @param designSize 设计图的宽/高（单位: dp）
 * @param base       适配基准
 */
private fun matchByDP(@NonNull context: Context, designSize: Float, base: Int) {
    val targetDensity: Float
    if (base == MATCH_BASE_WIDTH) {
        targetDensity = sMatchInfo!!.screenWidth * 1f / designSize
    } else if (base == MATCH_BASE_HEIGHT) {
        targetDensity = sMatchInfo!!.screenHeight * 1f / designSize
    } else {
        targetDensity = sMatchInfo!!.screenWidth * 1f / designSize
    }
    val targetDensityDpi = (targetDensity * 160).toInt()
    val targetScaledDensity =
        targetDensity * (sMatchInfo!!.appScaledDensity / sMatchInfo!!.appDensity)
    val displayMetrics = context.resources.displayMetrics
    displayMetrics.density = targetDensity
    displayMetrics.densityDpi = targetDensityDpi
    displayMetrics.scaledDensity = targetScaledDensity
}


/**
 * 使用 pt 作为适配单位（因为 pt 比较冷门，新老项目皆适合使用；也可作为 dp 适配的补充，
 * 在需要同时适配宽度和高度时，使用 pt 来适配 dp 未适配的宽度或高度）
 * <br></br>
 *
 *  pt 转 px 算法: pt * metrics.xdpi * (1.0f/72)
 *
 * @param context    上下文
 * @param designSize 设计图的宽/高（单位: pt）
 * @param base       适配基准
 */
private fun matchByPT(@NonNull context: Context, designSize: Float, base: Int) {
    val targetXdpi: Float
    if (base == MATCH_BASE_WIDTH) {
        targetXdpi = sMatchInfo!!.screenWidth * 72f / designSize
    } else if (base == MATCH_BASE_HEIGHT) {
        targetXdpi = sMatchInfo!!.screenHeight * 72f / designSize
    } else {
        targetXdpi = sMatchInfo!!.screenWidth * 72f / designSize
    }
    val displayMetrics = context.resources.displayMetrics
    displayMetrics.xdpi = targetXdpi
}

/**
 * 适配信息
 */
class MatchInfo {

    internal var screenWidth: Int = 0
    internal var screenHeight: Int = 0
    internal var appDensity: Float = 0.toFloat()
    internal var appDensityDpi: Float = 0.toFloat()
    internal var appScaledDensity: Float = 0.toFloat()
    internal var appXdpi: Float = 0.toFloat()
}