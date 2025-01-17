package com.shoppzy.domain.di

import com.shoppzy.domain.usecase.GetProductUseCase
import org.koin.dsl.module


val useCaseModule = module {
    factory { GetProductUseCase(get()) }
}