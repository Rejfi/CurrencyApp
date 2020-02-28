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

    /**
     * Jobs responsible for download data outside main thread
     *
     */
    private lateinit var jobA: Job
    private lateinit var jobB: Job
    private lateinit var jobC: Job
    private lateinit var jobFav: Job

    /**
     *  Arrays for data from repository
     *
     */
    private var loadedDataTableA: Array<TableA> = emptyArray()
    private var loadedDataTableB: Array<TableB> = emptyArray()
    private var loadedDataTableC: Array<TableC> = emptyArray()
    private var loadedDataTableFav: Array<TableA> = emptyArray()

    /**
     *
     *  LiveData which exposes data for Views
     */
    val tableA = MutableLiveData<Array<TableA>>().apply {
        loadTableA(c.timeInMillis)
        setTableA()
    }

    val tableB = MutableLiveData<Array<TableB>>().apply {
        loadTableB(c.timeInMillis)
        setTableB()
    }
    val tableC = MutableLiveData<Array<TableC>>().apply {
        loadTableC(c.timeInMillis)
        setTableC()
    }
    val tableFav = MutableLiveData<Array<TableA>>().apply {
        loadTableFav()
    }


    /**
     * Load async data from repository
     */
    private fun loadTableAAsync(time: Long): Deferred<Array<TableA>>{
       return CoroutineScope(Dispatchers.IO).async {
            repository.getTableA(time)
        }
    }
    private fun loadTableBAsync(time: Long): Deferred<Array<TableB>>{
        return CoroutineScope(Dispatchers.IO).async {
            repository.getTableB(time)
        }
    }

    private fun loadTableCAsync(time: Long): Deferred<Array<TableC>>{
        return CoroutineScope(Dispatchers.IO).async {
            repository.getTableC(time)
        }
    }


    /**
     * Set await for data from repository then set loadedDataTables
     *
     */

    fun loadTableA(time: Long) {
        jobA = CoroutineScope(viewModelScope.coroutineContext).launch {
            val loadedData = loadTableAAsync(time).await()
            loadedDataTableA = loadedData
        }
    }

     fun loadTableB(time: Long) {
        jobB = CoroutineScope(viewModelScope.coroutineContext).launch {
            val loadedData = loadTableBAsync(time).await()
            loadedDataTableB = loadedData
        }
    }

     fun loadTableC(time: Long) {
        jobC = CoroutineScope(viewModelScope.coroutineContext).launch {
            val loadedData = loadTableCAsync(time).await()
            loadedDataTableC = loadedData
        }

    }

     fun loadTableFav() {
        jobFav = CoroutineScope(viewModelScope.coroutineContext).launch {
            val loadedData = loadTableAAsync(c.timeInMillis).await()
            loadedDataTableFav = loadedData
        }
    }

    /**
     * If the jobs are completed post values
     */

    fun setTableA(){
        jobA.invokeOnCompletion {
            tableA.postValue(loadedDataTableA)
        }
    }

    fun setTableB(){
        jobB.invokeOnCompletion {
            tableB.postValue(loadedDataTableB)
        }
    }

    fun setTableC(){
        jobC.invokeOnCompletion {
            tableC.postValue(loadedDataTableC)
        }
    }

    fun setTableFav(){
        jobFav.invokeOnCompletion {
            tableFav.postValue(loadedDataTableFav)
        }

    }


    /**
     * Make sure that if viewmodel invokes onCleared all jobs will be canceled
     *
     */
    override fun onCleared() {
        super.onCleared()
        jobA.cancel()
        jobB.cancel()
        jobC.cancel()
        jobFav.cancel()
    }
}