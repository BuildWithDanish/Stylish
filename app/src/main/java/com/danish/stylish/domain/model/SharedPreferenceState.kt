package com.danish.stylish.domain.model

data class SharedPreferenceState(
    val isFirstTimeLogin: Boolean = true,
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = true
)