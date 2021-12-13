package com.example.admin.di

import com.example.admin.ui.desa_binaan.AddDesaBinaanViewModel
import com.example.admin.ui.ruang_aman.ListChatViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val adminViewModelModule = module {
    viewModel { ListChatViewModel(get()) }
    viewModel { AddDesaBinaanViewModel(get()) }
}