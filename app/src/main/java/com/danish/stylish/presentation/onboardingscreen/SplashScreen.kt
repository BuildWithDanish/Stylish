package com.danish.stylish.presentation.onboardingscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.danish.stylish.R

@Composable
fun SplashScreen(navController: NavHostController) {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Image(painter = painterResource(id = R.drawable.ic_splash), contentDescription = null,
            Modifier.size(250.dp))
    }
}

@Composable
@Preview(showSystemUi = true)
fun SplashScreenPreview() {
    SplashScreen(navController = rememberNavController())
}