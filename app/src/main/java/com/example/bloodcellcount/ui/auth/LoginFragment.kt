package com.example.bloodcellcount.ui.auth

import android.content.ContentValues
import android.os.Bundle
import android.text.AlteredCharSequence.make
import android.text.BoringLayout.make
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bloodcellcount.R
import com.example.bloodcellcount.databinding.FragmentLoginBinding
import com.example.bloodcellcount.models.LoginResponse
import com.example.bloodcellcount.util.AuthUser
import com.example.bloodcellcount.util.Resource
import com.google.android.material.snackbar.Snackbar.make
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var fragmentLoginBinding: FragmentLoginBinding

    private val loginViewModel:LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater,container,false)
        return fragmentLoginBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(loginViewModel.checkIfLoggedIn()){
            loadAuthUser()
            goToMainActivity()
        }
        fragmentLoginBinding.btnLogin.setOnClickListener {
            val username = fragmentLoginBinding.edtUsername.text.toString().trim()
            val password  = fragmentLoginBinding.edtPassword.text.toString().trim()
            loginViewModel.login(username,password)

        }
        loginViewModel.response.observe(viewLifecycleOwner,{resources ->
            Log.d("resources",resources.data.toString())
            when(resources){
                is Resource.Success -> {
                    resources.data?.let {  loginResponse ->
                        if(loginResponse.token!=null && loginResponse.userId != null) {
                            loginViewModel.saveToken(loginResponse.token!!)
                            loginViewModel.saveUserId(loginResponse.userId!!)
                            loadAuthUser()
                            goToMainActivity()
                        }
                    }
                }
                is Resource.Error -> {
                    resources.message?.let { message ->
                        Log.e(ContentValues.TAG, "An error occured: $message")
                        resources.data?.let {
                            Toast.makeText(requireContext(),it.non_field_errors,Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        })
    }

    private fun goToMainActivity(){
        findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
        activity?.finish()
    }

    private fun loadAuthUser(){
        AuthUser.id = loginViewModel.getUserId()
        AuthUser.token = loginViewModel.getToken()
    }
}