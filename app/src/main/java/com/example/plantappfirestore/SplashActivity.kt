package com.example.plantappfirestore

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.plantappfirestore.utils.Constant
import com.example.plantappfirestore.utils.Util

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        bindComponent()
        bindEvent()
    }

    private fun bindComponent() {
        Handler(Looper.getMainLooper()).postDelayed({
            Util.getPref(this, Constant.IS_FIRST_TIME_INTRO,"")?.let {
                if(it.isNotEmpty()){
                    startActivity(Intent(applicationContext, LogInActivity::class.java))
                    finish()
                }else{
                    startActivity(Intent(applicationContext, IntroProfileActivity::class.java))
                    finish()
                }
            }

        },1500)
    }

    private fun bindEvent() {

    }
}