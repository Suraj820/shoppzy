package com.shoppzy.data.di

import com.shoppzy.data.repository.ProductRepositoryImpl
import com.shoppzy.domain.repository.ProductRepository
import org.koin.dsl.module

val  repositoryModule = module {
    single<ProductRepository> { ProductRepositoryImpl(get()) }
}
