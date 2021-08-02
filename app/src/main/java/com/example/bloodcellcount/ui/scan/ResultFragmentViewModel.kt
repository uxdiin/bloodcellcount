package com.example.bloodcellcount.ui.scan

import androidx.lifecycle.ViewModel
import com.example.bloodcellcount.datasource.BloodCellDataSource
import com.example.bloodcellcount.repository.BloodCellRepository
import okhttp3.MultipartBody

class ResultFragmentViewModel(var repository: BloodCellRepository):ViewModel() {



    fun count(name: String, photo: MultipartBody.Part,backbone: String, bloodCellDataCallBack: BloodCellDataSource.BloodCellDataCallBack){
        repository.count(name,photo, backbone, bloodCellDataCallBack)
    }
}