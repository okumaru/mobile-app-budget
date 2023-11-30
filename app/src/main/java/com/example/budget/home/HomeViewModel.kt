package com.example.budget.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.budget.BudgetAPI
import com.example.budget.data.APIConfig
import com.example.budget.data.Account
import com.example.budget.data.CategoryTypeWithBudget
import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.*

class HomeViewModel(config: APIConfig): ViewModel() {

    private val apiConfig: APIConfig = config
    private val reqAPI = BudgetAPI(config).client

    var errorMessage: String by mutableStateOf("")

    suspend fun getAccounts(): List<Account>? {
        return try {

            val response: HttpResponse = reqAPI.get( apiConfig.apiPathAccount )
            val jsonCats: String = response.body()
            Gson().fromJson(jsonCats, Array<Account>::class.java).toList()

        } catch (e: Exception) {
            errorMessage = e.message.toString()
            null
        }
    }

    suspend fun getCategories(): List<CategoryTypeWithBudget>? {
        return try {

            val response: HttpResponse = reqAPI.get( apiConfig.apiPathCatType )
            val jsonCats: String = response.body()
            Gson().fromJson(jsonCats, Array<CategoryTypeWithBudget>::class.java).toList()

        } catch (e: Exception) {
            errorMessage = e.message.toString()
            null
        }
    }
}