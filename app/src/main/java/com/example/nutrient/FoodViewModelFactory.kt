package com.example.nutrient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FoodViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FoodViewModel() as T
    }
}