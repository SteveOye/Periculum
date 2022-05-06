package com.periculum.internal.models

data class Affordability(
    val dti: Double,
    val loanTenure: Int,
    val statementKey: Int,
    val averageMonthlyTotalExpenses: Double?,
    val averageMonthlyLoanRepaymentAmount: Double?,
    val monthlyDisposableIncomeMonthlyAffordabilityAmount: Long? = null,
    val affordabilityAmount: Long? = null,
    val createdDate: String? = null,
    val averagePredictedSalary: Double? = null,
    val averageOtherIncome: Long? = null,
)
