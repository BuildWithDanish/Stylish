package com.danish.stylish.domain.repository

import com.danish.stylish.domain.model.UserProfile
import com.danish.stylish.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface UserSettingsRepository {

    suspend fun saveUserProfile(userProfile: UserProfile): Result<Unit>
    suspend fun getUserProfile(userId: String): Flow<Result<UserProfile>>
    suspend fun updateUserProfile(userId: String, updates: Map<String, Any>): Result<Unit>
}