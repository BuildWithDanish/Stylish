package com.danish.stylish.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.danish.stylish.presentation.auth.ForegtScreen
import com.danish.stylish.presentation.auth.LoginScreen
import com.danish.stylish.presentation.auth.SignUpScreen
import com.danish.stylish.presentation.onboardingscreen.GetStartedScreen
import com.danish.stylish.presentation.onboardingscreen.SplashScreen
import com.danish.stylish.presentation.products.ProductDetailScreen
import com.danish.stylish.presentation.products.ProductListScreen
import com.danish.stylish.presentation.setting.SettingScreen
import com.danish.stylish.presentation.wishlist.WishListScreen

@Composable
fun StylishNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.GetStartedScreen) {

        composable<Routes.OnBoardingScreen1> { SwipOnBoarding(navController) }
        composable<Routes.LoginScreen> { LoginScreen(navController) }
        composable<Routes.SignUpScreen> { SignUpScreen(navController) }
        composable<Routes.ForgetScreen> { ForegtScreen(navController) }
        composable<Routes.GetStartedScreen> { GetStartedScreen(navController) }
        composable<Routes.SplashScreen> { SplashScreen(navController) }
        composable<Routes.ProductListScreen> { ProductListScreen(navController) }
        composable<Routes.ProductDetailScreen> { backstackEntry ->
            val args = backstackEntry.toRoute<Routes.ProductDetailScreen>() //it convert to object
            ProductDetailScreen(
                productId = args.productId,
                navController = navController
            )
        }
        composable<Routes.WishListScreen> { WishListScreen(navController) }
        composable <Routes.SettingScreen>{ SettingScreen(navController) }
    }
}