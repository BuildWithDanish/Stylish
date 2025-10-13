package com.danish.stylish.presentation.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Composable
fun ForegtScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Forgot\npassword?",
            fontFamily = FontFamily(Font(R.font.montserrat_bold)),
            fontSize = 32.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            lineHeight = 36.sp
        )
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email, onValueChange = { email = it },
            placeholder = { Text(text = "Enter your email address") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(R.color.lightGray),
                focusedContainerColor = colorResource(R.color.lightGray)
            ),
            shape = RoundedCornerShape(8.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_email),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = colorResource(R.color.Gray)
                )
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "*",
                color = colorResource(R.color.vivid)
            )

            Text(
                text = "We will send you an message to set or \nreset your new password",
                fontSize = 14.sp,
                color = colorResource(R.color.Gray)
            )
        }

        Spacer(Modifier.height(36.dp))

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(56.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.vivid)
            )
        ) {
            Text(text = "Submit")

        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun ForgetScreenPreview() {
    ForegtScreen(navController = rememberNavController())
}