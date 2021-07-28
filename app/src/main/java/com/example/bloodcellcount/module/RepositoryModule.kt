package com.example.submission1.module

import com.example.bloodcellcount.api.BloodCellDataService
import com.example.bloodcellcount.datasource.BloodCellDataSource
import com.example.bloodcellcount.repository.BloodCellRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {
    @Provides
    fun provideBloodCellRepository(): BloodCellRepository {
        return BloodCellRepository(BloodCellDataSource(RetrofitInstance.getRetrofitInstance().create(BloodCellDataService::class.java)))
    }
}