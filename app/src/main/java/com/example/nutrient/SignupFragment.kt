package com.example.nutrient

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class SignupFragment : Fragment(R.layout.fragment_signup) {

    private lateinit var signupBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup, container, false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signupBtn = view.findViewById(R.id.SignupBtn)

        signupBtn.setOnClickListener {

            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.flLoginFragment, SigninFragment())
            MainActivity.CurrentPage = "SignIn"
            transaction?.commit()

            val intent = Intent (getActivity(), FoodsActivity::class.java)
            getActivity()?.startActivity(intent)
        }

    }

}