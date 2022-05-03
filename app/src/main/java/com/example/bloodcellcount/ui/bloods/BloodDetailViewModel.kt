package com.example.bloodcellcount.ui.bloods

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloodcellcount.util.Resource
import com.example.bloodcellcount.dataclass.BloodCell
import com.example.bloodcellcount.repository.BloodCellRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BloodDetailViewModel @Inject constructor(private val bloodCellRepository: BloodCellRepository): ViewModel() {
    val blood:MutableLiveData<Resource<Any>> = MutableLiveData()

    fun find(bloodId: String)= viewModelScope.launch{
        val response = bloodCellRepository.getBloodById(bloodId)
        blood.postValue(handleFind(response) as Resource<Any>)
    }

    private fun handleFind(resource: Resource<BloodCell>): Resource<BloodCell> {
        return resource
    }
}