package com.example.bloodcellcount.module

import android.content.Context
import com.example.bloodcellcount.api.AuthDataService
import com.example.bloodcellcount.api.BloodCellDataService
import com.example.bloodcellcount.api.UserDataService
import com.example.bloodcellcount.datasource.AuthDataSourceApi
import com.example.bloodcellcount.datasource.BloodCellDataSource
import com.example.bloodcellcount.datasource.UserDataSource
import com.example.bloodcellcount.model.BloodModel
import com.example.bloodcellcount.repository.AuthRepository
import com.example.bloodcellcount.repository.BloodCellRepository
import com.example.bloodcellcount.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {
    @Provides
    fun provideBloodCellRepository(): BloodCellRepository {
        return BloodCellRepository(BloodCellDataSource(RetrofitInstance.getRetrofitInstance().create(BloodCellDataService::class.java)),Dispatchers.IO,BloodModel())
    }

    @Provides
    fun provideAuthRepository(@ApplicationContext appContext: Context): AuthRepository{
        return AuthRepository(AuthDataSourceApi(RetrofitInstance.getRetrofitInstance().create(AuthDataService::class.java)),Dispatchers.IO, appContext)
    }

    @Provides
    fun provideUserRepository(): UserRepository{
        return UserRepository(UserDataSource(RetrofitInstance.getRetrofitInstance().create(UserDataService::class.java)))
    }

}