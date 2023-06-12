package com.example.plantappfirestore.utils

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.preference.PreferenceManager

object Util {
    fun transparentStatusBar(window: Window) {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false, window)
        window.statusBarColor = Color.TRANSPARENT
    }

    private fun setWindowFlag(bits: Int, on: Boolean, window: Window) {
        val winParams = window.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        window.attributes = winParams
    }

    fun setPref(c: Context, pref: String, value: String) {
        val e = PreferenceManager.getDefaultSharedPreferences(c).edit()
        e.putString(pref, value)
        e.apply()

    }

    fun getPref(c: Context, pref: String, value: String): String? {
        return PreferenceManager.getDefaultSharedPreferences(c).getString(
            pref,
            value
        )
    }

}