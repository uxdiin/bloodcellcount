package com.example.bloodcellcount.ui.bloods

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloodcellcount.util.Resource
import com.example.bloodcellcount.models.BloodCell
import com.example.bloodcellcount.repository.BloodCellRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class BloodDetailViewModel @Inject constructor(private val bloodCellRepository: BloodCellRepository): ViewModel() {
    val blood:MutableLiveData<Resource<Response<BloodCell>>> = MutableLiveData()

    fun find(bloodId: String)= viewModelScope.launch{
        val response = bloodCellRepository.getBloodById(bloodId)
        blood.postValue(handleFind(response))
    }

    private fun handleFind(resource: Resource<Response<BloodCell>>): Resource<Response<BloodCell>> {
        return resource
    }
}