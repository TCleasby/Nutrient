package com.example.nutrient

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.HandlerCompat.postDelayed
import kotlinx.coroutines.delay

class SingleFoodFragment(food: FoodItem) : Fragment(R.layout.fragment_single_food) {


    private var singleFoodItem: FoodItem = food
    private lateinit var foodNameTextView: TextView
    private lateinit var foodServingSizeTextView: TextView
    private lateinit var addButton: Button
    private val entryFetcher = EntryFetcher()
    private var callbacks: NavCallbacks? = null
    private lateinit var selectedDate : String

    var nestedSingleFoodFragment = NestedSingleFoodFragment(singleFoodItem.Nutrients)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as NavCallbacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_single_food, container, false)
        foodNameTextView = view.findViewById(R.id.textSingleFood)
        foodServingSizeTextView = view.findViewById(R.id.textServingSize)
        addButton = view.findViewById(R.id.addButton)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var servingSize: String

        servingSize = singleFoodItem.servingSize.toString().plus(" ").plus(singleFoodItem.servingSizeUnit)

        selectedDate = callbacks?.getSelectedDate().toString()

        if(singleFoodItem.householdServingFullText != null){
            servingSize = servingSize.plus(" / ").plus(singleFoodItem.householdServingFullText)
        }

        foodServingSizeTextView.text = servingSize
        foodNameTextView.text = singleFoodItem.name

        addButton.setOnClickListener{
            singleFoodItem.upload_date = callbacks?.getSelectedDate()
            this.activity.let { it2 -> entryFetcher.createEntry(it2?.let { it1 -> SessionManager.getAuthToken(it1) }, singleFoodItem) }
            Handler().postDelayed({
                callbacks?.onEntryChanged()
            }, 500)

        }

        childFragmentManager.beginTransaction().apply {
            replace(R.id.flNestedSingleFoodLayout, nestedSingleFoodFragment)
            commit()
        }
    }
}