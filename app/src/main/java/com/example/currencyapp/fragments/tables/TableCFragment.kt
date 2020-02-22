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

/**
 * A simple [Fragment] subclass.
 */
class TableCFragment : Fragment() {

    private lateinit var currencyViewModel: CurrencyViewModel
    private lateinit var recyclerViewC: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
            recyclerViewC.adapter = AdapterTableC(it)
        })
    }
}
