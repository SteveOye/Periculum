package com.periculum.internal.utils

import android.content.Context
import androidx.startup.Initializer

internal class PericulumInitializer : Initializer<PericulumDependency> {
    override fun create(context: Context): PericulumDependency {
        val periculumDependency = PericulumDependency
        periculumDependency.setContext(context)
        return PericulumDependency
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}