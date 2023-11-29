package com.example.budget.account

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.budget.BudgetAPI
import com.example.budget.data.APIConfig
import com.example.budget.data.Account
import com.example.budget.data.AddAccount
import com.example.budget.data.Config
import com.example.budget.data.ConfigDao
import com.example.budget.data.ConfigDatabase
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class AccountViewModel(config: APIConfig) : ViewModel() {

    private val apiConfig: APIConfig = config;
    private val reqAPI = BudgetAPI(config).client;

    var errorMessage: String by mutableStateOf("")

    suspend fun deleteAccount(typeId: Int): Boolean {
        return try {

            val response: HttpResponse = reqAPI.delete("${apiConfig.apiPathAccount}?id=${typeId}")
            response.status.value.equals(200)

        } catch (e: Exception) {
            errorMessage = e.message.toString();
            false;
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun updateAccount(accountId: Int, addAccount: AddAccount): Boolean {
        return try {

            val response: HttpResponse = reqAPI.post("${apiConfig.apiPathAccount}?id=${accountId}") {
                contentType(ContentType.Application.Json)
                body =  Gson().toJson(addAccount)
            }
            response.status.value.equals(200)

        } catch (e: Exception) {
            errorMessage = e.message.toString();
            false;
        }
    }

    suspend fun detailAccount(accountId: Int): Account? {
        return try {

            val response: HttpResponse = reqAPI.get("${apiConfig.apiPathAccount}?id=${accountId}");
            val jsonCats: String = response.body();
            Gson().fromJson(jsonCats, Account::class.java);

        } catch (e: Exception) {
            errorMessage = e.message.toString()
            null
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun addAccount(addAccount: AddAccount): Boolean {
        return try {

            val response: HttpResponse = reqAPI.put( apiConfig.apiPathAccount ) {
                contentType(ContentType.Application.Json)
                body =  Gson().toJson(addAccount)
            }
            response.status.value.equals(200)

        } catch (e: Exception) {
            errorMessage = e.message.toString();
            false;
        }
    }

    suspend fun getAccounts(): List<Account>? {
        return try {

            val response: HttpResponse = reqAPI.get( apiConfig.apiPathAccount );
            val jsonCats: String = response.body();
            Gson().fromJson(jsonCats, Array<Account>::class.java).toList();

        } catch (e: Exception) {
            errorMessage = e.message.toString()
            null
        }
    }
}