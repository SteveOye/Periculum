package com.periculum.internal.models

data class IncomeAnalysis (
    val averagePredictedSalary: Double? = null,
    val isSalaryEarner: String? = null,
    val expectedSalaryPaymentDay: Long? = null,
    val frequencyOfSalaryPayments: String? = null,
    val lastDateOfSalaryPayment: String? = null,
    val numberOfSalaryPayments: Long? = null,
    val hasOtherIncome: String? = null,
    val averageOtherIncome: Long? = null,
    val numberOfOtherIncomePayments: Long? = null,
    val netAverageMonthlyEarning: Long? = null,
    val lowestSalary: Long? = null,
    val mostRecentSalary: String? = null
)