package com.danish.stylish.presentation.auth

import android.content.Context
import androidx.credentials.GetCredentialRequest
import com.danish.stylish.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption

class googleSignIn(private val context: Context) {

    // Instantiate a Google sign-in request
    val googleIdOption = GetGoogleIdOption.Builder()
        // Your server's client ID, not your Android client ID.
        .setServerClientId(context.getString(R.string.client_Id))
        // Only show accounts previously used to sign in.
        .setFilterByAuthorizedAccounts(true)
        .build()

    // Create the Credential Manager request
    val request = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()
}