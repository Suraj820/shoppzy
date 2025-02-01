package com.shoppzy.data.repository

import com.shoppzy.domain.netwrok.NetworkService
import com.shoppzy.domain.netwrok.ResultWrapper
import com.shoppzy.domain.repository.CategoriesRepository

class CategoriesRepositoryImpl(private val networkService: NetworkService): CategoriesRepository {
    override suspend fun getCategories(): ResultWrapper<List<String>> {
        return networkService.getCategories()
    }
}