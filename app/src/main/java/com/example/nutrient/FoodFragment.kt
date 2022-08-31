package com.example.nutrient

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.SearchView

class FoodFragment() : Fragment(R.layout.fragment_food_list) {

    private var nestedFoodFragment = NestedFoodFragment()
    private lateinit var searchView: SearchView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        searchView = view.findViewById(R.id.food_name_search)

        childFragmentManager.beginTransaction().apply {
            replace(R.id.flNestedFoodLayout, nestedFoodFragment)
            commit()
        }

        searchView.setOnQueryTextListener(nestedFoodFragment)
    }

}