package com.example.currencyapp.data

import android.net.Uri
import android.util.Log
import org.json.JSONException
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection

class CurrencyFetchr {
    /**
     * Function retrieves bytes from given address
     * @param urlSpec https address
     */

    private fun getUrlBytes(urlSpec: String): ByteArray {
        val url = URL(urlSpec)
        val connection = url.openConnection() as HttpsURLConnection

        try {
            val out = ByteArrayOutputStream()
            val input = connection.inputStream

            if (connection.responseCode != HttpsURLConnection.HTTP_OK) {
                throw IOException(connection.responseMessage)
            }

            var bytesRead: Int
            var buffer = ByteArray(8192)

            do {
                bytesRead = input.read(buffer)

                out.write(buffer, 0, bytesRead)

            } while (input.read(buffer) > 0)

            out.close()

            return out.toByteArray()

        } catch (e: IOException) {
            Log.e("ERROR_HTTP_CONNECTION", e.message!!)
            return emptyArray<Byte>().toByteArray()
        } finally {
            connection.disconnect()
        }
    }

    /**
     * Change byteArray into String
     * @param urlSpec https address
     */

    private fun getUrlString(urlSpec: String): String {
        return String(getUrlBytes(urlSpec))
    }
    /**
     * Make HTTPS request for data
     * @param time date in milliseconds
     * @param table special const value which type of table from NBP API you would like to
     */

    fun getJSONString(time: Long, table: Char): String {

        val jsonString: String

        val date = convertToCorrectDate(time)

        jsonString = try {
            val url: String = Uri.parse("https://api.nbp.pl/api/exchangerates/tables/$table/$date/")
                .buildUpon()
                .appendQueryParameter("?format", "json")
                .build().toString()

            getUrlString(url)

        } catch (je: JSONException) {
            Log.e("TAG", je.message)
           "JSOS ERROR"
        }

        Log.e("Tag", jsonString)
        return jsonString
    }
}

/**
 * Convert time (Long) to pattern YYYY-MM-DD for API request
 */
private fun convertToCorrectDate(time: Long): String{

    val c = Calendar.getInstance()
    c.timeInMillis = time

    val year =c[Calendar.YEAR]
    val month = c[Calendar.MONTH]+1
    val day = c[Calendar.DAY_OF_MONTH]

    var monthString = month.toString()
    var dayString = day.toString()

    if(month < 10){
        monthString = "0$month"
    }
    if(day < 10){
        dayString = "0$day"
    }

    return "$year-$monthString-$dayString"

}