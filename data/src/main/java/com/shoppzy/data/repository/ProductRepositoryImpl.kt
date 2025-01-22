package com.shoppzy.data.repository

import com.shoppzy.domain.model.Product
import com.shoppzy.domain.netwrok.NetworkService
import com.shoppzy.domain.netwrok.ResultWrapper
import com.shoppzy.domain.repository.ProductRepository

class ProductRepositoryImpl(private val networkService: NetworkService): ProductRepository{
    override suspend fun getProduct(category: String?): ResultWrapper<List<Product>> {
        return networkService.getProducts(category)
    }
}