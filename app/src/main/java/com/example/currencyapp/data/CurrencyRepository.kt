package com.example.currencyapp.data

import android.util.Log
import com.example.currencyapp.data.models.tableA.TableA
import com.google.gson.Gson
import org.json.JSONException

class CurrencyRepository {
     fun getCurrencies(time: Long): Array<TableA>? {
         val currencyFetcher = CurrencyFetchr()
         val gson = Gson()
         var tableA: Array<TableA>? = null
         try {
             tableA = gson.fromJson(currencyFetcher.getJSONString(time), Array<TableA>::class.java)
         } catch (je: JSONException) {
             Log.e("TAG", je.message)
         }
         if (tableA.isNullOrEmpty()) {
             Log.e("Tag", "tableNBP jest nullem albo jest pusta")
         }
         return tableA
     }

}