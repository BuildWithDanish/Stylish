package com.danish.stylish.data.local.dataStore


import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("recent_search_store")

class RecentSearchDataStore(private val context: Context) {

    companion object {
        private val RECENT_SEARCH_KEY = stringSetPreferencesKey("recent_searches")
        private const val MAX_SIZE = 10
    }

    val recentSearches: Flow<List<String>> = context.dataStore.data.map { pref ->
        // Stored as Set — convert to List but preserve insertion order
        pref[RECENT_SEARCH_KEY]?.toList()?.reversed() ?: emptyList()
    }

    suspend fun addSearch(query: String) {
        context.dataStore.edit { pref ->
            val current = pref[RECENT_SEARCH_KEY]?.toMutableList() ?: mutableListOf()

            current.remove(query)          // remove if exists
            current.add(query)             // add to end → latest last

            // Keep only last MAX_SIZE items
            val trimmed = current.takeLast(MAX_SIZE)

            // Save as Set
            pref[RECENT_SEARCH_KEY] = trimmed.toSet()
        }
    }

    suspend fun removeSearch(query: String) {
        context.dataStore.edit { pref ->
            val updated = pref[RECENT_SEARCH_KEY]?.toMutableList() ?: mutableListOf()
            updated.remove(query)
            pref[RECENT_SEARCH_KEY] = updated.toSet()
        }
    }

    suspend fun clearAll() {
        context.dataStore.edit { pref ->
            pref.remove(RECENT_SEARCH_KEY)
        }
    }
}