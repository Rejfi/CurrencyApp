package com.example.currencyapp.fragments.tables


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
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

class TableAFragment : Fragment() {

    private lateinit var currencyViewModel: CurrencyViewModel
    private lateinit var recyclerViewA: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_table_a, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currencyViewModel = ViewModelProvider(requireActivity()).get(CurrencyViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewA = view.findViewById(R.id.recyclerView_A)
        recyclerViewA.layoutManager = LinearLayoutManager(requireContext())
        currencyViewModel.tableA.observe(requireActivity(), androidx.lifecycle.Observer{
            recyclerViewA.adapter = NBPAdapter(it)
        })

    }

}

