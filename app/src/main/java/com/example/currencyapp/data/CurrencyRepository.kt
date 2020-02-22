package com.example.currencyapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.currencyapp.data.models.tableA.TableA
import com.example.currencyapp.data.models.tableC.TableC
import com.google.gson.Gson
import org.json.JSONException

class CurrencyRepository {

    fun getTableA(time: Long): Array<TableA> {
        val currencyFetcher = CurrencyFetchr()
        val gson = Gson()
        var tableA: Array<TableA> = emptyArray()
        val data: Array<TableA>? =gson.fromJson(currencyFetcher.getJSONString(time, 'A'), Array<TableA>::class.java)
        if(!data.isNullOrEmpty())
            tableA = data

        return tableA
    }

    fun getTableC(time: Long): Array<TableC> {
        val currencyFetcher = CurrencyFetchr()
        val gson = Gson()
        var tableC: Array<TableC> = emptyArray()
        val data: Array<TableC>? =gson.fromJson(currencyFetcher.getJSONString(time, 'C'), Array<TableC>::class.java)
        if(!data.isNullOrEmpty())
            tableC= data

        return tableC
    }

}