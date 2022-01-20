package com.periculum.internal.utils

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
internal object PericulumDependency {
    private lateinit var context: Context

    internal fun setContext(context: Context) {
        this.context = context
    }

    internal fun getApplicationContext(): Context {
        return context
    }
}