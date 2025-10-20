package com.danish.stylish.di

import com.danish.stylish.domain.repository.AuthRepository
import com.danish.stylish.domain.usecase.LoginUseCase
import com.danish.stylish.domain.usecase.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    @ViewModelScoped
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase{
        return LoginUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSignUpUseCase(authRepository: AuthRepository): SignUpUseCase{
        return SignUpUseCase(authRepository)
    }
}