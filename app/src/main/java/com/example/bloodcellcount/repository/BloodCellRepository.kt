package com.example.bloodcellcount.repository

import android.app.Application
import com.example.bloodcellcount.datasource.BloodCellDataSource
import okhttp3.MultipartBody

class BloodCellRepository(var dataSource: BloodCellDataSource, application: Application) {
    fun count(name: String, avatar:MultipartBody.Part, bloodCellDataCallBack: BloodCellDataSource.BloodCellDataCallBack){
        dataSource.count(name,avatar,bloodCellDataCallBack)
    }
}