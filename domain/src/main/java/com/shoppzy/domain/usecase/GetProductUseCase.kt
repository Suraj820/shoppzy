package com.shoppzy.domain.usecase

import com.shoppzy.domain.model.Product
import com.shoppzy.domain.netwrok.ResultWrapper
import com.shoppzy.domain.repository.ProductRepository

class GetProductUseCase(private val repository: ProductRepository) {
    suspend  fun execute(category: String?): ResultWrapper<List<Product>> {
        return repository.getProduct(category)
    }

}