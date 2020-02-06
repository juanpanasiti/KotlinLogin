package com.alaisoft.loginapp.domain.interactor.completeuserdatainteractor

import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser

class GetUserDataInteractorImpl : GetUserDataInteractor {
    override suspend fun getCurrentUser(): FirebaseUser? {

        var firebaseAuth: FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        return firebaseAuth.currentUser ?: throw FirebaseAuthException("Error","Error")

    }//completeData()
}