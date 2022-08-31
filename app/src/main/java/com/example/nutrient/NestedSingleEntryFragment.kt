package com.example.nutrient

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class NestedSingleEntryFragment(Nutrients: List<EntryNutrients>) : Fragment() {

    private var nutrients: List<EntryNutrients> = Nutrients
    private lateinit var singleEntryRecyclerView: RecyclerView
    private var adapter: SingleEntryAdapter? = SingleEntryAdapter(nutrients)
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
        val view = inflater.inflate(R.layout.fragment_nested_single_entry, container, false)
        singleEntryRecyclerView = view.findViewById(R.id.single_entry_recycler_view)
        singleEntryRecyclerView.layoutManager = LinearLayoutManager(context)
        singleEntryRecyclerView.adapter = adapter

        return view
    }

    private inner class SingleEntryHolder(view: View) : RecyclerView.ViewHolder(view) {

        private  lateinit var nutrient: EntryNutrients

        var nutrientTextView: TextView = itemView.findViewById(R.id.nutrition_name)
        var valueTextView: TextView = itemView.findViewById(R.id.nutrition_value)

        lateinit var value: String

        fun bind(nutrient: EntryNutrients){
            this.nutrient = nutrient

            when (this.nutrient.attributes.nutrientName){
                "Energy" -> {
                    value = this.nutrient.attributes.value.roundToInt().toString()
                    nutrientTextView.text = "Calories"
                    valueTextView.text = value
                }
                else -> {
                    value = this.nutrient.attributes.value.toString().plus(" ").plus(this.nutrient.attributes.unitName)
                    nutrientTextView.text = this.nutrient.attributes.nutrientName
                    valueTextView.text = value
                }
            }
        }
    }

    private inner class SingleEntryAdapter(var nutrients: List<EntryNutrients>) : RecyclerView.Adapter<SingleEntryHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleEntryHolder {
            val view = layoutInflater.inflate(R.layout.list_item_single_food, parent, false)
            return SingleEntryHolder(view)
        }

        override fun getItemCount(): Int {
            return nutrients.size
        }

        override fun onBindViewHolder(holder: SingleEntryHolder, position: Int) {
            val nutrient = nutrients[position]
            holder.bind(nutrient)
        }
    }
}