package com.danish.stylish.utils

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

object GoogleSignInHelper {

    // This is your Web Client ID (OAuth 2.0 client ID) from Firebase Console
    private const val WEB_CLIENT_ID = ""

    fun getGoogleSignInClient(context: Context): GoogleSignInClient {
        Log.d("GoogleSignInHelper", "Creating GoogleSignInClient with web client ID: $WEB_CLIENT_ID")

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(WEB_CLIENT_ID)  // Request ID token for Firebase Auth
            .requestEmail()                  // Request email address
            .requestProfile()                // Request profile information
            .build()

        Log.d("GoogleSignInHelper", "GoogleSignInOptions configured successfully")
        return GoogleSignIn.getClient(context, gso)
    }
}