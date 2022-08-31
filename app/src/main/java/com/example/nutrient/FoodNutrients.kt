package com.example.nutrient

import com.google.gson.annotations.SerializedName

data class FoodNutrients(
    //@SerializedName("nutrientId") var nutrientsId: Int,
    @SerializedName("nutrientName") var nutrientName: String,
    @SerializedName("unitName") var unitName: String,
    @SerializedName("value") var value: Float
)
