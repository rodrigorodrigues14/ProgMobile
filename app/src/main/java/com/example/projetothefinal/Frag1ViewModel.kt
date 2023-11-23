package com.example.projetothefinal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit.Sumario

class Frag1ViewModel : ViewModel() {
    val coroutineScope = CoroutineScope(Dispatchers.IO)
    val data = Backend()

    private val _symbolSummaryList = MutableLiveData<List<Sumario>>()
    val symbolSummaryList : LiveData<List<Sumario>> get( ) = _symbolSummaryList

    init {
        coroutineScope.launch{
            val SymbolSummaryList = data.fetchTickerSummaryList()
            _symbolSummaryList.postValue(SymbolSummaryList)
        }
    }
}