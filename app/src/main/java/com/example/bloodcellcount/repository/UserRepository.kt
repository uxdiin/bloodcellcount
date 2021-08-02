package com.example.bloodcellcount.repository

import com.example.bloodcellcount.datasource.UserDataSource
import com.example.bloodcellcount.models.User
import com.example.bloodcellcount.util.Resource
import com.example.bloodcellcount.util.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


class UserRepository(private val userDataSource: UserDataSource,private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {
    suspend fun findUserById(id: String): Resource<User> {
        return safeApiCall(dispatcher){
            userDataSource.getUserById(id)
        }
    }
}