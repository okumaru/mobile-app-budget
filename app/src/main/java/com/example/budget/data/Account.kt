package com.example.budget.data

import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val id: Int,
    val name: String,
    val description: String?,
    val star: Boolean,
    val type: String,
    val balance: Int,
    val createdAt: String,
    val updatedAt: String
)

@Serializable
data class AddAccount(
    val name: String,
    val description: String?,
    val star: Boolean,
    val type: String,
    val balance: Int
)