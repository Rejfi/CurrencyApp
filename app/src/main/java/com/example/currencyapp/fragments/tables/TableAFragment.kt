package com.example.currencyapp.fragments.tables


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.currencyapp.R
import com.example.currencyapp.adapters.NBPAdapter
import com.example.currencyapp.data.models.tableA.TableA
import com.example.currencyapp.viewmodels.CurrencyViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class TableAFragment : Fragment() {

    private var lastDate: Long = 0L
    private lateinit var currencyViewModel: CurrencyViewModel
    private lateinit var recyclerViewA: RecyclerView
    private lateinit var adapter: NBPAdapter
    private var currenciesRates: Array<TableA>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_table_a, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currencyViewModel = ViewModelProvider.AndroidViewModelFactory
            .getInstance(requireActivity().application)
            .create(CurrencyViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewA = view.findViewById(R.id.recyclerView_A)
        recyclerViewA.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(savedInstanceState != null && lastDate > 0){
            Log.e("Tag", "Wartosc ostatniej daty: $lastDate")
            val downloadJob = CoroutineScope(Dispatchers.Main).launch {
                currenciesRates = CoroutineScope(Dispatchers.IO).async {
                    currencyViewModel.getCurrencies(lastDate)
                }.await()
                if (currenciesRates != null) {
                    adapter = NBPAdapter(currenciesRates!!)
                    setupAdapterToRecyclerView(adapter, recyclerViewA)
                } else {
                    Snackbar.make(
                        requireView(),
                        "Brak danych",
                        Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }else{
            val downloadJob = CoroutineScope(Dispatchers.Main).launch {
                currenciesRates = CoroutineScope(Dispatchers.IO).async {
                    currencyViewModel.getCurrencies(Date().time)
                }.await()

                if (currenciesRates != null) {
                    adapter = NBPAdapter(currenciesRates!!)
                    setupAdapterToRecyclerView(adapter, recyclerViewA)
                } else {
                    Snackbar.make(
                        requireView(),
                        "Brak danych",
                        Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun setupAdapterToRecyclerView(adapter: NBPAdapter, recyclerView: RecyclerView){
        recyclerView.adapter = adapter
    }

}
