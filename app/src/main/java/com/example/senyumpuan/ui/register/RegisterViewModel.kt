package com.example.senyumpuan.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.Resource
import com.example.core.domain.model.User
import com.example.core.domain.usecase.user.UserUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegisterViewModel(private val useCase: UserUseCase): ViewModel(){
    private val _register = MutableLiveData<Resource<Boolean>>()
    val register = _register

    fun register(user: User, password: String){
        viewModelScope.launch {
            useCase.register(user, password).collect {
                _register.postValue(it)
            }
        }
    }
}