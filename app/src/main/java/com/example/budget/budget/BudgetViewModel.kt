package com.example.budget.budget

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.budget.BudgetAPI
import com.example.budget.data.APIConfig
import com.example.budget.data.AddBudget
import com.example.budget.data.CategoryBudget
import com.google.gson.Gson
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.InternalAPI

class BudgetViewModel(config: APIConfig): ViewModel()  {

    private val apiConfig: APIConfig = config
    private val reqAPI = BudgetAPI(config).client

    var errorMessage: String by mutableStateOf("")

    suspend fun deleteBudget(budgetId: Int): Boolean {
        return try {

            val response: HttpResponse = reqAPI.delete("${apiConfig.apiPathBudget}?id=${budgetId}")
            response.status.value.equals(200)

        } catch (e: Exception) {
            errorMessage = e.message.toString()
            false
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun addBudget(budget: AddBudget): Boolean {
        return try {

            val response: HttpResponse = reqAPI.put( apiConfig.apiPathBudget ) {
                contentType(ContentType.Application.Json)
                body =  Gson().toJson(budget)
            }
            response.status.value.equals(200)

        } catch (e: Exception) {
            errorMessage = e.message.toString()
            false
        }
    }

    suspend fun getBudget(catId: Int): List<CategoryBudget>? {
        return try {

            val response: HttpResponse = reqAPI.get("${apiConfig.apiPathBudget}?categoryid=${catId}")
            val jsonCategories: String = response.body()
            Gson().fromJson(jsonCategories, Array<CategoryBudget>::class.java).toList()

        } catch (e: Exception) {
            errorMessage = e.message.toString()
            null
        }
    }
}