package com.example.budget.data

import androidx.compose.ui.graphics.vector.ImageVector

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

data class TypeDetail(
    val id: Int,
    val type: Type,
    val description: String,
    val target: Int,
    val available: Int,
    val icon: ImageVector
)

enum class Type {
    Income, Need, Want, Saving
}