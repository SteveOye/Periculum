package com.periculum.models

/**
 * @param phoneNumber
 * @param bvn
 * @param dti
 * @param loanTenure
 * @param token
 */
data class VendorData(val phoneNumber: String, val bvn: String, val dti: Double, val loanTenure: Int, val token: String)
