package com.example.nutrient.FoodApi

import com.example.nutrient.FoodResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface FoodApi {
    @GET("/fdc/v1/foods/search?dataType=Branded&pageSize=25&pageNumber=1&api_key=eEivyGkbXyt95ekN0pquG89egd5uTCsJd8jGeW4Y")
    fun fetchFood(@Query("query") searchValue : String): Call<FoodResponse>
}