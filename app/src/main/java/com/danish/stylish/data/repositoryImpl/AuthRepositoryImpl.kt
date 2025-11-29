package com.danish.stylish.data.repositoryImpl

import android.util.Log
import com.danish.stylish.domain.repository.AuthRepository
import com.danish.stylish.domain.utils.Result
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(private val firebaseAuth: FirebaseAuth) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<String> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.Success("Login Successful")
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage ?: "Unknown Error during login")
        }
    }

    override suspend fun signup(email: String, password: String): Result<String> {
        try {
            Log.d("Tag", "entered sign fun")
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                Log.d("Tag", "success")
                Result.Success("SignUp Successful")
            }
                .addOnFailureListener {
                    Log.d("Tag", "error = ${it.localizedMessage}")
                }
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage ?: "Unknown Error during signup")
        }

        return Result.Success("")

    }

    override suspend fun signInWithGoogle(account: GoogleSignInAccount): Result<String> {
        return try {
            Log.d("AuthRepositoryImp", "Starting Google sign in with account ${account.email}")
            Log.d("AuthRepositoryImp", "ID Token Available: ${account.idToken != null}")

            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            Log.d("AuthRepositoryImp", "Created Credential , signIn with Firebase")

            val authResult = firebaseAuth.signInWithCredential(credential).await()
            Result.Success("Google sign-in successful")
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Google sign-in failed: ${e.message}", e)
            Result.Failure(e.localizedMessage ?: "Unknown error during Google sign-in")
        }
    }

}