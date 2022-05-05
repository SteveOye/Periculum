package com.periculum.internal.models

data class BehavioralAnalysis (
    val monthToMonthInflowToOutflowRate: String? = null,
    val overallInflowToOutflowRate: String? = null,
    val totalLoanAmount: Long? = null,
    val totalLoanRepaymentAmount: Double? = null,
    val loanInflowRate: Double? = null,
    val loanRepaymentToInflowRate: Double? = null,
    val numberOfCreditLoanTransactions: Long? = null,
    val numberOfDebitRepaymentTransactions: Long? = null,
    val gamblingStatus: String? = null,
    val gamblingRate: Long? = null,
    val accountActivity: Double? = null,
    val percentOfInflowIrregularity: Double? = null,
    val averageMonthlyLoanAmount: Long? = null,
    val averageMonthlyLoanRepaymentAmount: Double? = null,
    val accountSweep: String? = null
)
