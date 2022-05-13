package com.periculum.internal.models

data class MobileAnalysis(
    val key: Long? = null,
    val name: String? = null,
    val source: String? = null,
    val clientFullName: String? = null,
    val clientPhoneNumber: String? = null,
    val accountType: String? = null,
    val accountBalance: String? = null,
    val accountID: String? = null,
    val accountName: String? = null,
    val bankName: String? = null,
    val statementType: String? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val createdDate: String? = null,
    val processingStatus: String? = null,
    val clientIdentification: List<ClientIdentification>? = null,
    val spendAnalysis: SpendAnalysis? = null,
    val transactionPatternAnalysis: TransactionPatternAnalysis? = null,
    val behavioralAnalysis: BehavioralAnalysis? = null,
    val cashFlowAnalysis: CashFlowAnalysis? = null,
    val incomeAnalysis: IncomeAnalysis? = null,
    val confidenceOnParsing: Long? = null
)
