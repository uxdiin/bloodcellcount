package com.example.bloodcellcount.repository

import com.example.bloodcellcount.dataclass.BackboneStatisticPage
import com.example.bloodcellcount.util.Resource
import com.example.bloodcellcount.util.safeApiCall
import com.example.bloodcellcount.datasource.BloodCellDataSource
import com.example.bloodcellcount.dataclass.BloodCell
import com.example.bloodcellcount.dataclass.BloodPage
import com.example.bloodcellcount.dataclass.numOfScan
import com.example.bloodcellcount.model.BloodModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import java.util.HashMap




class BloodCellRepository(var dataSource: BloodCellDataSource,private val dispatcher: CoroutineDispatcher = Dispatchers.IO, private val bloodModel: BloodModel) {

    companion object{
        const val BACKBONE_RESNET50: String = "resnet50"
        const val BACKBONE_RESNET101: String = "resnet101"
        const val BACKBONE_XCEPTION: String = "xception"
        const val BACKBONE_INCEPTION_RESNETV2 = "inception_resnet_v2"

        const val ORDERING_ID_ASCENDING = "id"
        const val ORDERING_ID_DESCENDING = "-id"
    }

    fun count(name: String, photo:MultipartBody.Part,backbone: String, bloodCellDataCallBack: BloodCellDataSource.BloodCellDataCallBack){
        dataSource.count(name,photo,backbone,bloodCellDataCallBack)
    }

    suspend fun getAll(parameters: Map<String, String>): Resource<BloodPage> {
        return safeApiCall(dispatcher){
            dataSource.bloods(parameters = parameters)
        }
    }

    suspend fun getBloodById(bloodId: String): Resource<BloodCell> {
        return safeApiCall(dispatcher){
            dataSource.getBloodById(bloodId)
        }
    }

    suspend fun getNumOfScanStatistic(): Resource<numOfScan>{
        val parameters: Map<String, String> = HashMap()
        return bloodModel.makeNumOfScanStatistic(getAll(parameters))
    }

    suspend fun getBackboneStatistic(): Resource<BackboneStatisticPage> {
        return safeApiCall(dispatcher){
            dataSource.backBoneStatistic()
        }
    }
}