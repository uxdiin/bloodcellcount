package com.example.bloodcellcount.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bloodcellcount.datasource.BloodCellDataSource
import com.example.bloodcellcount.repository.BloodCellRepository
import okhttp3.MultipartBody

class BloodCellViewModel(var repository: BloodCellRepository):ViewModel() {
    fun count(name: String, avatar: MultipartBody.Part, bloodCellDataCallBack: BloodCellDataSource.BloodCellDataCallBack){
        repository.count(name,avatar, bloodCellDataCallBack)
    }
}