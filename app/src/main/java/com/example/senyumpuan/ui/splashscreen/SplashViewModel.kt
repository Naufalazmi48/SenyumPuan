package com.example.senyumpuan.ui.splashscreen

import androidx.lifecycle.ViewModel
import com.example.core.domain.usecase.user.UserUseCase

class SplashViewModel(private val useCase: UserUseCase): ViewModel() {
    fun isLoginedUser():Boolean = useCase.isLogginedUser()
}