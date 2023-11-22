package com.example.projetothefinal

import android.util.Log
import kotlinx.coroutines.*
import retrofit.Detalhes
import retrofit.News
import retrofit.Sumario
import retrofit.RetrofitHelper
import retrofit.retrofitInterface
import retrofit2.Response

class Backend {
    private val tickersAPI = RetrofitHelper.getInstance().create(retrofitInterface::class.java)
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private var tickersListDeferred: Deferred<List<String>>? = null

    init {
        getSimbolos()
    }
    fun getSimbolos() {
        try {
            tickersListDeferred = coroutineScope.async {
                val symbolsResponse = tickersAPI.getSimbolos()
                if (symbolsResponse.isSuccessful) {
                    symbolsResponse.body() ?: emptyList()
                } else {
                    emptyList()
                }
            }
        }catch (e : Exception){
            throw e
        }
    }

    private suspend fun fetchTickerDetails(ticker: String): Detalhes? {
        return try {
            val responseSymbolDetail: Response<Detalhes> = tickersAPI.getSimboloDetalhes(ticker)
            if (responseSymbolDetail.isSuccessful) {
                responseSymbolDetail.body()
            } else {
                Log.e("MyTag: ", "Failed to fetch details for ticker: $ticker")
                null
            }
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun fetchTickerSummary(ticker: String): Sumario? {
        return try {
            val responseSymbolSummary: Response<Sumario> = tickersAPI.getSimboloSumario(ticker)
            if (responseSymbolSummary.isSuccessful) {
                responseSymbolSummary.body()
            } else {
                Log.e("MyTag: ", "Failed to fetch summary for ticker: $ticker")
                null
            }
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun fetchTickersDetailsList(tickersList: List<String>): List<Detalhes> {
        val symbolDetailsList = mutableListOf<Detalhes>()
        for (ticker in tickersList) {
            fetchTickerDetails(ticker)?.let { symbolDetailsList.add(it) }
        }
        return symbolDetailsList
    }

    private suspend fun fetchTickersSummaryList(tickersList: List<String>): List<Sumario> {
        val symbolSummaryList = mutableListOf<Sumario>()
        for (ticker in tickersList) {
            fetchTickerSummary(ticker)?.let { symbolSummaryList.add(it) }
        }
        Log.e("Summary : ", "Fetched $symbolSummaryList")
        return symbolSummaryList
    }

    private suspend fun getSymbols() {
        try {
            tickersListDeferred = coroutineScope.async {
                val symbolsResponse = tickersAPI.getSimbolos()
                if (symbolsResponse.isSuccessful) {
                    symbolsResponse.body() ?: emptyList()
                } else {
                    emptyList()
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getTickersList(): List<String> {
        return tickersListDeferred?.await() ?: emptyList()
    }

    suspend fun fetchNews(): List<News> {
        try {
            val newsResponse: Response<List<News>> = tickersAPI.getNews()
            if (newsResponse.isSuccessful) {
                return newsResponse.body() ?: emptyList()
            } else {
                Log.e("MyTag: ", "Failed to get news")
                return emptyList()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun fetchTickerDetailsList(): List<Detalhes> {
        try {
            val tickersList = getTickersList()
            if (tickersList.isNullOrEmpty()) {
                Log.e("MyTag: ", "Failed to fetch details for ticker: $tickersList")
                return emptyList()
            }
            return fetchTickersDetailsList(tickersList)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun fetchTickerSummaryList(): List<Sumario> {
        try {
            val tickersList = getTickersList()
            if (tickersList.isNullOrEmpty()) {
                Log.e("MyTag: ", "Failed to fetch details for ticker: $tickersList")
                return emptyList()
            }
            return fetchTickersSummaryList(tickersList)
        } catch (e: Exception) {
            throw e
        }
    }
}
