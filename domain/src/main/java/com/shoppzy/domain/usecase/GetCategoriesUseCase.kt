package com.shoppzy.domain.usecase

import com.shoppzy.domain.repository.CategoriesRepository

class GetCategoriesUseCase(private val repository: CategoriesRepository){
    suspend fun execute() = repository.getCategories()
}