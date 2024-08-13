package com.kaankilic.foodapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kaankilic.foodapp.R
import com.kaankilic.foodapp.databinding.FragmentFoodListBinding
import com.kaankilic.foodapp.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth= Firebase.auth

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.kayitButton.setOnClickListener { login(it) }
        binding.registerButton.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }
    fun login(view: View){
        val email= binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()
        if (email.isNotEmpty()&& password.isNotEmpty()){
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                val action = LoginFragmentDirections.actionLoginFragmentToFoodListFragment2()
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.signUpFragment, true)
                    .setPopUpTo(R.id.loginFragment, true)
                    .setPopUpTo(R.id.splashScreenFragment, true)

                    .build()
                Navigation.findNavController(view).navigate(action,navOptions)

            }.addOnFailureListener {exception ->

                Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onResume() {
        super.onResume()

    }


}