package com.shoppzy.di

import com.shoppzy.ui.features.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel {
        HomeViewModel(get())
    }
}