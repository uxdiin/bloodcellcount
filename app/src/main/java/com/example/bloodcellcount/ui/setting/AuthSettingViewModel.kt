package com.example.bloodcellcount.ui.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloodcellcount.dataclass.ChangePasswordResponse
import com.example.bloodcellcount.repository.AuthRepository
import com.example.bloodcellcount.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthSettingViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {
    val changePasswordResponse: MutableLiveData<Resource<Any>> = MutableLiveData()


    fun logout() {
        authRepository.clearSharedPreference()
    }

    fun getPassword(): String? {
        return authRepository.getPassword()
    }

    fun changePassword(newPassword: String) = viewModelScope.launch{
        changePasswordResponse.postValue(authRepository.changePassword(newPassword) as Resource<Any>)
    }

    fun savePassword(password: String){
        authRepository.savePassword(password)
    }
}