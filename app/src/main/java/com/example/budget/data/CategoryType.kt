package com.example.budget.data

import kotlinx.serialization.Serializable

@Serializable
data class CategoryType(
    val id: Int,
    val type: String,
    val description: String?,
    val icon: String,
    val createdAt: String,
    val updatedAt: String
)

@Serializable
data class CategoryTypeWithBudget(
    val id: Int,
    val type: String,
    val description: String?,
    val icon: String,
    val createdAt: String,
    val updatedAt: String,
    val allocated: Int,
    val spent: Int,
    val available: Int,
)

@Serializable
data class AddCatType(
    val type: String,
    val description: String?,
    val icon: String
)