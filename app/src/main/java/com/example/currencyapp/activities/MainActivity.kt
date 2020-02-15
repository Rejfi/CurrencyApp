package com.example.currencyapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.currencyapp.R
import com.example.currencyapp.fragments.CurrencyFragment

class MainActivity : AppCompatActivity() {

    //private lateinit var currencyFragment: CurrencyFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fm = supportFragmentManager
        //currencyFragment = CurrencyFragment()
        fm.beginTransaction().apply {
            add(R.id.fragment_container, CurrencyFragment())
            commit()
        }
    }
}
