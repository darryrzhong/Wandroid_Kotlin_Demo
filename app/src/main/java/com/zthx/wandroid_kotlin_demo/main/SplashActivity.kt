package com.zthx.wandroid_kotlin_demo.main

import android.animation.Animator
import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.zthx.wandroid_kotlin_demo.MainActivity
import com.zthx.wandroid_kotlin_demo.R
import com.zthx.wandroid_kotlin_demo.core.BaseActivity
import com.zthx.wandroid_kotlin_demo.utils.gotoActivity

/**
 * 类名称: .java<p>
 * 类描述: <p>
 * Company: 江苏智体互享科技有限公司<p>
 * @author  darryrzhong
 * @since 2019/12/6 17:06
 */
class SplashActivity : BaseActivity() {

    private lateinit var logoLottieView: LottieAnimationView
    override fun initView() {
       logoLottieView = findViewById(R.id.lav_logo)
        logoLottieView.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(p0: Animator?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onAnimationEnd(p0: Animator?) {
                gotoMainActivity()
            }

            override fun onAnimationCancel(p0: Animator?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onAnimationStart(p0: Animator?) {

            }

        })
    }

    private fun gotoMainActivity() {
        gotoActivity(this,MainActivity().javaClass)
        finish()
    }

    override fun getLayoutRes(): Int {
       return R.layout.activity_splash
    }
}