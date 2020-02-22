package com.example.currencyapp.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.currencyapp.R
import com.example.currencyapp.adapters.CurrencyViewPagerAdapter
import com.example.currencyapp.fragments.CalendarFragment
import com.example.currencyapp.viewmodels.CurrencyViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.*

class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var currencyViewModel: CurrencyViewModel
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var navigationView: NavigationView
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var coordinatorLayout: CoordinatorLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fm = supportFragmentManager

        currencyViewModel = ViewModelProvider(this).get(CurrencyViewModel::class.java)

        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)
        navigationView = findViewById(R.id.navigationView)
        floatingActionButton = findViewById(R.id.floatingActionButton)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)

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
        floatingActionButton.setOnClickListener {
            val dialog = CalendarFragment()
            dialog.show(fm, "DatePickerDialog")
        }
    }
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        //Convert year,month,day to millisecounds
        val c = Calendar.getInstance()
        c.set(year,month,dayOfMonth)
        val dayInMillis = c.time.time
        val today = Calendar.getInstance()

        if(checkIsDateAfterToday(today, c)){
            currencyViewModel.setTableA(dayInMillis)
            currencyViewModel.setTableC(dayInMillis)
        }else{
            showSnackbar(dayInMillis)
        }
    }

    private fun showSnackbar(time: Long){
        Snackbar.make(coordinatorLayout,
            "Brak danych dla ${convertToDate(time)}",
            Snackbar.LENGTH_LONG)
            .show()
    }

    private fun checkIsDateAfterToday(todayDate: Calendar, nextDate: Calendar): Boolean {
        return !todayDate.before(nextDate)
    }

    private fun convertToDate(time: Long): String {
        val c = Calendar.getInstance()
        c.timeInMillis = time
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)+1
        val day = c.get(Calendar.DAY_OF_MONTH)

        var monthString = month.toString()
        var dayString = day.toString()

        if(month < 10){
            monthString = "0$month"
        }
        if(day < 10){
            dayString = "0$day"
        }

        return "$dayString-$monthString-$year"
    }
}
