package com.example.currencyapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapp.R
import com.example.currencyapp.data.models.tableA.RateA
import com.example.currencyapp.data.models.tableA.TableA

class AdapterTableA(private val currencies: Array<TableA>,
                    val itemClickListener: OnItemClickListener
): RecyclerView.Adapter<CurrencyTableAViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyTableAViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val row = layoutInflater.inflate(R.layout.table_a_row, parent, false)
        return CurrencyTableAViewHolder(row)
    }

    override fun getItemCount(): Int {
        return currencies[0].rates.size
    }

    override fun onBindViewHolder(holderTableA: CurrencyTableAViewHolder, position: Int) {
      /*
        holderTableA.currencyName.text = currencies[0].rates[position].currency

        val concatenateString = "${currencies[0].rates[position].code}/PLN"
        holderTableA.currencyByPLN.text = concatenateString

        val mid = "MID: ${currencies[0].rates[position].mid.toString()}"
        holderTableA.currencyRates.text = mid
    */
        val rateA = currencies[0].rates[position]
        holderTableA.bind(rateA, itemClickListener)
    }


    fun getCurrency(position: Int): RateA {
        return currencies[0].rates[position]
    }
}

class CurrencyTableAViewHolder(view: View): RecyclerView.ViewHolder(view){

    val currencyName: TextView = view.findViewById(R.id.currencyName)
    val currencyRates: TextView = view.findViewById(R.id.currencyRates)
    val currencyByPLN: TextView = view.findViewById(R.id.currencyByPLN)

    fun bind(rate: RateA, clickListener: OnItemClickListener)
    {
        currencyName.text = rate.currency
        currencyRates.text = rate.mid.toString()
        currencyByPLN.text = rate.code

        itemView.setOnClickListener {
            clickListener.onItemClicked(rate)
        }
    }

}

interface OnItemClickListener{
    fun onItemClicked(rate: RateA)
}