package com.example.bloodcellcount.ui.bloods

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloodcellcount.util.Resource
import com.example.bloodcellcount.dataclass.BloodPage
import com.example.bloodcellcount.repository.BloodCellRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BloodListViewModel @Inject constructor(private val bloodCellRepository: BloodCellRepository): ViewModel () {
    val bloodcellList: MutableLiveData<Resource<Any>> = MutableLiveData()

    fun index(parameters: Map<String, String>) = viewModelScope.launch{
        val bloodPageResponse = bloodCellRepository.getAll(parameters)
        bloodcellList.postValue(handleIndexResponse(bloodPageResponse) as Resource<Any>)
    }

    private fun handleIndexResponse(response: Resource<BloodPage>) : Resource<BloodPage>? {
        return response
    }
}