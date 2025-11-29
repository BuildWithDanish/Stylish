package com.danish.stylish.domain.repository

import kotlinx.coroutines.flow.Flow

interface SharedPreferenceRepository {

    val isLoggedIn: Flow<Boolean>

    val isFirstTimeLogin: Flow<Boolean>

    suspend fun setFirstTimeLogin(isFirstTimeLogin: Boolean)

    suspend fun setLoggedIn(isLoggedIn: Boolean)
}