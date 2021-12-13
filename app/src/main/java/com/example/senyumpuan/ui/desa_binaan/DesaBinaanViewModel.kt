package com.example.senyumpuan.ui.desa_binaan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.Resource
import com.example.core.domain.model.Desa
import com.example.core.domain.usecase.maps.DesaBinaanUseCase
import com.example.senyumpuan.utils.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DesaBinaanViewModel(private val desaBinaanUseCase: DesaBinaanUseCase) : ViewModel() {
    private val _listDesa = MutableLiveData<Event<Resource<List<Desa>>>>()
    val desa: LiveData<Event<Resource<List<Desa>>>> = _listDesa

    fun getLocationDesaBinaan(){
        viewModelScope.launch {
            desaBinaanUseCase.getLocationDesaBinaan().collect {
                _listDesa.postValue(Event(it))
            }
        }
    }
}