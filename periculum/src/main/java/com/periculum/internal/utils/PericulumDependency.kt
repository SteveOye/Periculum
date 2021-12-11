package com.periculum.internal.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

@SuppressLint("StaticFieldLeak")
internal object PericulumDependency {
    private lateinit var context: Context
    private lateinit var sharedPreferences: SharedPreferences

    internal fun setContext(context: Context) {
        this.context = context
    }

    internal fun getApplicationContext(): Context {
        return context
    }

    internal fun setSharedPreferences(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }

    internal fun getSharedPreferences() : SharedPreferences {
        return sharedPreferences
    }
}