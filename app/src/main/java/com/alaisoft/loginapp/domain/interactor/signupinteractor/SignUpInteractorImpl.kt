package com.alaisoft.loginapp.domain.interactor.signupinteractor

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class SignUpInteractorImpl:SignUpInteractor {
    override fun signUp(fullname: String,email: String,password: String,listener: SignUpInteractor.SignUpCallback) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener {itSignUp ->
            if(itSignUp.isSuccessful){
                val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(fullname).build()
                FirebaseAuth.getInstance().currentUser?.updateProfile(profileUpdates)?.addOnCompleteListener {
                    listener.onSignUpSuccess()
                }
            }else{
                listener.onSignUpFailure(itSignUp.exception?.message.toString())
            }
        }//FirebaseAuth listener
    }//signUp()
}