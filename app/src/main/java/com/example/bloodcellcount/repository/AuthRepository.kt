package com.example.bloodcellcount.repository

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.example.bloodcellcount.util.Resource
import com.example.bloodcellcount.util.safeApiCall
import com.example.bloodcellcount.datasource.AuthDataSourceApi
import com.example.bloodcellcount.models.LoginResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class AuthRepository(private val authDataSourceApi: AuthDataSourceApi, private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
                     context: Context) {
    companion object{
        private const val PREFERENCE_NAME:String = "auth_preference"
        private const val TOKEN_KEY:String = "token_key"
        private const val ID_KEY = "id_key"
    }

    private var authSharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    @SuppressLint("CommitPrefEdits")
    fun saveUserToken(token: String){
        val editor = authSharedPreferences.edit()
        editor.putString(TOKEN_KEY,token)
        editor.apply()
    }

    fun saveUserId(id: String){
        val editor = authSharedPreferences.edit()
        editor.putString(ID_KEY,id)
        editor.apply()
    }

    fun getUserToken(): String? {
        return authSharedPreferences.getString(TOKEN_KEY,null)
    }

    fun getUserId(): String? {
        return authSharedPreferences.getString(ID_KEY,null)
    }

    fun clearSharedPreference(){
        val editor = authSharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    suspend fun login(username:String, password:String): Resource<LoginResponse> {
        return safeApiCall(dispatcher){
            authDataSourceApi.apiTokenAuth(username,password)
        }
    }
}