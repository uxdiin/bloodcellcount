package com.example.bloodcellcount.datasource

import com.example.bloodcellcount.api.AuthDataService
import com.example.bloodcellcount.dataclass.ChangePasswordResponse
import com.example.bloodcellcount.dataclass.LoginResponse
import com.example.bloodcellcount.util.AuthUser
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart

class AuthDataSourceApi(private val authDataService: AuthDataService) {
    suspend fun apiTokenAuth(username: String, password: String): LoginResponse {
        return authDataService.apiTokenAuth(RequestBody.create(MultipartBody.FORM,username), RequestBody.create(MultipartBody.FORM, password))
    }

    suspend fun changePassword(oldPassword: String, newPassword: String): ChangePasswordResponse{
        return authDataService.changePassword("Token  ${AuthUser.token}",RequestBody.create(MultipartBody.FORM,oldPassword), RequestBody.create(MultipartBody.FORM,newPassword))
    }
}

