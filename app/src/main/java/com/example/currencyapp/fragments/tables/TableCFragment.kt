package com.example.currencyapp.fragments.tables


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.currencyapp.R
import com.example.currencyapp.adapters.AdapterTableA
import com.example.currencyapp.adapters.AdapterTableC
import com.example.currencyapp.viewmodels.CurrencyViewModel
import kotlinx.android.synthetic.main.fragment_table_c.*

class TableCFragment : Fragment() {

    private lateinit var currencyViewModel: CurrencyViewModel
    private lateinit var recyclerViewC: RecyclerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_table_c, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currencyViewModel = ViewModelProvider(requireActivity()).get(CurrencyViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewC = view.findViewById(R.id.recyclerView_C)
        recyclerViewC.layoutManager = LinearLayoutManager(requireContext())

        currencyViewModel.tableC.observe(requireActivity(), androidx.lifecycle.Observer{
            if (!it.isNullOrEmpty()){
                quotationsDateTableC.text = it[0].effectiveDate
                recyclerViewC.adapter = AdapterTableC(it)
            }
        })
    }
}
