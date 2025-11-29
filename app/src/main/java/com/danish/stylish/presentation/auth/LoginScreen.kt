package com.danish.stylish.presentation.auth

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.danish.stylish.R
import com.danish.stylish.domain.utils.Result
import com.danish.stylish.navigation.Routes
import com.danish.stylish.utils.GoogleSignInHelper
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException


@Composable
fun LoginScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var fontFamily = FontFamily(
        Font(R.font.montserrat_semibold, FontWeight.SemiBold),
        Font(R.font.montserrat_bold, FontWeight.Bold)
    )

    val authState by authViewModel.authState.collectAsState()

    val context = LocalContext.current
    // Google Sign-In launcher
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        android.util.Log.d("LoginScreen", "Google Sign-In result code: ${result.resultCode}")
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                android.util.Log.d("LoginScreen", "RESULT_OK received")
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    android.util.Log.d(
                        "LoginScreen",
                        "Account: ${account?.email}, ID Token: ${account?.idToken != null}"
                    )
                    if (account != null) {
                        if (account.idToken != null) {
                            authViewModel.signInWithGoogle(account)
                        } else {
                            showError = true
                            errorMessage =
                                "Google sign-in failed: No ID token received. Please check Firebase configuration."
                            android.util.Log.e("LoginScreen", "No ID token in account")
                        }
                    } else {
                        showError = true
                        errorMessage = "Google sign-in failed: Account is null"
                        android.util.Log.e("LoginScreen", "Account is null")
                    }
                } catch (e: ApiException) {
                    showError = true
                    errorMessage = "Google sign-in failed: Code ${e.statusCode} - ${e.message}"
                    android.util.Log.e(
                        "LoginScreen",
                        "ApiException: ${e.statusCode} - ${e.message}",
                        e
                    )
                }
            }

            Activity.RESULT_CANCELED -> {
                // Don't show error for user cancellation
                android.util.Log.d("LoginScreen", "User canceled Google sign-in")
            }

            else -> {
                showError = true
                errorMessage = "Google sign-in failed with result code: ${result.resultCode}"
                android.util.Log.e("LoginScreen", "Unknown result code: ${result.resultCode}")
            }
        }
    }

    // Handle authentication state
    LaunchedEffect(authState) {
        when (val currentState = authState) {
            is Result.Success -> {
//                Navigate to main screen or dashboard on successful login
                navController.navigate(Routes.ProductListScreen) {
                    popUpTo(Routes.LoginScreen) { inclusive = true }
                }
            }

            is Result.Failure -> {
                showError = true
                errorMessage = currentState.message
            }

            Result.Idle, Result.Loading -> {
                showError = false
            }
        }
    }



    Scaffold(modifier = Modifier.background(color = Color.White)) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(it)
                .padding(16.dp)
                .padding(top = 16.dp)
        ) {
            Text(
                text = "Welcome\nBack!",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily,
                lineHeight = 40.sp,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.heightIn(48.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = userName, onValueChange = { userName = it },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = colorResource(R.color.lightGray),
                        focusedContainerColor = colorResource(R.color.lightGray)
                    ),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_user),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = colorResource(R.color.Gray)
                        )
                    }, label = {
                        Text(text = "Username or Email")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(8.dp)
                )

                Spacer(modifier = Modifier.heightIn(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = colorResource(R.color.lightGray),
                        focusedContainerColor = colorResource(R.color.lightGray)
                    ),
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
                            modifier = Modifier.size(24.dp),
                            tint = colorResource(R.color.Gray)
                        )
                    },
                    label = {
                        Text(text = "Password")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(8.dp)
                )
                //ErrorMessage
                if (showError) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .padding(horizontal = 16.dp)

                ) {
                    Text(
                        text = "Forgot Password?",
                        color = colorResource(R.color.vivid),
                        modifier = Modifier.clickable() {
                            navController.navigate(Routes.ForgetScreen)
                        }
                    )
                }
                Spacer(modifier = Modifier.heightIn(36.dp))
                // Login Button
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clip(shape = RoundedCornerShape(12.dp))
                        .height(56.dp)
                        .fillMaxWidth()
                        .background(color = colorResource(R.color.vivid))
                        .clickable {
                            if (userName.isNotBlank() && password.isNotBlank()) {
                                authViewModel.login(userName, password)
                            }
                        }
                ) {
                    if (authState is Result.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.White,
                        )
                    } else {
                        Text(
                            text = "Login",
                            fontSize = 20.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.align(Alignment.Center),
                            letterSpacing = 0.5.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.heightIn(48.dp))

                Text(
                    text = "-OR Continue With-",
                    color = colorResource(R.color.Gray),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.heightIn(24.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        painter = painterResource(R.drawable.ic_google),
                        contentDescription = null,
                        Modifier
                            .size(64.dp)
                            .clickable {
                                val googleSignInClient =
                                    GoogleSignInHelper.getGoogleSignInClient(context)
                                val signInIntent = googleSignInClient.signInIntent
                                googleSignInLauncher.launch(signInIntent)
                            }
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_apple),
                        contentDescription = null,
                        Modifier.size(64.dp)
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_facebook),
                        contentDescription = null,
                        Modifier.size(64.dp)
                    )
                }
                Spacer(modifier = Modifier.heightIn(32.dp))
                Row(modifier = Modifier.padding(bottom = 32.dp)) {
                    Text(
                        text = "Create an account ",
                        color = colorResource(R.color.Gray),
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Sign Up",
                        color = colorResource(R.color.vivid),
                        fontSize = 14.sp,
                        fontWeight = FontWeight(600),
                        modifier = Modifier.clickable() {
                            navController.navigate(Routes.SignUpScreen)
                        }
                    )
                }
            }

        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun LoginScreenPreview() {
//    LoginScreen(navController = rememberNavController())
}