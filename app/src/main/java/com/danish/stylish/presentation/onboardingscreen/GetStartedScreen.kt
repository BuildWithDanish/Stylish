package com.danish.stylish.presentation.onboardingscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.danish.stylish.R
import com.danish.stylish.navigation.Routes

@Composable
fun GetStartedScreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.ic_getstarted),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.8f),
                            Color.Black.copy(alpha = 0.8f)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(0f, Float.POSITIVE_INFINITY)
                    )
                )
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = "You want\nAuthentic, here\nyou go!",
                fontSize = 36.sp,
                color = colorResource(R.color.white),
                textAlign = TextAlign.Center,
                lineHeight = 36.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Find it here, buy it now!",
                fontSize = 16.sp,
                color = colorResource(R.color.white)
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = { navController.navigate(Routes.OnBoardingScreen1) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.vivid)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Get Started",
                    fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
                    fontSize = 24.sp
                )
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun GetStartedScreenPreview() {
    GetStartedScreen(navController = rememberNavController())
}