package com.example.bloodcellcount.api

import com.example.bloodcellcount.models.LoginResponse
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part

interface AuthDataService {
    @Multipart
    @POST("api-token-auth/")
    suspend fun apiTokenAuth(
        @Part("username") username: RequestBody,
        @Part("password") password: RequestBody
    ):LoginResponse
}