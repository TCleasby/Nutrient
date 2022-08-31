package com.example.nutrient

import com.google.gson.annotations.SerializedName
import java.util.*

data class FoodItem (
    @SerializedName("description") val name: String,
    var upload_date: String? = null,
    @SerializedName("servingSize") val servingSize: Int,
    @SerializedName("servingSizeUnit") val servingSizeUnit: String,
    @SerializedName("householdServingFullText") val householdServingFullText: String,
    @SerializedName("foodNutrients") val Nutrients: List<FoodNutrients>
)