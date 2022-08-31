package com.example.nutrient

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    companion object {
        var CurrentPage = "SignIn"
    }

    val signinFragmant = SigninFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flLoginFragment, signinFragmant)
                commit()
            }
        }
    }

    override fun onBackPressed() {

        if (MainActivity.CurrentPage == "SignIn") {
            super.onBackPressed()
        } else if (MainActivity.CurrentPage == "SignUp") {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flLoginFragment, signinFragmant)
                MainActivity.CurrentPage = "SignIn"
                commit()
            }
        }
    }
}