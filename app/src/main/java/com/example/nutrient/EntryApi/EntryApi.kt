package com.example.nutrient.EntryApi

import com.example.nutrient.*
import retrofit2.Call
import retrofit2.http.*

interface EntryApi {

    @Headers(
        "Accept: application/json"
    )

    @POST("/api/login/token")
    fun login(@Body request : LoginRequest): Call<LoginResponse>

    @GET("/api/login/test")
    fun validate(@Header("Authorization") token: String): Call<ValidationResponse>

    @DELETE("/api/v1/entries/{id}")
    fun deleteEntry(@Header("Authorization") token: String, @Path("id") entryID: Int): Call<EntryListResponse>

    @GET("/api/v1/entries")
    fun getEntries(@Header("Authorization") token: String,@Query("upload_date") selectedDate: String): Call<EntryListResponse>

    @POST("/api/v1/entries")
    fun createEntry(@Header("Authorization") token: String, @Body request : FoodItem): Call<EntryListResponse>
}