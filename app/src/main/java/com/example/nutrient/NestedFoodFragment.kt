package com.example.nutrient

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NestedFoodFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var foodRecyclerView: RecyclerView
    private lateinit var foodViewModel: FoodViewModel
    private lateinit var foodViewModelFactory: FoodViewModelFactory
    private var adapter: FoodAdapter? = FoodAdapter(emptyList())
    private var callbacks: NavCallbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as NavCallbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        foodViewModelFactory = FoodViewModelFactory()
        foodViewModel = ViewModelProvider(this, foodViewModelFactory).get(FoodViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_nested_food_list, container, false)
        foodRecyclerView = view.findViewById(R.id.food_list_recycler_view)
        foodRecyclerView.layoutManager = LinearLayoutManager(context)
        foodRecyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        foodViewModel.foodItemLiveData.observe(
            viewLifecycleOwner,
            Observer { foodItems ->
                foodRecyclerView.adapter = FoodAdapter(foodItems)
                Log.d("Observer", "Received Results")
            }
        )
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        Log.d("Button Pressed", "onQueryTextSubmit")
        return false
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        if (p0 != null && p0.length > 2) {
            foodViewModel.getFoodsForSearch(p0)
        }
        return false
    }

    private inner class FoodHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var food: FoodItem

        var foodTextView: TextView = itemView.findViewById(R.id.food_name)
        var caloriesTextView: TextView = itemView.findViewById(R.id.food_calories)
        var proteinTextView: TextView = itemView.findViewById(R.id.food_protein)
        var totalFatTextView: TextView = itemView.findViewById(R.id.food_total_fat)
        var servingSizeTextView: TextView = itemView.findViewById(R.id.food_serving_size)

        lateinit var protein: String
        lateinit var calories: String
        lateinit var totalFats: String
        lateinit var servingSize: String

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(food: FoodItem){
            this.food = food

            Log.d("Food", this.food.toString())
            var nutrients: List<FoodNutrients> = food.Nutrients

            nutrients.forEach { nutrient ->
                    when (nutrient.nutrientName) {
                    "Protein" -> protein = "Protein: ".plus(nutrient.value).plus(" ").plus(nutrient.unitName)
                    "Total lipid (fat)" -> totalFats = "Total Fats: ".plus(nutrient.value).plus(" ").plus(nutrient.unitName)
                    "Energy" -> calories =  "Calories: ".plus(nutrient.value.toInt()).plus(" ")
                }
            }

            servingSize = "Serving Size: ".plus(this.food.servingSize.toString()).plus(" ").plus(this.food.servingSizeUnit)

            if(this.food.householdServingFullText != null){
                servingSize = servingSize.plus(" / ").plus(this.food.householdServingFullText)
            }

            foodTextView.text = this.food.name
            caloriesTextView.text = calories
            proteinTextView.text = protein
            totalFatTextView.text = totalFats
            servingSizeTextView.text = servingSize
        }

        override fun onClick(p0: View?) {
            callbacks?.onFoodSelected(this.food)
        }
    }

    private inner class FoodAdapter(var foods: List<FoodItem>) : RecyclerView.Adapter<FoodHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHolder {
            val view = layoutInflater.inflate(R.layout.list_item_food, parent, false)
            return FoodHolder(view)
        }

        override fun getItemCount(): Int {
            return foods.size
        }

        override fun onBindViewHolder(holder: FoodHolder, position: Int) {
            val food = foods[position]
            holder.bind(food)
        }
    }
}