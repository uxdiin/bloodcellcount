package com.example.bloodcellcount.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloodcellcount.models.LoginResponse
import com.example.bloodcellcount.repository.AuthRepository
import com.example.bloodcellcount.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {
    val response:MutableLiveData<Resource<LoginResponse>> = MutableLiveData()

    fun login(username: String, password: String) = viewModelScope.launch{
        response.postValue(authRepository.login(username, password))
    }

    fun checkIfLoggedIn(): Boolean {
        return authRepository.getUserToken()!=null
    }

    fun saveToken(token: String){
        authRepository.saveUserToken(token)
    }

    fun saveUserId(id: String){
        authRepository.saveUserId(id)
    }

    fun getToken(): String? {
        return authRepository.getUserToken()
    }

    fun getUserId() = authRepository.getUserId()

    fun logout(){
        authRepository.clearSharedPreference()
    }

}