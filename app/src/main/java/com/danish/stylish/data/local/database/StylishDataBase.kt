package com.danish.stylish.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.danish.stylish.data.local.converter.StringListConverter
import com.danish.stylish.data.local.dao.WishListDao
import com.danish.stylish.domain.model.Product

@Database(
    entities = [Product::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(StringListConverter::class)
abstract class StylishDataBase : RoomDatabase() {
    abstract fun wishListDao(): WishListDao
}