package com.example.nutrient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SingleFoodViewModelFactory(private var food: FoodItem) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SingleFoodViewModel(food) as T
    }
}