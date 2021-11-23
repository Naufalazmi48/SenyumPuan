package com.example.core.domain.repository

import com.example.core.data.Resource
import com.example.core.domain.model.Desa
import kotlinx.coroutines.flow.Flow

interface IMapsRepository {
    suspend fun getLocationDesaBinaan(): Flow<Resource<List<Desa>>>
    fun addLocationDesaBinaan(desa: Desa)
}