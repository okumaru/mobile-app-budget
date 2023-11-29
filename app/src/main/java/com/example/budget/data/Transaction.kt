package com.example.budget.data

import java.sql.Timestamp

data class Transaction (
    val id: Int,
    val credit: Int,
    val debit: Int,
    val description: String?,
    val balanceBefore: Int,
    val balanceAfter: Int,
    val datetime: Timestamp,
    val createdAt: Timestamp,
    val updatedAt: Timestamp,
    val accountid: Int,
    val categoryid: Int,
)

data class AddTransaction (
    val credit: Int,
    val debit: Int,
    val description: String?,
    val datetime: String,
    val accountid: Int,
    val categoryid: Int,
)

data class UpdateTransaction (
    val credit: Int,
    val debit: Int,
    val description: String?,
    val datetime: String,
)

data class TrxWithAccountBudget (
    val id: Int,
    val credit: Int,
    val debit: Int,
    val description: String?,
    val balanceBefore: Int,
    val balanceAfter: Int,
    val datetime: Timestamp,
    val createdAt: Timestamp,
    val updatedAt: Timestamp,
    val accountid: Int,
    val categoryid: Int,
    val account: Account,
    val category: Category
)