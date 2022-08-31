package com.example.nutrient

data class EntryItem (
    val attributes: Entry,
    val id: Int,
    val nutrients: List<EntryNutrients>,
)