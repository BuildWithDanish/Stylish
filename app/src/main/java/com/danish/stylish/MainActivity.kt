package com.danish.stylish

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.danish.stylish.navigation.StylishNavigation
import com.danish.stylish.presentation.auth.SignUpScreen
import com.danish.stylish.ui.theme.StylishTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StylishTheme {
                StylishNavigation()
            }
        }
    }
}

