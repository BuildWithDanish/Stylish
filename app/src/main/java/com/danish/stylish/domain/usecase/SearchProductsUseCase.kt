package com.danish.stylish.domain.usecase

import com.danish.stylish.domain.model.Product
import com.danish.stylish.domain.repository.ProductRepository
import com.danish.stylish.domain.utils.Result
import javax.inject.Inject

class SearchProductsUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend operator fun invoke(query: String): Result<List<Product>> {
        return if (query.isBlank()) {
            // If query is empty, return all products
            repository.getProduct()
        } else {
            // If query is not empty, search for products
            repository.searchProducts(query)
        }

    }
}