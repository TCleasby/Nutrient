package com.example.nutrient

interface NavCallbacks {
    fun onFoodSelected(food: FoodItem)
    fun onEntrySelected(entry: EntryItem)
    fun setSelectedDate(date: String)
    fun getSelectedDate(): String
    fun onEntryChanged()
}