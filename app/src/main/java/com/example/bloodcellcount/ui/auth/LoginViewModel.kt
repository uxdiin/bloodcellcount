package com.example.bloodcellcount.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloodcellcount.dataclass.LoginResponse
import com.example.bloodcellcount.repository.AuthRepository
import com.example.bloodcellcount.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {
    val response:MutableLiveData<Resource<Any>> = MutableLiveData()

    fun login(username: String, password: String) = viewModelScope.launch{
        response.postValue(authRepository.login(username, password) as Resource<Any>)
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

    fun savePassword(password: String){
        authRepository.savePassword(password)
    }

    fun getToken(): String? {
        return authRepository.getUserToken()
    }

    fun getUserId() = authRepository.getUserId()

    fun logout(){
        authRepository.clearSharedPreference()
    }

}