package com.periculum.internal.models

data class CreditScore (
    val rulesScore: Long? = null,
    val baseScore: Long? = null,
    val calculatedScore: Long? = null,
    val didPass: Boolean? = null,
    val passReasons: List<String>? = null,
    val failReasons: List<String>? = null,
    val type: String? = null
)
