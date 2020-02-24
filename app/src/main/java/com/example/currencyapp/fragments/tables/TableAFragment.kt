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
import com.example.currencyapp.adapters.AdapterTableA
import com.example.currencyapp.adapters.OnItemClickListener
import com.example.currencyapp.data.models.tableA.RateA
import com.example.currencyapp.viewmodels.CurrencyViewModel
import kotlinx.android.synthetic.main.fragment_table_a.*

class TableAFragment : Fragment(), OnItemClickListener{

    private lateinit var currencyViewModel: CurrencyViewModel
    private lateinit var recyclerViewA: RecyclerView
    private lateinit var shpref: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_table_a, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currencyViewModel = ViewModelProvider(requireActivity()).get(CurrencyViewModel::class.java)
        shpref = requireActivity().application.getSharedPreferences("favourite_curr", Context.MODE_PRIVATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewA = view.findViewById(R.id.recyclerView_A)
        recyclerViewA.layoutManager = LinearLayoutManager(requireContext())
        currencyViewModel.tableA.observe(requireActivity(), androidx.lifecycle.Observer{
            if (!it.isNullOrEmpty()) {
                quotationsDateTableA.text = it[0].effectiveDate
                recyclerViewA.adapter = AdapterTableA(it, this)
            }

        })

    }

    override fun onItemClicked(rate: RateA) {
         shpref.edit().apply{
            putString(rate.code, rate.code)
            commit()
        }
        currencyViewModel.setTableFav()
        Log.e("Tag", "Wybrana waluta ${rate.code}")
    }

}

