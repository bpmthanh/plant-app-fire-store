package com.example.plantappfirestore.extensions

import android.view.View

fun View.hide() {
    try {
        this.run {
            this.visibility = View.GONE
        }
    } catch (e: java.lang.Exception) {
        this.run {
            this.visibility = View.GONE
        }
    }
}

fun View.show() {
    try {
        this.run {
            this.visibility = View.VISIBLE
        }
    } catch (e: java.lang.Exception) {
        this.run {
            this.visibility = View.GONE
        }
    }
}