package com.example.currencyapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.currencyapp.data.CurrencyRepository
import com.example.currencyapp.data.models.tableA.TableA

class CurrencyViewModel : ViewModel() {

    private val repository = CurrencyRepository()
    private var currenciesToday: Array<TableA>? = null
    private var lastSelectedDate: Long = 0L

    fun getCurrencies(time: Long): Array<TableA>?{
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