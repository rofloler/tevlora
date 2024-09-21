package com.example.tevlora.service

import com.example.tevlora.dto.*
import com.example.tevlora.model.Product
import com.example.tevlora.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl @Autowired constructor(private val productRepository : ProductRepository): ProductService  {

    override fun findAllProducts(): List<ProductDto> {
        return productRepository.findAll().map{ ProductDto(it.id, it.price, it.type, it.name, it.description) }
    }

    override fun findProductById(id: Long): Product? {
        return productRepository.findById(id).get()
    }

    override fun addProduct(productDto: ProductDto): Product {
        return productRepository.save(productFromProductDto(productDto))

    }

    override fun deleteProduct(productDto: ProductDto) {
        return productRepository.delete(productFromProductDto(productDto))

    }

    override fun deleteProductById(productId: Long) {
        return productRepository.deleteById(productId)
    }

    override fun updateProduct(productId: Long, productDto: ProductDto): Product? {
        return productRepository.save(productFromProductDto(productDto))
    }
}