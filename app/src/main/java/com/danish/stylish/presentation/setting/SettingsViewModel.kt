package com.danish.stylish.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danish.stylish.domain.model.UserProfile
import com.danish.stylish.domain.repository.UserSettingsRepository
import com.danish.stylish.domain.utils.Result
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingState(
    val userProfile: UserProfile = UserProfile(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSaving: Boolean = false,
    val savedSuccess: Boolean = false,
    val profilePhotoUrl: String? = null,
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: UserSettingsRepository,
    private val firebaseAuth: FirebaseAuth,
) : ViewModel() {

    private val _state = MutableStateFlow(SettingState())
    val state = _state.asStateFlow()

    init {
        loadUserData()
    }

    private fun loadUserData() {
        val currentUser = firebaseAuth.currentUser
        val email = currentUser?.email ?: ""
        val photoUrl = currentUser?.photoUrl?.toString()

        _state.value = _state.value.copy(
            userProfile = _state.value.userProfile.copy(emailAddress = email),
            profilePhotoUrl = photoUrl
        )
    }

    fun updateUserProfile(userProfile: UserProfile) {
        viewModelScope.launch {
            try {
                val userId = firebaseAuth.currentUser?.uid

                if (userId == null) {
                    _state.value = _state.value.copy(
                        isSaving = false,
                        savedSuccess = false,
                        error = "user not logged in"
                    )
                    return@launch
                }

                _state.value = _state.value.copy(
                    isSaving = true,
                    savedSuccess = false,
                    error = null
                )

                val profileWithUserId = userProfile.copy(userId = userId)

                when (val result = settingsRepository.saveUserProfile(profileWithUserId)) {
                    is Result.Success -> {
                        _state.value = _state.value.copy(
                            userProfile = profileWithUserId,
                            savedSuccess = true,
                            isSaving = false,
                            error = null
                        )
                    }

                    is Result.Failure -> {
                        _state.value = _state.value.copy(
                            isSaving = false,
                            savedSuccess = false,
                            error = result.message
                        )
                    }

                    else -> {
                        _state.value = _state.value.copy(
                            isSaving = false,
                            savedSuccess = false,
                            error = "Unknown Error"
                        )
                    }
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isSaving = false,
                    savedSuccess = false,
                    error = e.message ?: "Failed to save profile"
                )
            }
        }
    }

    fun resetSaveSuccess() {
        _state.value = _state.value.copy(savedSuccess = false)
    }

    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}