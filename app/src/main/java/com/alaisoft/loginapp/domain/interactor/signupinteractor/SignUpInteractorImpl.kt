package com.alaisoft.loginapp.domain.interactor.signupinteractor

import com.alaisoft.loginapp.presentation.signup.exceptions.FirebaseSignUpException
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class SignUpInteractorImpl:SignUpInteractor {
    suspend override fun signUp(fullname: String,email: String,password: String):Unit = suspendCancellableCoroutine {continuation ->

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            if(it.isSuccessful){
                continuation.resume(Unit)
            }else{
                continuation.resumeWithException(FirebaseSignUpException(it.exception?.message))
            }
        }

        /*FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener {itSignUp ->
            if(itSignUp.isSuccessful){
                val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(fullname).build()
                FirebaseAuth.getInstance().currentUser?.updateProfile(profileUpdates)?.addOnCompleteListener {

                }
            }else{

            }
        }//FirebaseAuth listener

         */
    }//signUp()
}