package com.example.nutrient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDate

class FoodsActivity : AppCompatActivity(), NavCallbacks {

    private lateinit var fabMenu: FloatingActionButton
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var fabSummary: FloatingActionButton
    private lateinit var fabOptions: FloatingActionButton
    private lateinit var textAddEntry: TextView
    private lateinit var textSummary: TextView
    private lateinit var textOptions: TextView

    var date: String = LocalDate.now().toString()

    val foodFragment = FoodFragment()


    companion object {
        var currentPage = "Home"
    }

    private var isOpen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foods)

        val userFoodListFragment = EntryListFragment()

        fabMenu = findViewById(R.id.fabMenu)
        fabAdd = findViewById(R.id.fabAdd)
        fabSummary = findViewById(R.id.fabSummary)
        fabOptions = findViewById(R.id.fabOptions)
        textAddEntry = findViewById(R.id.textAddEntry)
        textSummary = findViewById(R.id.textSummary)
        textOptions = findViewById(R.id.textOptions)


        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFoodFragment, userFoodListFragment)
                commit()
            }
        }

        fabMenu.setOnClickListener { view ->
            toggleFab()
        }

        fabAdd.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFoodFragment, foodFragment)
                toggleFab()
                hideFab()
                currentPage = "UserList"
                commit()
            }
        }
    }

    override fun onBackPressed() {

        if (currentPage == "Home") {
            super.onBackPressed()
        } else if (currentPage  == "UserList" || currentPage == "SingleEntry") {
            supportFragmentManager.beginTransaction().apply {
                val userFoodListFragment = EntryListFragment()
                replace(R.id.flFoodFragment, userFoodListFragment)
                currentPage = "Home"
                showFab()
                commit()
            }
        } else if (currentPage == "SingleFood") {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFoodFragment, foodFragment)
                currentPage = "UserList"
                commit()
            }
        }
    }

    override fun onFoodSelected(Food: FoodItem) {

        val singleFoodFragment = SingleFoodFragment(Food)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFoodFragment, singleFoodFragment)
            currentPage = "SingleFood"
            commit()
        }
    }

    override fun onEntryChanged() {

        Log.d("onEntryChanged", "called")

        showFab()

        val userFoodListFragment = EntryListFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFoodFragment, userFoodListFragment)
            currentPage = "Home"
            commit()
        }
    }

    override fun onEntrySelected(entry: EntryItem) {
        val singleEntryFragment = singleEntryFragment(entry)

        Log.d("onEntrySelected", "Clicked")

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFoodFragment, singleEntryFragment)
            currentPage = "SingleEntry"
            commit()
        }
    }


    private fun toggleFab() {
        if(isOpen){

            fabMenu.rotation = 180F
            fabAdd.isClickable = false
            fabAdd.visibility = View.INVISIBLE
            fabSummary.isClickable = false
            fabSummary.visibility = View.INVISIBLE
            fabOptions.isClickable = false
            fabOptions.visibility = View.INVISIBLE
            textAddEntry.visibility = View.INVISIBLE
            textSummary.visibility = View.INVISIBLE
            textOptions.visibility = View.INVISIBLE
            isOpen = false

        } else {

            fabMenu.rotation = 0F
            fabAdd.isClickable = true
            fabAdd.visibility = View.VISIBLE
//            fabSummary.isClickable = true
//            fabSummary.visibility = View.VISIBLE
//            fabOptions.isClickable = true
//            fabOptions.visibility = View.VISIBLE
            textAddEntry.visibility = View.VISIBLE
//            textSummary.visibility = View.VISIBLE
//            textOptions.visibility = View.VISIBLE
            isOpen = true

        }
    }

    private fun hideFab() {
        fabMenu.visibility = View.INVISIBLE
        fabMenu.isClickable = false
        fabAdd.isClickable = false
        fabAdd.visibility = View.INVISIBLE
        fabSummary.isClickable = false
        fabSummary.visibility = View.INVISIBLE
        fabOptions.isClickable = false
        fabOptions.visibility = View.INVISIBLE
        textAddEntry.visibility = View.INVISIBLE
        textSummary.visibility = View.INVISIBLE
        textOptions.visibility = View.INVISIBLE
    }

    private fun showFab(){
        fabMenu.visibility = View.VISIBLE
        fabMenu.isClickable = true
    }

    override fun setSelectedDate(date: String) {
        this.date = date
    }

    override fun getSelectedDate(): String {
        return this.date
    }
}