package com.example.senyumpuan.di

import com.example.core.domain.usecase.chat.ChatInteractor
import com.example.core.domain.usecase.chat.ChatUseCase
import com.example.core.domain.usecase.maps.DesaBinaanInteractor
import com.example.core.domain.usecase.maps.DesaBinaanUseCase
import com.example.core.domain.usecase.user.UserInteractor
import com.example.core.domain.usecase.user.UserUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<ChatUseCase> { ChatInteractor(get()) }
    factory<DesaBinaanUseCase> { DesaBinaanInteractor(get()) }
    factory<UserUseCase> { UserInteractor(get()) }
}

val viewModelModule = module {
//    viewModel { ChatViewModel(get()) }
//    viewModel { DesaBinaanViewModel(get()) }
//    viewModel { SignInViewModel(get()) }
//    viewModel { SignUpViewModel(get()) }
//    viewModel { SplashScreenViewModel(get()) }
}