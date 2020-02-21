package com.example.currencyapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.currencyapp.R
import com.example.currencyapp.adapters.CurrencyViewPagerAdapter
import com.example.currencyapp.fragments.FavouriteCurrencyFrag
import com.example.currencyapp.viewmodels.CurrencyViewModel
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var currencyViewModel: CurrencyViewModel
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currencyViewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(CurrencyViewModel::class.java)

        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)
        navigationView = findViewById(R.id.navigationView)

        val viewPagerAdapter = CurrencyViewPagerAdapter(this)
        viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(tabLayout
            ,viewPager
            ,TabLayoutMediator.TabConfigurationStrategy {
                    tab, position ->
                when(position){
                    0 -> tab.text = "Tabela A"
                    1 -> tab.text = "Tabela B"
                    2 -> tab.text = "Tabela C"
                }
            }).attach()
    }
}
