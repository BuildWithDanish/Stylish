package com.danish.stylish.domain.repository

import com.danish.stylish.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface WishListRepo {

    fun getWishListProducts(): Flow<List<Product>>
    suspend fun addToWishList(product: Product)
    suspend fun removeFromWishList(productId: Int)
    suspend fun isInWishList(productId: Int): Boolean
    suspend fun clearWishList()
}