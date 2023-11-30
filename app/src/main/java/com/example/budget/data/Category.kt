package com.example.budget.data

data class Category(
    val id: Int,
    val name: String,
    val description: String?,
    val createdAt: String,
    val updatedAt: String,
    val typeid: Int,
)

data class CategoryWithTypeBudget(
    val id: Int,
    val name: String,
    val description: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val typeid: Int,
    val type: CategoryType,
    val budget: CategoryBudget?
)

data class AddCatWithTypeBudget(
    val name: String,
    val description: String?,
    val typeid: Int,
    val budget: AddBudgetFromCat?
)

data class UpdateCategory(
    val name: String,
    val description: String?,
)
