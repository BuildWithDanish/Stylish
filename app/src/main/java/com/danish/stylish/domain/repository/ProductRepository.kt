package com.danish.stylish.domain.repository

import com.danish.stylish.domain.model.Product
import com.danish.stylish.domain.utils.Result

interface ProductRepository {
    suspend fun getProduct(): Result<List<Product>>
    suspend fun searchProducts(query: String): Result<List<Product>>
}