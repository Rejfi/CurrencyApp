package com.example.currencyapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.currencyapp.fragments.tables.TableAFragment
import com.example.currencyapp.fragments.tables.TableBFragment
import com.example.currencyapp.fragments.tables.TableCFragment

class CurrencyViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity){

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> TableAFragment()
            1 -> TableBFragment()
            2 -> TableCFragment()
            else -> TableAFragment()
        }
    }
}