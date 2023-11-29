package com.example.budget.data

import com.google.gson.Gson
import io.ktor.client.HttpClient
import kotlinx.serialization.Serializable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.sql.Timestamp

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