package com.example.core.domain.usecase.maps

import android.net.Uri
import com.example.core.data.Resource
import com.example.core.domain.model.Desa
import com.example.core.domain.repository.IMapsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take

class DesaBinaanInteractor(private val mapsRepo: IMapsRepository): DesaBinaanUseCase {
    override suspend fun getLocationDesaBinaan(): Flow<Resource<List<Desa>>> =
        mapsRepo.getLocationDesaBinaan()

    override suspend fun addLocationDesaBinaan(desa: Desa): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        val listDownloadUrl = arrayListOf<String>()

        desa.pictures.forEach { uriString ->
            mapsRepo.uploadImage(desa.name, Uri.parse(uriString)).take(1).collect { resource ->
                if (resource is Resource.Error) {
                    emit(Resource.Error<Boolean>(resource.message.toString()))
                } else {
                    resource.data?.let {
                        listDownloadUrl.add(it)
                    }
                }
            }
        }

        mapsRepo.addLocationDesaBinaan(desa.copy(pictures = listDownloadUrl))
        emit(Resource.Success(true))
    }
}