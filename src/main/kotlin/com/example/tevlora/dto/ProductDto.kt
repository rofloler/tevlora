package com.example.tevlora.dto

import com.example.tevlora.model.Product


data class ProductDto(
    val id : Long = 0,
    val price: Double? = null ,
    val type: String? = null ,
    val name: String? = null ,
    val description: String? = null
)

fun dtofromProduct(product: Product): ProductDto {
    return ProductDto(id = product.id, product.price, product.type, product.name , product.description)
}


fun productFromProductDto(productDto : ProductDto): Product {
    return Product(id = productDto.id, name = productDto.name, type = productDto.type, description= productDto.description, price = productDto.price)
}

