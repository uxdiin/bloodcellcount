package com.example.bloodcellcount.api

import com.example.bloodcellcount.models.User
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface UserDataService {
    @GET("users/{user_id}")
    suspend fun getUserById(
        @Header("Authorization") token: String,
        @Path("user_id", encoded = true)  userId: String
    ):User
}