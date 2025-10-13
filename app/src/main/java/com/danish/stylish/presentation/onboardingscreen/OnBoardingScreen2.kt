package com.danish.stylish.presentation.onboardingscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.danish.stylish.R
import com.danish.stylish.navigation.Routes

@Composable
fun OnBoardingScreen2(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "2/3",
                fontSize = 16.sp,
                color = Color.Gray
            )
            TextButton(onClick = { navController.navigate(Routes.LoginScreen) }) {
                Text(
                    text = "Skip",
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .size(300.dp)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_splashscreen2),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Make Payment",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit.",
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = { navController.navigate(Routes.OnBoardingScreen1) }
            ) {
                Text(
                    text = "Prev",
                    fontSize = 18.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
               PageIndicator(false)
               PageIndicator(true)
               PageIndicator(false)
            }

            TextButton(
                onClick = { navController.navigate(Routes.OnBoardingScreen3) }
            ) {
                Text(
                    text = "Next",
                    fontSize = 18.sp,
                    color = Color.Red,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }

}


@Composable
fun PageIndicator(isActive: Boolean) {
    Box(
        modifier = Modifier
            .size(
                width = if (isActive) 36.dp else 8.dp,
                height = 8.dp
            )
            .clip(CircleShape)
            .background(
                if (isActive) Color.Black else Color.LightGray
            )
    )

}

@Composable
@Preview(showSystemUi = true)
fun OnBoardingScreen2Preview() {
    OnBoardingScreen2(navController = rememberNavController())
}

