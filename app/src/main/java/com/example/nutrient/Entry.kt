package com.example.nutrient

import com.google.gson.annotations.SerializedName
import java.util.*

data class Entry(
    @SerializedName("description") val name: String,
    @SerializedName("servingSize") val servingSize: Int,
    @SerializedName("servingSizeUnit") val servingSizeUnit: String,
    @SerializedName("householdServingFullText") val householdServingFullText: String,
    @SerializedName("upload_date") val upload_date: Date,
)
