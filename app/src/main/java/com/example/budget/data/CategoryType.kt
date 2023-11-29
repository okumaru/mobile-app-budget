package com.example.budget.data

import com.google.gson.Gson
import io.ktor.client.HttpClient
import kotlinx.serialization.Serializable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.sql.Timestamp

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