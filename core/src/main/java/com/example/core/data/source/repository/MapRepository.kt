package com.example.core.data.source.repository

import android.net.Uri
import com.example.core.data.Resource
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.domain.model.Desa
import com.example.core.domain.repository.IMapsRepository
import com.example.core.utils.FAILED_UPLOAD_IMAGE
import com.example.core.utils.Mapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class MapRepository(private val remoteDataSource: RemoteDataSource): IMapsRepository {
    override suspend fun getLocationDesaBinaan(): Flow<Resource<List<Desa>>> = flow {
        emit(Resource.Loading())
        remoteDataSource.getLocationDesaBinaan().collect {
            when (it) {
                is ApiResponse.Empty -> emit(Resource.Success(emptyList<Desa>()))
                is ApiResponse.Error -> emit(Resource.Error<List<Desa>>(it.errorMessage))
                is ApiResponse.Success -> emit(Resource.Success(Mapper.mapLocationResponseToDomain(it.data)))
            }
        }
    }

    override suspend fun uploadImage(villageName: String, uri: Uri): Flow<Resource<String>> = flow {
        remoteDataSource.uploadImage(villageName, uri).collect {
            when (it) {
                is ApiResponse.Empty -> emit(Resource.Error<String>(FAILED_UPLOAD_IMAGE))
                is ApiResponse.Error -> emit(Resource.Error<String>(it.errorMessage))
                is ApiResponse.Success -> emit(Resource.Success(it.data))
            }
        }
    }

    override fun addLocationDesaBinaan(desa: Desa) {
        remoteDataSource.addLocationDesaBinaan(desa)
    }
}