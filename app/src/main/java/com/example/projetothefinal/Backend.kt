package com.example.projetothefinal

import kotlinx.coroutines.*
import retrofit2.Response
import retrofit.RetrofitHelper
import retrofit.retrofitInterface
import retrofit.News
import retrofit.Detalhes
import retrofit.Sumario
import android.util.Log

class Backend {
    private val tickersAPI = RetrofitHelper.getInstance().create(retrofitInterface::class.java)
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private var tickersListDeferred: Deferred<List<String>>? = null

    init {
        getSimbolos()
    }

    fun getSimbolos() {
        tickersListDeferred = coroutineScope.async {
            runCatching { tickersAPI.getSimbolos() }
                .getOrNull()
                ?.body() ?: emptyList()
        }
    }

    suspend fun getTickersList(): List<String> = tickersListDeferred?.await() ?: emptyList()

    suspend fun fetchNews(): List<News> {
        val newsResponse = runCatching { tickersAPI.getNews() }.getOrNull()
        return newsResponse?.body().orEmpty()
    }

    suspend fun fetchDetalhes(): List<Detalhes> {
        val tickersList = getTickersList()
        return tickersList.flatMap { ticker ->
            runCatching { tickersAPI.getSimboloDetalhes(ticker) }
                .getOrNull()
                ?.body()?.let{ listOf(it)}?: emptyList()

        }
    }

    suspend fun fetchSumario(): List<Sumario> {
        val tickersList = getTickersList()
        return tickersList.flatMap { ticker ->
            runCatching { tickersAPI.getSimboloSumario(ticker) }
                .getOrNull()
                ?.body()?.let{ listOf(it)}?: emptyList()
        }
    }
}