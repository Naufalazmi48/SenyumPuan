package com.example.senyumpuan.admin.ui.desa_binaan

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.Resource
import com.example.core.domain.model.Desa
import com.example.core.domain.usecase.maps.DesaBinaanUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AddDesaBinaanViewModel(private val desaBinaanUseCase: DesaBinaanUseCase): ViewModel() {
    val imagesUri = MutableLiveData<Uri>()

    private val _desa = MutableLiveData<Resource<Boolean>>()
    val desa: LiveData<Resource<Boolean>> = _desa

    fun addLocationDesa(desa: Desa) {
        viewModelScope.launch {
            desaBinaanUseCase.addLocationDesaBinaan(desa).collect {
                _desa.postValue(it)
            }
        }
    }
}