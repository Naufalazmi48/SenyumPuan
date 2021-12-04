package com.example.senyumpuan.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.Resource
import com.example.core.domain.model.User
import com.example.core.domain.usecase.user.UserUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

enum class DashboardRoutes {
    MAP,
    CHAT
}
class DashboardViewModel(private val useCase: UserUseCase): ViewModel() {

    private val _user = MutableLiveData<Resource<User>>()
    var route: DashboardRoutes? = null

    val role = Transformations.map(_user) {
        it.data?.role
    }

    fun checkRoleUser(){
        viewModelScope.launch {
            useCase.getUser().collect {
                _user.postValue(it)
            }
        }
    }
    fun signOut(): Boolean = useCase.signOut()
}