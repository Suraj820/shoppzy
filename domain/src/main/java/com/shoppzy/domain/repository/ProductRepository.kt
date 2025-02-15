package com.shoppzy.domain.repository

import com.shoppzy.domain.model.Product
import com.shoppzy.domain.netwrok.ResultWrapper

interface ProductRepository {

    suspend fun getProduct(category: String?):ResultWrapper<List<Product>>
}