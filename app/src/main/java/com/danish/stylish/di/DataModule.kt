package com.danish.stylish.di

import android.content.Context
import androidx.room.Room
import com.danish.stylish.data.local.dao.WishListDao
import com.danish.stylish.data.local.database.StylishDataBase
import com.danish.stylish.data.remote.ProductApiService
import com.danish.stylish.data.repositoryImpl.AuthRepositoryImpl
import com.danish.stylish.data.repositoryImpl.ProductRepositoryImpl
import com.danish.stylish.data.repositoryImpl.WishListRepoImp
import com.danish.stylish.domain.repository.AuthRepository
import com.danish.stylish.domain.repository.ProductRepository
import com.danish.stylish.domain.repository.WishListRepo
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            defaultRequest {
                url("https://dummyjson.com")
            }
            install(ContentNegotiation) {
                json(Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideProductRepository(apiService: ProductApiService): ProductRepository {
        return ProductRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideStylishDataBase(@ApplicationContext context: Context): StylishDataBase {
        return Room.databaseBuilder(
            context,
            StylishDataBase::class.java,
            "stylish_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    fun provideWishListDao(dataBase: StylishDataBase): WishListDao {
        return dataBase.wishListDao()
    }

    @Provides
    @Singleton
    fun provideWishListReop(wishListDao: WishListDao): WishListRepo {
        return WishListRepoImp(wishListDao)
    }
}

