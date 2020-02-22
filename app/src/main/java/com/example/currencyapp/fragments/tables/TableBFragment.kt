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
import com.example.currencyapp.activities.MainActivity.Companion.convertToDate
import com.example.currencyapp.adapters.AdapterTableA
import com.example.currencyapp.adapters.AdapterTableB
import com.example.currencyapp.adapters.AdapterTableC
import com.example.currencyapp.viewmodels.CurrencyViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_table_b.*

/**
 * A simple [Fragment] subclass.
 */
class TableBFragment : Fragment() {

    private lateinit var currencyViewModel: CurrencyViewModel
    private lateinit var recyclerViewB: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_table_b, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currencyViewModel = ViewModelProvider(requireActivity()).get(CurrencyViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewB = view.findViewById(R.id.recyclerView_B)
        recyclerViewB.layoutManager = LinearLayoutManager(requireContext())

        currencyViewModel.tableB.observe(requireActivity(), androidx.lifecycle.Observer{
            //TableA is the same as TableB
            if(it.isNullOrEmpty()) showSnackbar()

            else {
                quotationsDateTableB.text = it[0].effectiveDate
                recyclerViewB.adapter = AdapterTableB(it)
            }
        })
    }

    private fun showSnackbar(){
        Snackbar.make(view!!,
            "Brak danych",
            Snackbar.LENGTH_LONG)
            .show()
    }
}
