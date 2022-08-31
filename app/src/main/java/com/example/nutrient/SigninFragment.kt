package com.example.nutrient

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer

class SigninFragment : Fragment(R.layout.fragment_signin) {

    private lateinit var signupBtn: TextView
    private lateinit var signinBtn: Button
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signupBtn = view.findViewById(R.id.SignInToSignUpBtn)
        signinBtn = view.findViewById(R.id.SigninBtn)
        emailField = view.findViewById(R.id.editTextTextSIEmail)
        passwordField = view.findViewById(R.id.editTextTextSIPassword)

        signupBtn.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.flLoginFragment, SignupFragment())
            MainActivity.CurrentPage = "SignUp"
            transaction?.commit()
        }

        signinBtn.setOnClickListener {
            var tokenLiveData = EntryFetcher().getAuthToken(emailField.text.toString(), passwordField.text.toString())

            emailField.setText("")
            passwordField.setText("")

            tokenLiveData.observe(
                viewLifecycleOwner,
                Observer { response ->
                    this.activity?.let { it1 -> SessionManager.setAuthToken(it1, response.authToken) }
                    val intent = Intent (activity, FoodsActivity::class.java)
                    startActivity(intent)
                }
            )
        }
    }

}

