package com.example.bloodcellcount.repository

import android.app.Application
import com.example.bloodcellcount.datasource.BloodCellDataSource
import okhttp3.MultipartBody

class BloodCellRepository(var dataSource: BloodCellDataSource, application: Application) {

    companion object{
        val BACKBONE_RESNET50: String = "resnet50"
        val BACKBONE_RESNET101: String = "resnet101"
        val BACKBONE_XCEPTION: String = "xception"
        val BACKBONE_INCEPTION_RESNETV2 = "inception_resnet_v2"
    }

    fun count(name: String, photo:MultipartBody.Part,backbone: String, bloodCellDataCallBack: BloodCellDataSource.BloodCellDataCallBack){
        dataSource.count(name,photo,backbone,bloodCellDataCallBack)
    }
}