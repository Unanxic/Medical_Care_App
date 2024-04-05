package com.example.data.utils

import android.util.Log

fun Any.toLog() {
    Log.d("MYAPP", this.toString())
}