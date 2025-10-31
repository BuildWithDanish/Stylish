package com.danish.stylish.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danish.stylish.domain.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface WishListDao {

    @Query("SELECT * From wishlist")
    fun getAllWishListItems(): Flow<List<Product>>

    @Query("SELECT * FROM wishlist WHERE id = :productId")
    suspend fun getWishlistItem(productId: Int): Product?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWishlistItem(item: Product)

    @Query("DELETE FROM wishlist WHERE id = :productId")
    suspend fun deleteWishlistItem(productId: Int)

    @Query("DELETE FROM wishlist")
    suspend fun clearWishlist()

    @Query("SELECT EXISTS(SELECT 1 FROM wishlist WHERE id = :productId)")
    suspend fun isInWishlist(productId: Int): Boolean

    @Query("SELECT COUNT(*) FROM wishlist")
    suspend fun getWishlistCount(): Int
}