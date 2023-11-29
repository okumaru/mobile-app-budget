package com.example.budget.trx_cat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.budget.data.Category
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
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class CategoryViewModel: ViewModel() {
    private val client = HttpClient(CIO) {
        defaultRequest {
            url {
                protocol = URLProtocol.HTTP
                host = "sohfin.okumaru.my.id"
                path("/")
            }
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                }
            )
        }
    }

    var errorMessage: String by mutableStateOf("")

    suspend fun getCategories(type: String?): List<Category>? {
        return try {

            val urlPath: String = when (type) {
                "Income" -> "trx_cats?typeid=1"
                "Saving" -> "trx_cats?typeid=2"
                "Need" -> "trx_cats?typeid=3"
                "Want" -> "trx_cats?typeid=4"
                else -> "trx_cats"
            }

            val response: HttpResponse = client.get(urlPath);
            val jsonCats: String = response.body();
            Gson().fromJson(jsonCats, Array<Category>::class.java).toList();

        } catch (e: Exception) {
            errorMessage = e.message.toString()
            null
        }
    }
}