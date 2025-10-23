package com.danish.stylish.domain.usecase

import com.danish.stylish.domain.repository.AuthRepository
import com.danish.stylish.domain.utils.Result
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: AuthRepository) {

    suspend operator fun invoke(email: String, password: String): Result<String> {
        return repository.login(email, password)
    }
}