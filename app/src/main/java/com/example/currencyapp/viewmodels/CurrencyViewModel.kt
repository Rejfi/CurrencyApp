package com.example.currencyapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyapp.data.CurrencyRepository
import com.example.currencyapp.data.models.NBPTable
import kotlinx.coroutines.runBlocking

class CurrencyViewModel : ViewModel() {

    private val repository = CurrencyRepository()
    private var currenciesToday: Array<NBPTable>? = null
    private var lastSelectedDate: Long = 0L

    fun getCurrencies(time: Long): Array<NBPTable>?{
        val currenciesRatesToday = repository.getCurrencies(time)
        if(currenciesRatesToday != null){
            currenciesToday = currenciesRatesToday
            return currenciesToday
        }
        return currenciesToday
    }
    fun getLastSelectedDate(): Long {
        return lastSelectedDate
    }

    fun setLastSelectedDate(time: Long){
        lastSelectedDate = time
    }
}