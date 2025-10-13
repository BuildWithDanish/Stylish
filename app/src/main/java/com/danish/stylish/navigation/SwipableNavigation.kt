package com.danish.stylish.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.danish.stylish.presentation.onboardingscreen.OnBoardingScreen2
import com.danish.stylish.presentation.onboardingscreen.OnBoardingScreen3
import com.danish.stylish.presentation.onboardingscreen.OnboardingScreen1
import kotlinx.coroutines.launch


@Composable
fun SwipOnBoarding(navController: NavController) {

    val oBScreen = listOf(
        Routes.OnBoardingScreen1,
        Routes.OnBoardingScreen2,
        Routes.OnBoardingScreen3
    )
    val pagerState = rememberPagerState(pageCount = { oBScreen.size } )
    val scope = rememberCoroutineScope()


    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
               when(page){
                   0 -> OnboardingScreen1(navController)
                   1 -> OnBoardingScreen2(navController)
                   2 -> OnBoardingScreen3(navController)
               }
            }
        }
    }
}


@Composable
@Preview(showSystemUi = true)
fun SwipOnBoardingPreview() {
    SwipOnBoarding(navController = NavController(context = LocalContext.current))
}

