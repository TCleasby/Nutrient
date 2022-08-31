package com.example.nutrient

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NestedEntryListFragment : Fragment() {

    private lateinit var entryRecyclerView: RecyclerView
    private val entryViewModel: EntryViewModel by viewModels()
    private var adapter: EntryAdapter? = EntryAdapter(emptyList())
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
        val view = inflater.inflate(R.layout.fragment_nested_entry_list, container, false)
        entryRecyclerView = view.findViewById(R.id.entry_list_recycler_view)
        entryRecyclerView.layoutManager = LinearLayoutManager(context)
        entryRecyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        entryViewModel.entryListLiveData.observe(
            viewLifecycleOwner,
            Observer { entries ->
                entryRecyclerView.adapter = EntryAdapter(entries)
            }
        )
    }

    private inner class EntryHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var entry: EntryItem

        var entryTextView: TextView = itemView.findViewById(R.id.food_name)
        var caloriesTextView: TextView = itemView.findViewById(R.id.food_calories)
        var proteinTextView: TextView = itemView.findViewById(R.id.food_protein)
        var totalFatTextView: TextView = itemView.findViewById(R.id.food_total_fat)
        var servingSizeTextView: TextView = itemView.findViewById(R.id.food_serving_size)

        var protein: String = ""
        var calories: String = ""
        var totalFats: String = ""
        lateinit var servingSize: String

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(entry: EntryItem){
            this.entry = entry

            Log.d("Entry", this.entry.toString())
            var nutrients: List<EntryNutrients> = entry.nutrients

            if (nutrients.size != 0) {
                nutrients.forEach { nutrient ->
                    when (nutrient.attributes.nutrientName) {
                        "Protein" -> protein = "Protein: ".plus(nutrient.attributes.value).plus(" ").plus(nutrient.attributes.unitName)
                        "Total lipid (fat)" -> totalFats = "Total Fats: ".plus(nutrient.attributes.value).plus(" ").plus(nutrient.attributes.unitName)
                        "Energy" -> calories =  "Calories: ".plus(nutrient.attributes.value.toInt()).plus(" ")
                    }
                }
            }


            servingSize = "Serving Size: ".plus(this.entry.attributes.servingSize.toString()).plus(" ").plus(this.entry.attributes.servingSizeUnit)

            if(this.entry.attributes.householdServingFullText != null){
                servingSize = servingSize.plus(" / ").plus(this.entry.attributes.householdServingFullText)
            }

            entryTextView.text = this.entry.attributes.name
            caloriesTextView.text = calories
            proteinTextView.text = protein
            totalFatTextView.text = totalFats
            servingSizeTextView.text = servingSize
        }

        override fun onClick(p0: View?) {
            Log.d("onClick", "Clicked")
            callbacks?.onEntrySelected(this.entry)
        }
    }

    private inner class EntryAdapter(var entries: List<EntryItem>) : RecyclerView.Adapter<EntryHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryHolder {
            val view = layoutInflater.inflate(R.layout.list_item_food, parent, false)
            return EntryHolder(view)
        }

        override fun getItemCount(): Int {
            return entries.size
        }

        override fun onBindViewHolder(holder: EntryHolder, position: Int) {
            val entry = entries[position]
            holder.bind(entry)
        }
    }

    fun getEntriesForDate(selectedDate : String) {
        entryViewModel.getEntriesForDate(selectedDate)
    }

}