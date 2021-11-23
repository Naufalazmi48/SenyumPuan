package com.example.senyumpuan.ui.sign_in

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.Resource
import com.example.core.domain.usecase.user.UserUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SignInViewModel(private val useCase: UserUseCase): ViewModel() {
    private val _login = MutableLiveData<Resource<Boolean>>()
    val login = _login

    fun login(email: String, password: String){
        viewModelScope.launch {
            useCase.login(email, password).collect {
                _login.postValue(it)
            }
        }
    }
}