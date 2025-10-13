package com.danish.stylish.presentation


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.serialization.builtins.serializer

@Composable
fun SocialButton(
    onClick: () -> Unit,           // ← Function parameter
    backgroundColor: Color,        // ← Color parameter
    borderColor: Color,           // ← Color parameter
    content: @Composable () -> Unit // ← Composable content slot
) {
    Box(
        modifier = Modifier
            .size(56.dp)              // Button size
            .clip(CircleShape)        // Round button
            .background(backgroundColor) // Use passed background
            .clickable { onClick() }   // Handle clicks
            .padding(1.dp)
            .clip(CircleShape)
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        content() // ← Your icon content goes here
    }
}

@Composable
@Preview
fun SocialButtonPreview(showSystemUi: Boolean = true){
    SocialButton(onClick = {},
        backgroundColor = Color.White,
        borderColor = Color.Black,
        content = {})
}