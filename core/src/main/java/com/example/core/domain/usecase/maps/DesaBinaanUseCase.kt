package com.example.core.domain.usecase.maps

import com.example.core.data.Resource
import com.example.core.domain.model.Desa
import kotlinx.coroutines.flow.Flow

interface DesaBinaanUseCase {
    suspend fun getLocationDesaBinaan(): Flow<Resource<List<Desa>>>
    suspend fun addLocationDesaBinaan(desa: Desa): Flow<Resource<Boolean>>
}