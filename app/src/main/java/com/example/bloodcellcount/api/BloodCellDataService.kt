package com.example.bloodcellcount.api

import com.example.bloodcellcount.models.BloodCell
import com.example.bloodcellcount.models.BloodResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

public interface BloodCellDataService {
    @Multipart
    @POST("count")

    fun count(
        @Header("Authorization") token: String,
        @Part("name") name: String,
        @Part("backbone") backbone:RequestBody,
        @Part photo: MultipartBody.Part
    ): Call<BloodResponse>
}