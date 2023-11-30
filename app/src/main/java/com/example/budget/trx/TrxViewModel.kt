package com.example.budget.trx

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.budget.BudgetAPI
import com.example.budget.data.APIConfig
import com.example.budget.data.AddTransaction
import com.example.budget.data.TrxWithAccountBudget
import com.example.budget.data.UpdateTransaction
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

class TrxViewModel(config: APIConfig): ViewModel() {

    private val apiConfig: APIConfig = config
    private val reqAPI = BudgetAPI(config).client

    var errorMessage: String by mutableStateOf("")

    suspend fun deleteTrx(trxId: Int): Boolean {
        return try {

            val response: HttpResponse = reqAPI.delete("${apiConfig.apiPathTrx}?id=${trxId}")
            response.status.value.equals(200)

        } catch (e: Exception) {
            errorMessage = e.message.toString()
            false
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun updateTrx(trxId: Int, updateTrx: UpdateTransaction): Boolean {
        return try {

            val response: HttpResponse = reqAPI.post("${apiConfig.apiPathTrx}?id=${trxId}") {
                contentType(Json)
                body =  Gson().toJson(updateTrx)
            }
            response.status.value.equals(200)

        } catch (e: Exception) {
            errorMessage = e.message.toString()
            false
        }
    }

    suspend fun detailTrx(trxId: Int): TrxWithAccountBudget? {
        return try {

            val response: HttpResponse = reqAPI.get("${apiConfig.apiPathTrx}?id=${trxId}")
            val jsonCats: String = response.body()
            Gson().fromJson(jsonCats, TrxWithAccountBudget::class.java)

        } catch (e: Exception) {
            errorMessage = e.message.toString()
            null
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun addTrx(addTrx: AddTransaction): Boolean {
        return try {

            val response: HttpResponse = reqAPI.put( apiConfig.apiPathTrx ) {
                contentType(Json)
                body =  Gson().toJson(addTrx)
            }
            response.status.value.equals(200)

        } catch (e: Exception) {
            errorMessage = e.message.toString()
            false
        }
    }

    suspend fun getTrxs(accountId: Int?, categoryId: Int?): List<TrxWithAccountBudget>? {
        return try {

            var urlPath = apiConfig.apiPathTrx
            var listConditions: List<String> = mutableListOf()

            if (accountId !== null && accountId != 0) {
                listConditions += "accountid=${accountId}"
            }

            if (categoryId !== null && categoryId != 0) {
                listConditions += "categoryid=${categoryId}"
            }

            if (listConditions.isNotEmpty()) {
                urlPath += "?"
                urlPath += listConditions.joinToString(separator = "&")
            }

            val response: HttpResponse = reqAPI.get(urlPath)
            val jsonCats: String = response.body()
            Gson().fromJson(jsonCats, Array<TrxWithAccountBudget>::class.java).toList()

        } catch (e: Exception) {
            errorMessage = e.message.toString()
            null
        }
    }
}