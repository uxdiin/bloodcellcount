package com.example.bloodcellcount.datasource

import android.util.Log
import com.example.bloodcellcount.api.BloodCellDataService
import com.example.bloodcellcount.models.BloodCell
import com.example.bloodcellcount.models.BloodResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Multipart
import java.lang.Exception

public class BloodCellDataSource(val bloodCellDataService: BloodCellDataService) {
    fun count(name: String, avatar:MultipartBody.Part, bloodCellDataCallBack: BloodCellDataCallBack){
        val call: retrofit2.Call<BloodCell> = bloodCellDataService.count(name, avatar)
        val obj = object : Callback<BloodCell>{
            override fun onFailure(call: Call<BloodCell>, t: Throwable) {
                bloodCellDataCallBack.onError(t.message!!)
                Log.d("ERRORDATASource", t.message!!)
            }

            override fun onResponse(call: Call<BloodCell>, response: Response<BloodCell>) {
                Log.d("response", response.toString())
                try {
                    response.body()?.let {
                        bloodCellDataCallBack.onSuccess(it)
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                    bloodCellDataCallBack.onError(e.message!!)
                }
            }

        }
        call.enqueue(obj)
    }

    interface BloodCellDataCallBack{
        fun onSuccess(bloodCell:BloodCell)
        fun onError(errorMessage: String)
    }
}