package com.example.bloodcellcount.api

import com.example.bloodcellcount.dataclass.ChangePasswordResponse
import com.example.bloodcellcount.dataclass.LoginResponse
import okhttp3.RequestBody
import retrofit2.http.*

interface AuthDataService {
    @Multipart
    @POST("api-token-auth")
    suspend fun apiTokenAuth(
        @Part("username") username: RequestBody,
        @Part("password") password: RequestBody
    ):LoginResponse

    @Multipart
    @PUT("users/change-password")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Part("old_password") oldPassword: RequestBody,
        @Part("new_password") newPassword: RequestBody
    ): ChangePasswordResponse
}