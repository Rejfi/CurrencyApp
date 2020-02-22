package com.example.currencyapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyapp.data.CurrencyRepository
import com.example.currencyapp.data.models.tableA.TableA
import com.example.currencyapp.data.models.tableC.TableC
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class CurrencyViewModel : ViewModel() {

    private val repository = CurrencyRepository()
    private val c = Calendar.getInstance()

    val tableA = MutableLiveData<Array<TableA>>().apply {
        loadTableA(c.timeInMillis)
    }
    val tableC = MutableLiveData<Array<TableC>>().apply {
        loadTableC(c.timeInMillis)
    }

    /**
     * Load async data from repository and post value if isn't null or empty
     */
    private fun loadTableA(time: Long) {
            CoroutineScope(viewModelScope.coroutineContext).launch {
               val loadedData =
                   withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                       repository.getTableA(time)
                   }
                    if(!loadedData.isNullOrEmpty())
                        tableA.postValue(loadedData)
            }
    }

    private fun loadTableC(time: Long) {
        CoroutineScope(viewModelScope.coroutineContext).launch {
            val loadedData =
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                    repository.getTableC(time)
                }
            if(!loadedData.isNullOrEmpty())
                tableC.postValue(loadedData)
        }
    }

    fun setTableA(time: Long){
       loadTableA(time)
    }

    fun setTableC(time: Long){
        loadTableC(time)
    }
}