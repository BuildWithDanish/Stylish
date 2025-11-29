package com.danish.stylish.domain.usecase

import com.danish.stylish.domain.repository.SharedPreferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSharedPreferenceUseCase @Inject constructor(private val sharedPreferenceRepository: SharedPreferenceRepository) {

    fun isFirstTimeLogin(): Flow<Boolean> = sharedPreferenceRepository.isFirstTimeLogin

    fun isLoggedIn(): Flow<Boolean> = sharedPreferenceRepository.isLoggedIn
}