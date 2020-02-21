package com.example.currencyapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapp.R
import com.example.currencyapp.data.models.tableA.TableA

class NBPAdapter(private val currencies: Array<TableA>): RecyclerView.Adapter<CurrencyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val row = layoutInflater.inflate(R.layout.currency_row, parent, false)
        return CurrencyViewHolder(row)
    }

    override fun getItemCount(): Int {
        return currencies[0].rates.size
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.currencyName.text = currencies[0].rates[position].currency
        val concatenateString = "${currencies[0].rates[position].code}/PLN"
        holder.currencyByPLN.text = concatenateString
        holder.currencyRates.text = currencies[0].rates[position].mid.toString()

    }
}

class CurrencyViewHolder(view: View): RecyclerView.ViewHolder(view){
    val currencyName: TextView = view.findViewById(R.id.currencyName)
    val currencyRates: TextView = view.findViewById(R.id.currencyRates)
    val currencyByPLN: TextView = view.findViewById(R.id.currencyByPLN)

}