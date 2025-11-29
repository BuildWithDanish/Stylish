package com.danish.stylish.utils

import android.content.Context
import android.util.Log
import com.danish.stylish.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

object GoogleSignInHelper {

    fun getGoogleSignInClient(context: Context): GoogleSignInClient {

        // Get client id from string.xml
        val webClientId = context.getString(R.string.client_Id)

        Log.d("GoogleSignInHelper", "Creating GoogleSignInClient with web client ID: $webClientId")

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(webClientId)  // Request ID token for Firebase Auth
            .requestEmail()                  // Request email address
            .requestProfile()                // Request profile information
            .build()

        Log.d("GoogleSignInHelper", "GoogleSignInOptions configured successfully")
        return GoogleSignIn.getClient(context, gso)
    }
}