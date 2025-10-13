package com.danish.stylish.domain.usecase

import com.danish.stylish.domain.repository.AuthRepository
import com.danish.stylish.domain.utils.Result

class LoginUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(email: String, password: String): Result<String> {
        return repository.login(email, password)
    }
}