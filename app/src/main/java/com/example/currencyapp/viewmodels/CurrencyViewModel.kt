package com.example.currencyapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyapp.data.CurrencyRepository
import com.example.currencyapp.data.models.tableA.TableA
import com.example.currencyapp.data.models.tableB.TableB
import com.example.currencyapp.data.models.tableC.TableC
import kotlinx.coroutines.*
import java.util.*

class CurrencyViewModel : ViewModel() {


    private val repository = CurrencyRepository()
    private val c = Calendar.getInstance()
    private lateinit var jobA: Job
    private lateinit var jobB: Job
    private lateinit var jobC: Job
    private lateinit var jobFav: Job

    private var loadedDataTableA: Array<TableA> = emptyArray()
    private var loadedDataTableB: Array<TableB> = emptyArray()
    private var loadedDataTableC: Array<TableC> = emptyArray()

    val tableA = MutableLiveData<Array<TableA>>().apply {
        loadTableA(c.timeInMillis)
    }

    val tableB = MutableLiveData<Array<TableB>>().apply {
        loadTableB(c.timeInMillis)
    }
    val tableC = MutableLiveData<Array<TableC>>().apply {
        loadTableC(c.timeInMillis)
    }
    val tableFav = MutableLiveData<Array<TableA>>().apply {
        loadTableFav()
    }


    /**
     * Load async data from repository and post value if isn't null or empty
     */
    private suspend fun loadTableAAsync(time: Long): Deferred<Array<TableA>>{
       return CoroutineScope(Dispatchers.IO).async {
            repository.getTableA(time)
        }
    }
    private fun loadTableA(time: Long) {
       jobA = CoroutineScope(viewModelScope.coroutineContext).launch {
            val loadedData =
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                    repository.getTableA(time)
                }
            if(!loadedData.isNullOrEmpty())
                tableA.postValue(loadedData)
        }

    }

    private fun loadTableB(time: Long) {
       jobB = CoroutineScope(viewModelScope.coroutineContext).launch {
            val loadedData =
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                    repository.getTableB(time)
                }
            Log.e("Tag", "Załadowanie danych i wysłanie zapytania na serwer")
            if(!loadedData.isNullOrEmpty())
                tableB.postValue(loadedData)
        }
    }

    private fun loadTableC(time: Long) {
         jobC = CoroutineScope(viewModelScope.coroutineContext).launch {
            val loadedData =
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                    repository.getTableC(time)
                }

             if(!loadedData.isNullOrEmpty())
                tableC.postValue(loadedData)
        }

    }

    private fun loadTableFav() {
        jobFav = CoroutineScope(viewModelScope.coroutineContext).launch {
            val loadedData =
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                    repository.getTableA(c.timeInMillis)
                }
            Log.e("Tag", "Załadowanie danych i wysłanie zapytania na serwer")
            if(!loadedData.isNullOrEmpty())
                tableFav.postValue(loadedData)
        }
    }

    fun setTableA(time: Long){
        loadTableA(time)
    }
    fun setTableB(time: Long){
        loadTableB(time)
    }
    fun setTableC(time: Long){
        loadTableC(time)
    }
    fun setTableFav(){
        loadTableFav()
    }


    override fun onCleared() {
        super.onCleared()
        jobA.cancel()
        jobB.cancel()
        jobC.cancel()
        jobFav.cancel()
    }
}