package com.danish.stylish.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.danish.stylish.presentation.onboardingscreen.OnBoardingScreen2
import com.danish.stylish.presentation.onboardingscreen.OnBoardingScreen3
import com.danish.stylish.presentation.onboardingscreen.OnboardingScreen1
import com.danish.stylish.presentation.onboardingscreen.PageIndicator
import kotlinx.coroutines.launch


@Composable
fun SwipOnBoarding(navController: NavController) {

    val oBScreen = listOf(
        Routes.OnBoardingScreen1,
        Routes.OnBoardingScreen2,
        Routes.OnBoardingScreen3
    )
    val pagerState = rememberPagerState(pageCount = { oBScreen.size })
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
                when (page) {
                    0 -> OnboardingScreen1(navController)
                    1 -> OnBoardingScreen2(navController)
                    2 -> OnBoardingScreen3(navController)
                }
            }
        }

        //Bottom Navigation
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            //Prev Button
            TextButton(
                onClick = {
                    scope.launch {
                        if (pagerState.currentPage > 0) {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                },
                enabled = pagerState.currentPage > 0
            ) {
                Text(
                    text = "Prev",
                    fontSize = 18.sp,
                    color = if (pagerState.currentPage > 0) Gray else Color.Transparent,
                    fontWeight = FontWeight.Medium
                )
            }

            // Page Indicator
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(oBScreen.size) {
                    PageIndicator(isActive = it == pagerState.currentPage)
                }
            }

            // Next button
            TextButton(
                onClick = {
                    scope.launch {
                        if (pagerState.currentPage < oBScreen.size - 1) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            navController.navigate(Routes.LoginScreen)
                        }
                    }
                }
            ) {
                Text(
                    text = if (pagerState.currentPage == oBScreen.size - 1) "Get Started" else "Next",
                    fontSize = 18.sp,
                    color = Color.Red,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}


@Composable
@Preview(showSystemUi = true)
fun SwipOnBoardingPreview() {
    SwipOnBoarding(navController = NavController(context = LocalContext.current))
}

