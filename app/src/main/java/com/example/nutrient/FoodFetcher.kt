package com.example.nutrient

import com.example.nutrient.FoodApi.FoodApi
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class FoodFetcher {

    private val foodApi: FoodApi

    init {

//         Additional logging capabilities ////////////////////////////////////
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.nal.usda.gov/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

        foodApi = retrofit.create(FoodApi::class.java)
    }

    fun fetchFoods(searchValue : String): LiveData<List<FoodItem>> {
        val responseLiveData: MutableLiveData<List<FoodItem>> = MutableLiveData()
        val foodRequest: Call<FoodResponse> = foodApi.fetchFood(searchValue)

        foodRequest.enqueue(object: Callback<FoodResponse> {
            override fun onFailure(call: Call<FoodResponse>, t: Throwable) {
                Log.e("FoodFetcher: ", "Failed to fetch food", t)
            }

            override fun onResponse(call: Call<FoodResponse>, response: Response<FoodResponse>) {
                val foodResponse: FoodResponse? = response.body()
                var foodItems: List<FoodItem> = foodResponse?.foods ?: mutableListOf()
                Log.d("Food Items", foodItems.size.toString())
                responseLiveData.value = foodItems
            }
        })

        return responseLiveData
    }
}