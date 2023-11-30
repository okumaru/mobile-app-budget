package com.example.budget.trx_cat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.budget.BudgetAPI
import com.example.budget.data.APIConfig
import com.example.budget.data.AddCatWithTypeBudget
import com.example.budget.data.CategoryWithTypeBudget
import com.example.budget.data.UpdateCategory
import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.ContentType.Application.Json
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.InternalAPI
import kotlinx.coroutines.*

class TrxCatViewModel(config: APIConfig): ViewModel() {

    private val apiConfig: APIConfig = config
    private val reqAPI = BudgetAPI(config).client

    var errorMessage: String by mutableStateOf("")

    suspend fun deleteCat(catId: Int): Boolean {
        return try {

            val response: HttpResponse = reqAPI.delete("${apiConfig.apiPathTrxCat}?id=${catId}")
            response.status.value.equals(200)

        } catch (e: Exception) {
            errorMessage = e.message.toString()
            false
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun updateCat(catId: Int, updateCat: UpdateCategory): Boolean {
        return try {

            val response: HttpResponse = reqAPI.post("${apiConfig.apiPathTrxCat}?id=${catId}") {
                contentType(Json)
                body =  Gson().toJson(updateCat)
            }
            response.status.value.equals(200)

        } catch (e: Exception) {
            errorMessage = e.message.toString()
            false
        }
    }

    suspend fun detailCat(typeId: Int): CategoryWithTypeBudget? {
        return try {

            val response: HttpResponse = reqAPI.get("${apiConfig.apiPathTrxCat}?id=${typeId}")
            val jsonCats: String = response.body()
            Gson().fromJson(jsonCats, CategoryWithTypeBudget::class.java)

        } catch (e: Exception) {
            errorMessage = e.message.toString()
            null
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun addCategory(addCat: AddCatWithTypeBudget): Boolean {
        return try {

            val response: HttpResponse = reqAPI.put( apiConfig.apiPathTrxCat ) {
                contentType(Json)
                body =  Gson().toJson(addCat)
            }
            response.status.value.equals(200)

        } catch (e: Exception) {
            errorMessage = e.message.toString()
            false
        }
    }

    suspend fun getCategories(type: String?): List<CategoryWithTypeBudget>? {
        return try {

            val urlPath: String = when (type) {
                "Income" -> "${apiConfig.apiPathTrxCat}?typeid=1"
                "Saving" -> "${apiConfig.apiPathTrxCat}?typeid=2"
                "Need" -> "${apiConfig.apiPathTrxCat}?typeid=3"
                "Want" -> "${apiConfig.apiPathTrxCat}?typeid=4"
                else -> apiConfig.apiPathTrxCat
            }

            val response: HttpResponse = reqAPI.get(urlPath)
            val jsonCats: String = response.body()
            Gson().fromJson(jsonCats, Array<CategoryWithTypeBudget>::class.java).toList()

        } catch (e: Exception) {
            errorMessage = e.message.toString()
            null
        }
    }
}