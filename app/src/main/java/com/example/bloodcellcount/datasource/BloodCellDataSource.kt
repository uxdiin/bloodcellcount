package com.example.bloodcellcount.datasource

import android.util.Log
import com.example.bloodcellcount.api.BloodCellDataService
import com.example.bloodcellcount.dataclass.BloodCountResponse
import com.example.bloodcellcount.util.AuthUser
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

public class BloodCellDataSource(val bloodCellDataService: BloodCellDataService) {


    fun count(name: String, photo:MultipartBody.Part,backbone: String, bloodCellDataCallBack: BloodCellDataCallBack){
        val call: Call<BloodCountResponse> = bloodCellDataService.count("Token 2d6a98394112122ae5eec11e98385887fecf32ab",
            RequestBody.create(MultipartBody.FORM,name),
            RequestBody.create(
            MultipartBody.FORM, backbone), photo)
        val obj = object : Callback<BloodCountResponse>{
            override fun onFailure(call: Call<BloodCountResponse>, t: Throwable) {
                bloodCellDataCallBack.onError(t.message!!)
                Log.d("ERRORDATASource", t.message!!)
            }

            override fun onResponse(call: Call<BloodCountResponse>, countResponse: Response<BloodCountResponse>) {
                Log.d("response", countResponse.toString())
                if (countResponse.code()==200){
                    countResponse.body()?.let {
                        bloodCellDataCallBack.onSuccess(it)
                    }
                }else{
                    bloodCellDataCallBack.onError(countResponse.message())
                }


            }

        }
        call.enqueue(obj)
    }

    suspend fun bloods(parameters: Map<String, String>) = bloodCellDataService.bloods("Token ${AuthUser.token}", parameters)

    suspend fun getBloodById(bloodId: String) = bloodCellDataService.getBloodById("Token  ${AuthUser.token}",bloodId)

    suspend fun backBoneStatistic() = bloodCellDataService.backboneStatistic("Token ${AuthUser.token}")

    interface BloodCellDataCallBack{
        fun onSuccess(bloodCountResponse:BloodCountResponse)
        fun onError(errorMessage: String)
    }
}