package com.danish.stylish.domain.usecase

import com.danish.stylish.domain.repository.SharedPreferenceRepository
import javax.inject.Inject

class SetSharedPreferenceUseCase @Inject constructor(val sharedPreferenceRepository: SharedPreferenceRepository) {

    suspend fun setFirstTimeLogin(isFirstTimeLogin: Boolean) {
        sharedPreferenceRepository.setFirstTimeLogin(isFirstTimeLogin)
    }

    suspend fun setLoggedIn(isLoggedIn: Boolean) {
        sharedPreferenceRepository.setLoggedIn(isLoggedIn)
    }
}