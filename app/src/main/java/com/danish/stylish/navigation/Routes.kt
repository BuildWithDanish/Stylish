package com.danish.stylish.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {

    @Serializable
    object LoginScreen: Routes()

    @Serializable
    object SignUpScreen: Routes()

    @Serializable
    object ForgetScreen: Routes()

    @Serializable
    object OnBoardingScreen1: Routes()

    @Serializable
    object OnBoardingScreen2: Routes()

    @Serializable
    object OnBoardingScreen3: Routes()

    @Serializable
    object GetStartedScreen: Routes()

    @Serializable
    object SplashScreen: Routes()

    @Serializable
    object ProductListScreen: Routes()

    @Serializable
    data class ProductDetailScreen(val productId: Int): Routes()

    @Serializable
    data object WishListScreen: Routes()
}