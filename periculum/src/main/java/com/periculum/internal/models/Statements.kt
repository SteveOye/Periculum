package com.periculum.internal.models

data class Statements (
    val key: Long? = null,
    val name: String? = null,
    val source: String? = null,
    val clientFullName: Any? = null,
    val clientPhoneNumber: Any? = null,
    val accountType: Any? = null,
    val accountBalance: Any? = null,
    val accountID: Any? = null,
    val accountName: Any? = null,
    val bankName: Any? = null,
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