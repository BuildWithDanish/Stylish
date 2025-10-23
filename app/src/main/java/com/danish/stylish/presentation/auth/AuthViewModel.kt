package com.danish.stylish.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danish.stylish.domain.usecase.LoginUseCase
import com.danish.stylish.domain.usecase.SignUpUseCase
import com.danish.stylish.domain.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val signUseCase: SignUpUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<Result<String>>(Result.Idle)
    val authState = _authState.asStateFlow()

    fun login(email: String, password: String) {
        _authState.value = Result.Loading
        viewModelScope.launch {
            try {
                _authState.value = loginUseCase(email, password)
            } catch (e: Exception) {
                _authState.value = Result.Failure(e.localizedMessage ?: "Login Failed")
            }
        }
    }

    fun signup(email: String, password: String) {
        _authState.value = Result.Loading
        viewModelScope.launch {
            try {
                _authState.value = signUseCase(email, password)
            } catch (e: Exception) {
                _authState.value = Result.Failure(e.localizedMessage ?: "SignUp Failed")
            }
        }
    }

    /*
    fun signInWithGoogle(account: GoogleSignInAccount) {
    Log.d("AuthViewModel", "Starting google sign in ${account.email}")
    _authState.value = Result.Loading
    viewModelScope.launch {
    try {
    Log.d("AuthViewModel", "Calling GoogleSignUseCse")
    val result = GoogleSignInUseCase(account)
    Log.d("AuthViewModel", "Google sign in result: $result")
    _authState.value = result
    if (result is Result.Success) {
    Log.d("AuthViewModel", "Google sign in successful, Updating Preference")
    // Login mark kr rhe hai taki baad mai dobara login screen p na le jaye
    //                    setUserPreferenceUseCase.setLoggedIn(true)
    //                    setUserPreferenceUseCase.setFirstTimeLogin(false)
    } else if (result is Result.Failure) {
    Log.d("AuthViewModel", "Google sign in failed: ${result.message}")
    }
    } catch (e: Exception) {
    Log.d("AuthViewModel", "Exceptiion in Google sign in : ${e.localizedMessage}")
    _authState.value = Result.Failure(e.localizedMessage ?: "Google Sign In Failed")
    }
    }
    }
    */
}