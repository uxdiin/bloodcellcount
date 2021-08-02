package com.example.bloodcellcount.repository

import com.example.bloodcellcount.util.Resource
import com.example.bloodcellcount.util.safeApiCall
import com.example.bloodcellcount.datasource.BloodCellDataSource
import com.example.bloodcellcount.models.BloodCell
import com.example.bloodcellcount.models.BloodPage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import retrofit2.Response

class BloodCellRepository(var dataSource: BloodCellDataSource,private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    companion object{
        val BACKBONE_RESNET50: String = "resnet50"
        val BACKBONE_RESNET101: String = "resnet101"
        val BACKBONE_XCEPTION: String = "xception"
        val BACKBONE_INCEPTION_RESNETV2 = "inception_resnet_v2"
    }

    fun count(name: String, photo:MultipartBody.Part,backbone: String, bloodCellDataCallBack: BloodCellDataSource.BloodCellDataCallBack){
        dataSource.count(name,photo,backbone,bloodCellDataCallBack)
    }

    suspend fun getAll(authToken: String = ""): Resource<Response<BloodPage>> {
        return safeApiCall(dispatcher){
            dataSource.bloods()
        }
    }

    suspend fun getBloodById(bloodId: String): Resource<Response<BloodCell>> {
        return safeApiCall(dispatcher){
            dataSource.getBloodById(bloodId)
        }
    }
}