package com.danish.stylish.domain.usecase

import com.danish.stylish.domain.repository.AuthRepository
import com.danish.stylish.domain.utils.Result
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<String> {
        if (email.isBlank()) {
            return Result.Failure(message = "Email cannot be empty")
        }
        if (password.length < 6) {
            return Result.Failure(message = "Password must atleat 6 characters")
        }
        if (!email.contains("@") || !email.contains(".")) {
            return Result.Failure(message = "invalid email formate")
        }
        return repository.signup(email, password)
    }
}
