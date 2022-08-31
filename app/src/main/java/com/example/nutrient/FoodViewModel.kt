package com.example.nutrient

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class FoodViewModel: ViewModel() {
    var foodItemLiveData: LiveData<List<FoodItem>> = MutableLiveData()

    private val foodFetcher = FoodFetcher()
    private val mutableSearchValue = MutableLiveData<String>()

    init {
        mutableSearchValue.value = ""

        foodItemLiveData = Transformations.switchMap(mutableSearchValue) {
                searchValue -> foodFetcher.fetchFoods(searchValue)
        }
    }

    fun getFoodsForSearch(searchValue : String) {
        mutableSearchValue.value = searchValue
    }
}