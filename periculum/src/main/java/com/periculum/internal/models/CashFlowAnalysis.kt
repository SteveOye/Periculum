package com.periculum.internal.models

data class CashFlowAnalysis (
    val totalCreditTurnover: Double? = null,
    val totalDebitTurnOver: Double? = null,
    val averageMonthlyCredits: Double? = null,
    val averageMonthlyDebits: Double? = null,
    val averageWeeklyCredits: Double? = null,
    val averageWeeklyDebits: Double? = null,
    val averageMonthlyBalance: Double? = null,
    val averageWeeklyBalance: Double? = null,
    val numberOfTransactingMonths: Long? = null,
    val periodInStatement: String? = null,
    val yearInStatement: String? = null,
    val firstDateInStatement: String? = null,
    val lastDateInStatement: String? = null,
    val closingBalance: Double? = null
)