package com.example.currencyapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapp.R
import com.example.currencyapp.data.models.tableA.RateA

class AdapterFavCurrency(private val currencies: List<RateA>,
                         private val longClickListener: OnLongItemClickListener):
    RecyclerView.Adapter<CurrencyFavouriteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyFavouriteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val row = layoutInflater.inflate(R.layout.table_a_row, parent, false)
        return CurrencyFavouriteViewHolder(row)
    }

    override fun getItemCount(): Int {
        return currencies.size
    }

    override fun onBindViewHolder(holderTableA: CurrencyFavouriteViewHolder, position: Int) {

        if(currencies.isNotEmpty()){
            val rateA = currencies[position]
            holderTableA.bind(rateA, longClickListener)
        }
    }


    fun refreshDataSet(){
        notifyDataSetChanged()
    }


}

class CurrencyFavouriteViewHolder(view: View): RecyclerView.ViewHolder(view){

    val currencyName: TextView = view.findViewById(R.id.currencyName)
    val currencyRates: TextView = view.findViewById(R.id.currencyRates)
    val currencyByPLN: TextView = view.findViewById(R.id.currencyByPLN)

    fun bind(rate: RateA, longClickListener: OnLongItemClickListener)
    {
        currencyName.text = rate.currency
        currencyRates.text = rate.mid.toString()
        currencyByPLN.text = rate.code

        itemView.setOnLongClickListener {
            longClickListener.onLongItemClicked(rate)
        }
    }

}
    interface OnLongItemClickListener{
        fun onLongItemClicked(rate: RateA): Boolean
    }

