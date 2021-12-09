package com.example.core.domain.repository

import android.net.Uri
import com.example.core.data.Resource
import com.example.core.domain.model.Desa
import kotlinx.coroutines.flow.Flow

interface IMapsRepository {
    suspend fun getLocationDesaBinaan(): Flow<Resource<List<Desa>>>
    suspend fun uploadImage(villageName:String, uri: Uri): Flow<Resource<String>>
    fun addLocationDesaBinaan(desa: Desa)
}