package com.example.currencyapp.fragments.tables


import android.content.Context
import android.content.SharedPreferences
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
import com.example.currencyapp.adapters.AdapterFavCurrency
import com.example.currencyapp.adapters.AdapterTableA
import com.example.currencyapp.adapters.OnLongItemClickListener
import com.example.currencyapp.data.models.tableA.RateA
import com.example.currencyapp.data.models.tableA.TableA
import com.example.currencyapp.viewmodels.CurrencyViewModel
import kotlinx.android.synthetic.main.fragment_table_a.*

class FavouriteCurrencyFrag : Fragment(), OnLongItemClickListener {

    private lateinit var currencyViewModel: CurrencyViewModel
    private lateinit var recyclerViewFav: RecyclerView
    private lateinit var shpref: SharedPreferences
    private lateinit var adapterFav: AdapterFavCurrency

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite_currency, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currencyViewModel = ViewModelProvider(requireActivity()).get(CurrencyViewModel::class.java)
        shpref = requireActivity().application.getSharedPreferences("favourite_curr", Context.MODE_PRIVATE)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewFav = view.findViewById(R.id.recyclerView_favourite)
        recyclerViewFav.layoutManager = LinearLayoutManager(requireContext())
        currencyViewModel.tableFav.observe(requireActivity(), androidx.lifecycle.Observer{
            if (!it.isNullOrEmpty()) {
                val list = ArrayList<RateA>()
                it[0].rates.forEach {
                        if(getFavCurrencyList().contains(it.code)){
                            Log.e("Tag", "Wypisanie danych z rate ForEach")
                            list.add(it)
                        }
                    }
                    adapterFav = AdapterFavCurrency(list.toList(), this)
                    recyclerViewFav.adapter = adapterFav

            }
        })
    }

    override fun onLongItemClicked(rate: RateA): Boolean {
        shpref.edit().apply {
            remove(rate.code)
            commit()
        }
        currencyViewModel.setTableFav()
        Log.e("Tag", "long click ${rate.code}")
        return true
    }


    private fun getFavCurrencyList(): ArrayList<String> {
        val allFavCurrencyCode = shpref.all
        val keys = allFavCurrencyCode.keys
        val favCurrencyList = ArrayList<String>()

        for(i in keys){
            favCurrencyList.add(allFavCurrencyCode[i] as String)
        }

        return favCurrencyList
    }

}
