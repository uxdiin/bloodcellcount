package com.example.bloodcellcount.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloodcellcount.models.User
import com.example.bloodcellcount.repository.UserRepository
import com.example.bloodcellcount.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    val user: MutableLiveData<Resource<User>> = MutableLiveData()

    fun findUser(userId: String) = viewModelScope.launch{
        user.postValue(userRepository.findUserById(userId))
    }

}