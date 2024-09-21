package com.example.tevlora.service

import com.example.tevlora.dto.ProductDto
import com.example.tevlora.model.Product


interface ProductService {
    
    fun findAllProducts(): List<ProductDto>

    fun findProductById(id : Long): Product?

    fun addProduct(product: ProductDto): Product

    fun deleteProduct(product: ProductDto)

    fun deleteProductById(productId: Long)

    fun updateProduct(productId: Long, productDto: ProductDto): Product?
}