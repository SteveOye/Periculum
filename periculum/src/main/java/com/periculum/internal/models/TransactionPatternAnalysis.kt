package com.periculum.internal.models


data class TransactionPatternAnalysis (
    val lastDayOfCredit: String? = null,
    val lastDayOfDebit: String? = null,
    val percentDebitTransactions: Double? = null,
    val percentCreditTransactions: Double? = null,
    val totalNumberOfTransactions: Long? = null,
    val percentOfTransactionsLessThan10ThousandNaira: Double? = null,
    val percentOfTransactionsBetween10ThousandTo100ThousandNaira: Double? = null,
    val percentOfTransactionsBetween100ThousandTo500ThousandNaira: Double? = null,
    val percentOfTransactionsBetween500ThousandToOneMillionNaira: Long? = null,
    val percentOfTransactionsGreaterThanOneMillionNaira: Long? = null,
    val percentNumberOfDaysTransactionsWasLessThan10ThousandNaira: Double? = null,
    val percentOfBalancesLessThan10ThousandNaira: Double? = null,
    val percentOfBalancesBetween10ThousandTo100ThousandNaira: Double? = null,
    val percentOfBalancesBetween100ThousandTo500ThousandNaira: Double? = null,
    val percentOfBalancesBetween500ThousandToOneMillionNaira: Long? = null,
    val percentOfBalancesGreaterThanOneMillionNaira: Long? = null,
    val percentNumberOfDaysBalanceWasLessThan10ThousandNaira: Double? = null,
    val mostFrequentBalanceRange: String? = null,
    val mostFrequentTransactionRange: String? = null,
    val numberOfCardRequests: Long? = null,
    val topIncomingTransfer: Any? = null,
    val mostFrequentCreditTransfer: String? = null,
    val mostFrequentDebitTransfer: String? = null,
    val topOutgoingTransfer: Any? = null,
    val returnCheque: Long? = null,
    val balanceBreakdown: List<Breakdown>? = null,
    val transactionBreakdown: List<Breakdown>? = null,
    val percentNumberOfDaysTransactionsInSmallestBucket: Double? = null,
    val percentNumberOfDaysBalancesInSmallestBucket: Double? = null
)