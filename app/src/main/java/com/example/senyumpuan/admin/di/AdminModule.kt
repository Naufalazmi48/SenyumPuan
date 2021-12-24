package com.example.senyumpuan.admin.di

import com.example.senyumpuan.admin.ui.desa.AddDesaBinaanViewModel
import com.example.senyumpuan.admin.ui.ruang_aman.ListChatViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val adminViewModelModule = module {
    viewModel { ListChatViewModel(get()) }
    viewModel { AddDesaBinaanViewModel(get()) }
}