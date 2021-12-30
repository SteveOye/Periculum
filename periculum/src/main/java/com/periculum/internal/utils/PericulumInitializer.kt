package com.periculum.internal.utils

import android.content.Context
import androidx.startup.Initializer

internal class PericulumInitializer : Initializer<PericulumDependency> {
    override fun create(context: Context): PericulumDependency {
        val periculumDependency = PericulumDependency
        periculumDependency.setContext(context)
        val sharedPreferences = context.getSharedPreferences(Utils.sharedPreferencesKey, 0)
        periculumDependency.setSharedPreferences(sharedPreferences)
        return PericulumDependency
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}