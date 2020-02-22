package com.example.currencyapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapp.R
import com.example.currencyapp.data.models.tableB.TableB

class AdapterTableB(private val currencies: Array<TableB>): RecyclerView.Adapter<CurrencyTableBViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyTableBViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //TableA and TableB are the same
        val row = layoutInflater.inflate(R.layout.table_a_row, parent, false)
        return CurrencyTableBViewHolder(row)
    }

    override fun getItemCount(): Int {
        return currencies[0].rates.size
    }

    override fun onBindViewHolder(holderTableB: CurrencyTableBViewHolder, position: Int) {
        holderTableB.currencyName.text = currencies[0].rates[position].currency

        val concatenateString = "${currencies[0].rates[position].code}/PLN"
        holderTableB.currencyByPLN.text = concatenateString

        val mid = "MID: ${currencies[0].rates[position].mid.toString()}"
        holderTableB.currencyRates.text = mid
    }
}

class CurrencyTableBViewHolder(view: View): RecyclerView.ViewHolder(view){
    val currencyName: TextView = view.findViewById(R.id.currencyName)
    val currencyRates: TextView = view.findViewById(R.id.currencyRates)
    val currencyByPLN: TextView = view.findViewById(R.id.currencyByPLN)

}