package com.danish.stylish.data.repositoryImpl

import android.util.Log
import com.danish.stylish.data.remote.ProductApiService
import com.danish.stylish.domain.model.Product
import com.danish.stylish.domain.repository.ProductRepository
import com.danish.stylish.domain.utils.Result
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private val apiService: ProductApiService) :
    ProductRepository {
    override suspend fun getProduct(): Result<List<Product>> {
        return try {
            val response = apiService.getProduct()
            Result.Success(response.products)
        } catch (e: Exception) {
            Log.e("ProductRepository", "Error fetching products: ${e.message}", e)
            Result.Failure(e.localizedMessage ?: "Unknown error occurred")
        }
    }

    override suspend fun searchProducts(query: String): Result<List<Product>> {
        return try {
            Log.d("ProductRepository", "Searching products with query: $query")
            val response = apiService.searchProducts(query)
            Log.d("ProductRepository", "Found ${response.products.size} products")
            Result.Success(response.products)
        } catch (e: Exception) {
            Log.e("ProductRepository", "Error searching products: ${e.message}", e)
            Result.Failure(e.localizedMessage ?: "Unknown error occurred")
        }
    }
}