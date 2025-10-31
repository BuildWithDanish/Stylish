package com.danish.stylish.data.remote

import com.danish.stylish.domain.model.CategoryList
import com.danish.stylish.domain.model.ProductResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import javax.inject.Inject

class ProductApiService @Inject constructor(private val client: HttpClient) {

    suspend fun getProduct(limit: Int = 0): ProductResponse {
        return client.get("products") {
            parameter("limit", limit)
        }.body()
    }

    suspend fun searchProducts(query: String): ProductResponse {
        return client.get("products/search") {
            parameter("q", query)
        }.body()
    }

    suspend fun getCategories(category: String): List<CategoryList> {
        return client.get {
            url("products/categories")
        }.body()
    }

    suspend fun getProductByCategory(category: String): ProductResponse {
        return client.get("products/category/$category").body()
    }
}