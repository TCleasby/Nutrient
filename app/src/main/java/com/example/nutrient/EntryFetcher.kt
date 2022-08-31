package com.example.nutrient

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nutrient.EntryApi.EntryApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EntryFetcher {

    private val entryApi: EntryApi

    init {
        Log.d("EntryAPI", "Start")
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://live.tyrenc.me/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

        entryApi = retrofit.create(EntryApi::class.java)
    }

    fun getAuthToken(email: String, password: String): LiveData<LoginResponse> {
        val request: LoginRequest = LoginRequest(email, password)
        val requestCall: Call<LoginResponse> = entryApi.login(request)
        val responseLiveData: MutableLiveData<LoginResponse> = MutableLiveData()

        requestCall.enqueue(object: Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("EntryFetcher", "Login call failed", t)
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val loginResponse = response.body()
                if (loginResponse != null) {
                    Log.d("Auth", loginResponse.authToken)
                    responseLiveData.value = loginResponse
                }
            }
        })

        return responseLiveData
    }

    fun getEntries(token: String?, selectedDate: String): LiveData<List<EntryItem>> {

        Log.d("getEntries", "Start")
        val responseLiveData: MutableLiveData<List<EntryItem>> = MutableLiveData()
        val entryRequest: Call<EntryListResponse> = entryApi.getEntries("Bearer $token", selectedDate)

        entryRequest.enqueue(object: Callback<EntryListResponse>{
            override fun onFailure(call: Call<EntryListResponse>, t: Throwable) {
                Log.e("EntryFetcher: ", "Failed to fetch entries", t)
            }

            override fun onResponse(call: Call<EntryListResponse>, response: Response<EntryListResponse>) {
                val entryListResponse: EntryListResponse? = response.body()
                val entries: List<EntryItem> = entryListResponse?.data ?: mutableListOf()
                responseLiveData.value = entries
            }
        })

        return responseLiveData
    }

    fun deleteEntry(token: String?, entry: EntryItem){
        val entryID: Int = entry.id

        val entryRequest: Call<EntryListResponse> = entryApi.deleteEntry("Bearer $token", entryID)

        entryRequest.enqueue(object : Callback<EntryListResponse>{
            override fun onFailure(call: Call<EntryListResponse>, t: Throwable) {
                Log.d("EntryFetcher: ", "Failed to delete entry", t)
            }

            override fun onResponse(call: Call<EntryListResponse>, response: Response<EntryListResponse>) {
                Log.d("EntryFetcher: ", "Deleted entry")
            }
        })
    }

    fun createEntry(token: String?, food: FoodItem){
        val entryRequest: Call<EntryListResponse> = entryApi.createEntry("Bearer $token", food)

        entryRequest.enqueue(object  : Callback<EntryListResponse>{
            override fun onResponse(call: Call<EntryListResponse>, response: Response<EntryListResponse>) {
                Log.d("EntryFetcher: ", "Created entry")
            }

            override fun onFailure(call: Call<EntryListResponse>, t: Throwable) {
                Log.d("EntryFetcher: ", "Failed to create entry", t)
            }

        })
    }
}


