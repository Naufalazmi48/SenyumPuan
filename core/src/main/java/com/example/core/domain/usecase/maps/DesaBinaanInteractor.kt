package com.example.core.domain.usecase.maps

import com.example.core.data.Resource
import com.example.core.domain.model.Desa
import com.example.core.domain.repository.IMapsRepository
import kotlinx.coroutines.flow.Flow

class DesaBinaanInteractor(private val mapsRepo: IMapsRepository): DesaBinaanUseCase {
    override suspend fun getLocationDesaBinaan(): Flow<Resource<List<Desa>>> =
        mapsRepo.getLocationDesaBinaan()

    override fun addLocationDesaBinaan(desa: Desa) {
        mapsRepo.addLocationDesaBinaan(desa)
    }

}