package com.example.bloodcellcount.ui.bloods

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.util.Resource
import com.example.bloodcellcount.models.BloodPage
import com.example.bloodcellcount.repository.BloodCellRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class BloodListViewModel @Inject constructor(private val bloodCellRepository: BloodCellRepository): ViewModel () {
    val bloodcellList: MutableLiveData<Resource<Response<BloodPage>>> = MutableLiveData()

    fun index() = viewModelScope.launch{
        val bloodPageResponse = bloodCellRepository.getAll()

        bloodcellList.postValue(handleIndexResponse(bloodPageResponse))
    }

    private fun handleIndexResponse(response: Resource<Response<BloodPage>>) : Resource<Response<BloodPage>>? {
        return response
    }
}