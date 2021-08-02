package com.example.bloodcellcount.datasource

import android.util.Log
import com.example.bloodcellcount.api.UserDataService
import com.example.bloodcellcount.models.User
import com.example.bloodcellcount.util.AuthUser

class UserDataSource(private val userDataService: UserDataService) {
    suspend fun getUserById(id: String): User {
        Log.d("lol","lol")
        return userDataService.getUserById("Token ${AuthUser.token}",id)
    }
}