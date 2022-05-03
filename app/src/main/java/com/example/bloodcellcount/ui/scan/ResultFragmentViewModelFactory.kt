package com.example.bloodcellcount.ui.scan

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bloodcellcount.api.BloodCellDataService
import com.example.bloodcellcount.datasource.BloodCellDataSource
import com.example.bloodcellcount.model.BloodModel
import com.example.bloodcellcount.repository.BloodCellRepository
import kotlinx.coroutines.Dispatchers

class ResultFragmentViewModelFactory(var application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ResultFragmentViewModel(
            repository = BloodCellRepository(
                dataSource = BloodCellDataSource(
                    bloodCellDataService = RetrofitInstance.getRetrofitInstance()
                        .create(BloodCellDataService::class.java)
                )
                , Dispatchers.IO
                ,BloodModel()
            )
        ) as T
    }
}