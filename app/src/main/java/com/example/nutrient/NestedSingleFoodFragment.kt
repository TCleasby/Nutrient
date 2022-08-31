package com.example.nutrient

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class NestedSingleFoodFragment(Nutrients: List<FoodNutrients>) : Fragment() {

    private var nutrients: List<FoodNutrients> = Nutrients
    private lateinit var singleFoodRecyclerView: RecyclerView
    private var adapter: SingleFoodAdapter? = SingleFoodAdapter(nutrients)
    private var callbacks: NavCallbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as NavCallbacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_nested_single_food, container, false)
        singleFoodRecyclerView = view.findViewById(R.id.single_food_recycler_view)
        singleFoodRecyclerView.layoutManager = LinearLayoutManager(context)
        singleFoodRecyclerView.adapter = adapter

        Log.d("Food Nutrients", nutrients.toString())
        return view
    }

    private inner class SingleFoodHolder(view: View) : RecyclerView.ViewHolder(view) {

        private lateinit var nutrient: FoodNutrients

        var nutrientTextView: TextView = itemView.findViewById(R.id.nutrition_name)
        var valueTextView: TextView = itemView.findViewById(R.id.nutrition_value)

        lateinit var value: String

        fun bind(nutrient: FoodNutrients){
            this.nutrient = nutrient

            Log.d("Nutrient", this.nutrient.nutrientName.toString())

            when (this.nutrient.nutrientName) {
                "Energy" -> {
                    value = this.nutrient.value.roundToInt().toString()
                    nutrientTextView.text = "Calories"
                    valueTextView.text = value
                }
                else -> {
                    value = this.nutrient.value.toString().plus(" ").plus(this.nutrient.unitName)
                    nutrientTextView.text = this.nutrient.nutrientName
                    valueTextView.text = value
                }
            }
        }
    }

    private inner class SingleFoodAdapter(var nutrients: List<FoodNutrients>) : RecyclerView.Adapter<SingleFoodHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleFoodHolder {
            val view = layoutInflater.inflate(R.layout.list_item_single_food, parent, false)
            return SingleFoodHolder(view)
        }

        override fun getItemCount(): Int {
            return nutrients.size
        }

        override fun onBindViewHolder(holder: SingleFoodHolder, position: Int) {
            val nutrient = nutrients[position]
            holder.bind(nutrient)
        }
    }
}