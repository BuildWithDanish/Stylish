package com.danish.stylish.domain.usecase

import com.danish.stylish.domain.repository.AuthRepository
import com.danish.stylish.domain.utils.Result
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import javax.inject.Inject

class GoogleSignInUseCase @Inject constructor(private val repository: AuthRepository) {

    suspend operator fun invoke(account: GoogleSignInAccount): Result<String> {
        return repository.signInWithGoogle(account)
    }

}