package com.danish.stylish.data.repositoryImpl

import com.danish.stylish.data.local.dao.WishListDao
import com.danish.stylish.domain.model.Product
import com.danish.stylish.domain.repository.WishListRepo
import kotlinx.coroutines.flow.Flow

class WishListRepoImp(
    private val wishListDao: WishListDao,
) : WishListRepo {
    override fun getWishListProducts(): Flow<List<Product>> {
        return wishListDao.getAllWishListItems()
    }

    override suspend fun addToWishList(product: Product) {
        wishListDao.insertWishlistItem(product)
    }

    override suspend fun removeFromWishList(productId: Int) {
        wishListDao.deleteWishlistItem(productId)
    }

    override suspend fun isInWishList(productId: Int): Boolean {
        return wishListDao.isInWishlist(productId)
    }

    override suspend fun clearWishList() {
        wishListDao.clearWishlist()
    }
}