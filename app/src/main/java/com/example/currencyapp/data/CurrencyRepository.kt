package com.example.currencyapp.data

import android.util.Log
import com.example.currencyapp.data.models.NBPTable
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException

class CurrencyRepository {
     fun getCurrencies(time: Long): Array<NBPTable>? {
         val currencyFetcher = CurrencyFetchr()
         val gson = Gson()
         var tableNBP: Array<NBPTable>? = null
         try {
             tableNBP = gson.fromJson(currencyFetcher.getJSONString(time), Array<NBPTable>::class.java)
         } catch (je: JSONException) {
             Log.e("TAG", je.message)
         }
         if (tableNBP.isNullOrEmpty()) {
             Log.e("Tag", "tableNBP jest nullem albo jest pusta")
         }
         return tableNBP
     }

}