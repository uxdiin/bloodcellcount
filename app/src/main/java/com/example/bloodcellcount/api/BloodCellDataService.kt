package com.example.bloodcellcount.api

import com.example.bloodcellcount.models.BloodCell
import com.example.bloodcellcount.models.BloodCountResponse
import com.example.bloodcellcount.models.BloodPage
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

public interface BloodCellDataService {
    @Multipart
    @POST("count")
    fun count(
        @Header("Authorization") token: String,
        @Part("name") name: RequestBody,
        @Part("backbone") backbone:RequestBody,
        @Part photo: MultipartBody.Part
    ): Call<BloodCountResponse>

    @GET("bloods")
    suspend fun bloods(
        @Header("Authorization") token: String
    ): Response<BloodPage>

    @GET("bloods/{blood_id}")
    suspend fun getBloodById(
        @Header("Authorization") token: String,
        @Path("blood_id", encoded = true)  bloodId: String
    ): Response<BloodCell>

}