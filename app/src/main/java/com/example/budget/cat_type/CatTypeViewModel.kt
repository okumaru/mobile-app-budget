package com.example.budget.cat_type

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.budget.BudgetAPI
import com.example.budget.data.APIConfig
import com.example.budget.data.AddCatType
import com.example.budget.data.CategoryTypeWithBudget
import com.google.gson.Gson
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.InternalAPI

class CatTypeViewModel(config: APIConfig): ViewModel() {

    private val apiConfig: APIConfig = config
    private val reqAPI = BudgetAPI(config).client

    var errorMessage: String by mutableStateOf("")

    suspend fun deleteCatTypes(typeId: Int): Boolean {
        return try {

            val response: HttpResponse = reqAPI.delete("${apiConfig.apiPathCatType}?id=${typeId}")
            response.status.value.equals(200)

        } catch (e: Exception) {
            errorMessage = e.message.toString()
            false
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun updateCatTypes(typeId: Int, updateCatType: AddCatType): Boolean {
        return try {

            val response: HttpResponse = reqAPI.post("${apiConfig.apiPathCatType}?id=${typeId}") {
                contentType(ContentType.Application.Json)
                body =  Gson().toJson(updateCatType)
            }
            response.status.value.equals(200)

        } catch (e: Exception) {
            errorMessage = e.message.toString()
            false
        }
    }

    suspend fun detailCatTypes(typeId: Int): CategoryTypeWithBudget? {
        return try {

            val response: HttpResponse = reqAPI.get("${apiConfig.apiPathCatType}?id=${typeId}")
            val jsonCats: String = response.body()
            Gson().fromJson(jsonCats, CategoryTypeWithBudget::class.java)

        } catch (e: Exception) {
            errorMessage = e.message.toString()
            null
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun addCatType(addCatType: AddCatType): Boolean {
        return try {

            val response: HttpResponse = reqAPI.put( apiConfig.apiPathCatType ) {
                contentType(ContentType.Application.Json)
                body =  Gson().toJson(addCatType)
            }
            response.status.value.equals(200)

        } catch (e: Exception) {
            errorMessage = e.message.toString()
            false
        }
    }

    suspend fun getCatTypes(): List<CategoryTypeWithBudget>? {
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