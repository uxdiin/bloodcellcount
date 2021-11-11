package com.example.bloodcellcount.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloodcellcount.dataclass.BackboneStatisticPage
import com.example.bloodcellcount.dataclass.BloodCell
import com.example.bloodcellcount.dataclass.numOfScan
import com.example.bloodcellcount.dataclass.User
import com.example.bloodcellcount.repository.BloodCellRepository
import com.example.bloodcellcount.repository.UserRepository
import com.example.bloodcellcount.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val userRepository: UserRepository, private val bloodCellRepository: BloodCellRepository) : ViewModel() {
    val user: MutableLiveData<Resource<Any>> = MutableLiveData()
    val blood:MutableLiveData<Resource<Any>> = MutableLiveData()
    val numOfScan: MutableLiveData<Resource<Any>> = MutableLiveData()
    val backboneStatistic: MutableLiveData<Resource<Any>> = MutableLiveData()

    fun findUser(userId: String) = viewModelScope.launch{
        user.postValue(userRepository.findUserById(userId) as Resource<Any>)
    }

    fun populateNumOfScanChart() = viewModelScope.launch{
        numOfScan.postValue(bloodCellRepository.getNumOfScanStatistic() as Resource<Any>)
    }

    fun populateBackBoneStatisticChart() = viewModelScope.launch {
        backboneStatistic.postValue(bloodCellRepository.getBackboneStatistic() as Resource<Any>)
    }

}