package com.example.nutrient

import android.content.Context
import android.content.SharedPreferences

private const val USER_TOKEN = "user_token"

object SessionManager {

    fun setAuthToken(context: Context, token: String){
        var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun getAuthToken(context: Context): String? {
        var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        return prefs.getString(USER_TOKEN, null)
    }

}