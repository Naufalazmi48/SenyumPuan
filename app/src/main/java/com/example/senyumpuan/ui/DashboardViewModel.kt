package com.example.senyumpuan.ui

import androidx.lifecycle.ViewModel
import com.example.core.domain.usecase.user.UserUseCase

class DashboardViewModel(private val useCase: UserUseCase): ViewModel() {
    fun signOut(): Boolean = useCase.signOut()
}