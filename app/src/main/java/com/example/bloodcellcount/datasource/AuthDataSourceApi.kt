package com.example.bloodcellcount.datasource

import android.content.ContentValues.TAG
import android.util.Log
import com.example.bloodcellcount.api.AuthDataService
import com.example.bloodcellcount.models.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class AuthDataSourceApi(private val authDataService: AuthDataService) {
    suspend fun apiTokenAuth(username: String, password: String): LoginResponse {
        return authDataService.apiTokenAuth(RequestBody.create(MultipartBody.FORM,username), RequestBody.create(MultipartBody.FORM, password))
    }
}

