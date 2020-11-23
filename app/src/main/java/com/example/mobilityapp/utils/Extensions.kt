package com.example.mobilityapp.utils

import android.view.View
import com.example.mobilityapp.model.Transport

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}
