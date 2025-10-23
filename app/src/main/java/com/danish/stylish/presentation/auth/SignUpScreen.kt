package com.danish.stylish.presentation.auth


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.danish.stylish.R
import com.danish.stylish.domain.utils.Result
import com.danish.stylish.navigation.Routes

@Composable
fun SignUpScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val authState by authViewModel.authState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Create an\naccount",
            fontSize = 32.sp,
            fontFamily = FontFamily(Font(R.font.montserrat_bold)),
            textAlign = TextAlign.Start,
            lineHeight = 36.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
                .padding(horizontal = 16.dp)
        )
        OutlinedTextField(
            value = userName,
            onValueChange = { userName = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(R.color.lightGray),
                focusedContainerColor = colorResource(R.color.lightGray)

            ),
            placeholder = { Text(text = "Username or Email") },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_user),
                    contentDescription = null,
                    tint = colorResource(R.color.Gray),
                    modifier = Modifier.size(24.dp)
                )
            })

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(R.color.lightGray),
                focusedContainerColor = colorResource(R.color.lightGray)

            ),
            placeholder = { Text(text = "Password") },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_password),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = colorResource(R.color.Gray)

                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_eye),
                    contentDescription = null,
                    tint = colorResource(R.color.Gray),
                    modifier = Modifier.size(24.dp)
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(R.color.lightGray),
                focusedContainerColor = colorResource(R.color.lightGray)

            ),
            placeholder = { Text(text = "ConfirmPassword") },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_password),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = colorResource(R.color.Gray)

                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_eye),
                    contentDescription = null,
                    tint = colorResource(R.color.Gray),
                    modifier = Modifier.size(24.dp)
                )
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        val annotatedText = buildAnnotatedString {
            append("By clicking the ")
            withStyle(style = SpanStyle(color = Color.Red)) {
                append("Register")
            }
            append(" button you agree\nto the public offer")
        }
        Text(
            text = annotatedText,
            fontSize = 14.sp,
            color = colorResource(R.color.Gray),
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(bottom = 24.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )

        //Button
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(12.dp))
                .height(56.dp)
                .fillMaxWidth()
                .background(color = colorResource((R.color.vivid)))
                .clickable{
                    if (userName.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()){
                        authViewModel.signup(userName, password)
                    }
                }
        ) {
            if(authState is Result.Loading){
                CircularProgressIndicator(
                    modifier = Modifier.align (Alignment.Center),
                    color = Color.White
                )
            }
            else{
                Text(
                    text = "Sign up",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.align(Alignment.Center),
                    letterSpacing = 0.5.sp
                )
            }

        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "- OR Continue with -",
            fontSize = 14.sp,
            color = colorResource(R.color.Gray),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(bottom = 32.dp)
        ) {

            Image(
                painter = painterResource(R.drawable.ic_google),
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )

            Image(
                painter = painterResource(R.drawable.ic_apple),
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )

            Image(
                painter = painterResource(R.drawable.ic_facebook),
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
        }

        Row {

            Text(
                text = "I Already Have an Account ",
                fontSize = 14.sp,
                color = colorResource(R.color.Gray)
            )

            Text(
                text = "Login",
                fontSize = 14.sp,
                color = Color.Red,
                modifier = Modifier.clickable() {
                    navController.navigate(Routes.LoginScreen)
                }
            )
        }
    }
}


@Composable
@Preview(showSystemUi = true)
fun SignUpScreenPreview() {
//    SignUpScreen(navController = rememberNavController())
}