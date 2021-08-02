package com.example.bloodcellcount.datasource

import android.util.Log
import com.example.bloodcellcount.api.BloodCellDataService
import com.example.bloodcellcount.models.BloodResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

public class BloodCellDataSource(val bloodCellDataService: BloodCellDataService) {


    fun count(name: String, photo:MultipartBody.Part,backbone: String, bloodCellDataCallBack: BloodCellDataCallBack){
        val call: retrofit2.Call<BloodResponse> = bloodCellDataService.count("Token 2d6a98394112122ae5eec11e98385887fecf32ab",name,
            RequestBody.create(
            okhttp3.MultipartBody.FORM, backbone), photo)
        val obj = object : Callback<BloodResponse>{
            override fun onFailure(call: Call<BloodResponse>, t: Throwable) {
                bloodCellDataCallBack.onError(t.message!!)
                Log.d("ERRORDATASource", t.message!!)
            }

            override fun onResponse(call: Call<BloodResponse>, response: Response<BloodResponse>) {
                Log.d("response", response.toString())
                if (response.code()==200){
                    response.body()?.let {
                        bloodCellDataCallBack.onSuccess(it)
                    }
                }else{
                    bloodCellDataCallBack.onError(response.message())
                }


            }

        }
        call.enqueue(obj)
    }

    interface BloodCellDataCallBack{
        fun onSuccess(bloodResponse:BloodResponse)
        fun onError(errorMessage: String)
    }
}