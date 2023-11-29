package com.example.projetothefinal.interfaceFrags.Home.CardFrags.EditList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit.Sumario
import com.example.projetothefinal.Backend

class Frag1ViewModel : ViewModel() {
    val coroutineScope = CoroutineScope(Dispatchers.IO)
    val data = Backend()

    private val _symbolSummaryList = MutableLiveData<List<Sumario>>()
    val symbolSummaryList : LiveData<List<Sumario>> get( ) = _symbolSummaryList

    init {
        coroutineScope.launch{
            val SymbolSummaryList = data.fetchSumario()
            _symbolSummaryList.postValue(SymbolSummaryList)
        }
    }
}