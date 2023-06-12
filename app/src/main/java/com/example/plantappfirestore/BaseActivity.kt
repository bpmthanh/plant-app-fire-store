package com.example.plantappfirestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.plantappfirestore.utils.Util

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Util.transparentStatusBar(window)
    }
}