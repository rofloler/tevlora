package com.example.tevlora.repository

import com.example.tevlora.model.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository: JpaRepository<Product, Long> {

}