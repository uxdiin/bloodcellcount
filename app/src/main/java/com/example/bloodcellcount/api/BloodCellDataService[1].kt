package com.example.bloodcellcount.api

import com.example.bloodcellcount.models.BloodCell
import com.example.bloodcellcount.models.BloodResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

public interface BloodCellDataService {
    @Multipart
    @POST("count")
    fun count(
        @Part("name") name: String,
        @Part avatar: MultipartBody.Part
    ): Call<BloodCell>
}