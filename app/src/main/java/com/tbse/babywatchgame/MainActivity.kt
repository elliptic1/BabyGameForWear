package com.tbse.babywatchgame

import android.content.Context
import android.os.*
import android.support.wearable.activity.WearableActivity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : WearableActivity() {

    private var vibrationEffect: VibrationEffect = VibrationEffect.createOneShot(1000, 30)
    private lateinit var slideAnimation: Animation
    private lateinit var slideAnimationListener: Animation.AnimationListener
    private lateinit var fallAnimation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enables Always-on
        setAmbientEnabled()

        slideAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.slide)
        fallAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.fall)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)


        slideAnimationListener = object: Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) { }
            override fun onAnimationEnd(animation: Animation?) {
                balloonImageView.startAnimation(animation)
            }
            override fun onAnimationStart(animation: Animation?) { }
        }

        slideAnimation.setAnimationListener(slideAnimationListener)

        balloonImageView.startAnimation(slideAnimation)
    }

    fun balloonTouch(view: View) {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(vibrationEffect)
        balloonWrapper.visibility = View.GONE
        explodeWrapper.visibility = View.VISIBLE
        explodeImageView.startAnimation(fallAnimation)
        Handler(Looper.getMainLooper()).postDelayed({
            explodeWrapper.visibility = View.GONE
            balloonWrapper.visibility = View.VISIBLE
            balloonImageView.startAnimation(slideAnimation)
        }, 2000)
    }
}
