package com.example.bloodcellcount.model

import android.util.Log
import com.example.bloodcellcount.dataclass.BloodPage
import com.example.bloodcellcount.dataclass.numOfScan
import com.example.bloodcellcount.util.Resource

class BloodModel()  {
     fun makeNumOfScanStatistic(resource: Resource<BloodPage>): Resource<numOfScan> {
        when(resource){
            is Resource.Success -> {
                resource.data?.run {
                    val dashboardChart = numOfScan(this.count)
                    val result = Resource.Success(dashboardChart)
                    Log.d("result", dashboardChart.numOfImageScanned.toString())
                    return result
                }
            }
            is Resource.Error -> {
                Log.d("result", resource.message)
                return Resource.Error(null,"error")
            }
        }
        return Resource.Error(null, message = "error")
    }
}