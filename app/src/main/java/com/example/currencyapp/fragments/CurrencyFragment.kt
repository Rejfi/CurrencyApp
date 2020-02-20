package com.example.currencyapp.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapp.R
import com.example.currencyapp.adapters.NBPAdapter
import com.example.currencyapp.data.models.NBPTable
import com.example.currencyapp.viewmodels.CurrencyViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.currency_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*

class CurrencyFragment: Fragment(), DatePickerDialog.OnDateSetListener{

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NBPAdapter
    private lateinit var currencyViewModel: CurrencyViewModel
    private var currenciesRates: Array<NBPTable>? = null
    private lateinit var floatingActionButton: FloatingActionButton
    private var lastDate: Long = 0L

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.currency_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currencyViewModel = ViewModelProvider.AndroidViewModelFactory
            .getInstance(requireActivity().application)
            .create(CurrencyViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        floatingActionButton = view.findViewById(R.id.floatingButton)
        floatingActionButton.setOnClickListener {
            val dialog = CalendarFragment()
            dialog.show(fragmentManager!!, "1")
        }
        if(savedInstanceState != null)
            lastDate = savedInstanceState.getLong("lastDate")
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(savedInstanceState != null && lastDate > 0){
            Log.e("Tag", "Wartosc ostatniej daty: $lastDate")
            val downloadJob = CoroutineScope(Dispatchers.Main).launch {
                currenciesRates = CoroutineScope(IO).async {
                    currencyViewModel.getCurrencies(lastDate)
                }.await()

                if (currenciesRates != null) {
                    adapter = NBPAdapter(currenciesRates!!)
                    setupAdapterToRecyclerView(adapter, recyclerView)
                    setQuotationsDate(lastDate)
                } else {
                    Snackbar.make(
                        requireView(),
                        "Brak danych",
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                    setQuotationsDate(lastDate)
                }
            }
        }else{
            val downloadJob = CoroutineScope(Dispatchers.Main).launch {
                currenciesRates = CoroutineScope(IO).async {
                    currencyViewModel.getCurrencies(Date().time)
                }.await()

                if (currenciesRates != null) {
                    adapter = NBPAdapter(currenciesRates!!)
                    setupAdapterToRecyclerView(adapter, recyclerView)
                    setQuotationsDate(Date().time)
                } else {
                    Snackbar.make(
                        requireView(),
                        "Brak danych",
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                    setQuotationsDate(Date().time)
                }
            }
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        //Convert year,month,day to millisecounds
        val c = Calendar.getInstance()
        c.set(year,month,dayOfMonth)
        val dayInMillis = c.time.time
        val today = Calendar.getInstance()

        if(checkIsDateAfterToday(today, c)){
            CoroutineScope(Dispatchers.Main).launch {
                CoroutineScope(IO).async {
                    currenciesRates = currencyViewModel.getCurrencies(dayInMillis)
                }.await()
                if(currenciesRates != null){
                    val nbpAdapter = NBPAdapter(currenciesRates!!)
                    recyclerView.adapter = nbpAdapter
                    setQuotationsDate(dayInMillis)
                    lastDate = dayInMillis
                }
            }
            Log.e("Tag", lastDate.toString())
        }else{
            Snackbar.make(requireView(),
                "Brak danych dla dnia: " +
                        "${if(dayOfMonth < 10){"0$dayOfMonth" } else{dayOfMonth}}" +
                        "-${if(month <10){"0${month+1}"} else month+1}-$year",
                Snackbar.LENGTH_LONG)
                .show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("lastDate", lastDate)
    }

    private fun checkIsDateAfterToday(todayDate: Calendar, nextDate: Calendar): Boolean {
        return !todayDate.before(nextDate)
    }

    private fun updateCurrencyRates(time: Long): Boolean{
        currenciesRates = currencyViewModel.getCurrencies(time)
        if(!currenciesRates.isNullOrEmpty()){
            val nbpAdapter = NBPAdapter(currenciesRates!!)
            recyclerView.adapter = nbpAdapter
            return true
        }else{
            return false
        }
    }

    private fun setupAdapterToRecyclerView(adapter: NBPAdapter, recyclerView: RecyclerView){
        recyclerView.adapter = adapter
    }

    private fun setQuotationsDate(time: Long){
        val c = Calendar.getInstance()
        c.timeInMillis = time
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)+1
        val day = c.get(Calendar.DAY_OF_MONTH)

        var monthString = month.toString()
        var dayString = day.toString()

        if(month < 10){
            monthString = "0$month"
        }
        if(day < 10){
            dayString = "0$day"
        }

        val convertedDate = "$dayString-$monthString-$year"
        quotationsDate.text = convertedDate
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

}





