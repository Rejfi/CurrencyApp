package com.example.currencyapp.data

import com.example.currencyapp.data.models.tableA.TableA
import com.example.currencyapp.data.models.tableB.TableB
import com.example.currencyapp.data.models.tableC.TableC
import com.google.gson.Gson

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

    fun getTableB(time: Long): Array<TableB> {
        val currencyFetcher = CurrencyFetchr()
        val gson = Gson()
        var tableB: Array<TableB> = emptyArray()
        val data: Array<TableB>? =gson.fromJson(currencyFetcher.getJSONString(time, 'B'), Array<TableB>::class.java)
        if(!data.isNullOrEmpty())
            tableB= data

        return tableB
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