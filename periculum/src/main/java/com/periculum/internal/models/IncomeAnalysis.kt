package com.periculum.internal.models

data class IncomeAnalysis (
    val averagePredictedSalary: Double? = null,
    val isSalaryEarner: String? = null,
    val expectedSalaryPaymentDay: Double? = null,
    val frequencyOfSalaryPayments: String? = null,
    val lastDateOfSalaryPayment: String? = null,
    val numberOfSalaryPayments: Double? = null,
    val hasOtherIncome: String? = null,
    val averageOtherIncome: Double? = null,
    val numberOfOtherIncomePayments: Double? = null,
    val netAverageMonthlyEarning: Double? = null,
    val lowestSalary: Double? = null,
    val mostRecentSalary: String? = null
)