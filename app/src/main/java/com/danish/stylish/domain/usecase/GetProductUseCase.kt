package com.danish.stylish.domain.usecase

import com.danish.stylish.domain.model.Product
import com.danish.stylish.domain.repository.ProductRepository
import com.danish.stylish.domain.utils.Result
import javax.inject.Inject

class GetProductUseCase @Inject constructor(private val productRepository: ProductRepository) {
    suspend operator fun invoke(): Result<List<Product>> {
        return productRepository.getProduct()
    }
}