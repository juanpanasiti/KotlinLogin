package com.alaisoft.loginapp.domain.interactor.auth.passwordrecoverinteractor

import com.alaisoft.loginapp.presentation.auth.passwordrecover.exceptions.PasswordRecoverException
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class PasswordRecoverImpl: PasswordRecover {
    override suspend fun sendPasswordResetEmail(email: String): Unit = suspendCancellableCoroutine  {continuation ->
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener {
            if(it.isSuccessful){
                continuation.resume(Unit)
            }else{
                continuation.resumeWithException(PasswordRecoverException(it.exception?.message!!))
            }
        }
    }
}