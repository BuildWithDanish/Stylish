package com.danish.stylish.di

import com.danish.stylish.domain.usecase.LoginUseCase
import com.danish.stylish.domain.usecase.SignUpUseCase
import com.danish.stylish.presentation.auth.AuthViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideAuthViewModel(loginUseCase: LoginUseCase, signUpUseCase: SignUpUseCase): AuthViewModel{
        return AuthViewModel(loginUseCase, signUpUseCase)
    }
}