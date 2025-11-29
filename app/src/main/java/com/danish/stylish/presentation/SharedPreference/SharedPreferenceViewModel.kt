package com.danish.stylish.presentation.SharedPreference

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danish.stylish.domain.model.SharedPreferenceState
import com.danish.stylish.domain.usecase.GetSharedPreferenceUseCase
import com.danish.stylish.domain.usecase.SetSharedPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@HiltViewModel
class SharedPreferenceViewModel @Inject constructor(
    private val getSharedPreferenceUseCase: GetSharedPreferenceUseCase,
    private val setSharedPreferenceUseCase: SetSharedPreferenceUseCase,
) : ViewModel() {

    private val _sharedPreferenceState = MutableStateFlow(SharedPreferenceState())
    val sharedPreferenceState = _sharedPreferenceState.asStateFlow()

    init {
        observeSharedPreference()
    }

    private fun observeSharedPreference() {
        viewModelScope.launch {
            combine(
                getSharedPreferenceUseCase.isFirstTimeLogin(),
                getSharedPreferenceUseCase.isLoggedIn()
            ) { isFirstTimeLogin, isLoggedIn ->
                SharedPreferenceState(
                    isFirstTimeLogin = isFirstTimeLogin,
                    isLoggedIn = isLoggedIn,
                    isLoading = false
                )
            }.collect { newState ->
                _sharedPreferenceState.value = newState
            }
        }
    }
}