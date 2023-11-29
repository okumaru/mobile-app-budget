package com.example.budget.data

data class CategoryBudget (
    val id: Int,
    val periode: String,
    val allocated: Int,
    val spent: Int,
    val available: Int,
    val createdAt: String,
    val updatedAt: String,
    val categoryid: Int
)

data class AddBudget (
    val periode: String,
    val allocated: Int,
    val spent: Int,
    val available: Int,
    val categoryid: Int,
)

data class AddBudgetFromCat (
    val periode: String,
    val allocated: Int,
    val spent: Int,
    val available: Int,
)