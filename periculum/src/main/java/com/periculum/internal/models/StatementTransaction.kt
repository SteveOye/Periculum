package com.periculum.internal.models

data class StatementTransaction (
    val date: String? = null,
    val amount: Double? = null,
    val type: String? = null,
    val description: String? = null,
    val balance: Double? = null
)
