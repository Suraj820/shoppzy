package com.shoppzy.domain.repository

import com.shoppzy.domain.netwrok.ResultWrapper

interface CategoriesRepository {
    suspend fun getCategories():ResultWrapper<List<String>>
}