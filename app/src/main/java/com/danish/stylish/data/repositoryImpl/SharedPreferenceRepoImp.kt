package com.danish.stylish.data.repositoryImpl

import com.danish.stylish.data.local.dataStore.SharedPreferenceDataStore
import com.danish.stylish.domain.repository.SharedPreferenceRepository
import kotlinx.coroutines.flow.Flow

class SharedPreferenceRepoImp(
    private val sharedPreferenceDataStore: SharedPreferenceDataStore,
) :
    SharedPreferenceRepository {
    override val isLoggedIn: Flow<Boolean> = sharedPreferenceDataStore.isLoggedIn
    override val isFirstTimeLogin: Flow<Boolean> = sharedPreferenceDataStore.isFirstTimeLogin

    override suspend fun setFirstTimeLogin(isFirstTimeLogin: Boolean) {
        sharedPreferenceDataStore.setFirstTimeLogin(isFirstTimeLogin)
    }

    override suspend fun setLoggedIn(isLoggedIn: Boolean) {
        sharedPreferenceDataStore.setLoggedIn(isLoggedIn)
    }
}