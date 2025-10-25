package com.danish.stylish.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.danish.stylish.data.repositoryImpl.AuthRepositoryImpl
import com.danish.stylish.domain.usecase.LoginUseCase
import com.danish.stylish.domain.usecase.SignUpUseCase
import com.danish.stylish.presentation.auth.AuthViewModel
import com.danish.stylish.presentation.auth.ForegtScreen
import com.danish.stylish.presentation.auth.LoginScreen
import com.danish.stylish.presentation.auth.SignUpScreen
import com.danish.stylish.presentation.onboardingscreen.GetStartedScreen
import com.danish.stylish.presentation.onboardingscreen.SplashScreen
import com.danish.stylish.presentation.products.ProductListScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

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
        composable <Routes.ProductListScreen>{ ProductListScreen(navController) }

    }
}