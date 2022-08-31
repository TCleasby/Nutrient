package com.example.nutrient

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class SingleFoodViewModel(food: FoodItem): ViewModel() {
    var singleFoodItemLiveData: FoodItem = food
}