package com.shoppzy.data.model

import com.shoppzy.domain.model.Product
import kotlinx.serialization.Serializable

@Serializable
class DataProductModel(
    val id: Int,
    val title: String,
    val price: Double,
    val categoryId: Int,
    val description: String,
    val image: String
) {
    fun toProduct() = Product(
        id= id,
        title= title,
        price= price,
        categoryId=categoryId,
        description =description,
        image= image,
    )
}