package com.example.currencyapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapp.R
import com.example.currencyapp.data.models.tableC.TableC

class AdapterTableC(private val currencies: Array<TableC>): RecyclerView.Adapter<CurrencyTableCViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyTableCViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val row = layoutInflater.inflate(R.layout.table_c_row, parent, false)
        return CurrencyTableCViewHolder(row)
    }

    override fun getItemCount(): Int {
        return currencies[0].rates.size
    }

    override fun onBindViewHolder(holder: CurrencyTableCViewHolder, position: Int) {
        holder.currencyName.text = currencies[0].rates[position].currency

        val concatenateString = "${currencies[0].rates[position].code}/PLN"
        holder.currencyByPLN.text = concatenateString

        val ask = "ASK: ${currencies[0].rates[position].ask}"
        holder.currencyAsk.text = ask

        val bid = "BID: ${currencies[0].rates[position].bid}"
        holder.currencyBid.text = bid
    }
}

class CurrencyTableCViewHolder(view: View): RecyclerView.ViewHolder(view){
    val currencyName: TextView = view.findViewById(R.id.currencyName)
    val currencyAsk: TextView = view.findViewById(R.id.currencyAsk)
    val currencyBid: TextView = view.findViewById(R.id.currencyBid)
    val currencyByPLN: TextView = view.findViewById(R.id.currencyByPLN)

}