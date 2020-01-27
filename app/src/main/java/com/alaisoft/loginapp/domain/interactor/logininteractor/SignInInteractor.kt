package com.alaisoft.loginapp.domain.interactor.logininteractor

interface SignInInteractor {

    interface SignInCallback{
        fun onSignInSuccess()
        fun onSignInFailure(errorMsg:String)
    }

    fun signInWithEmailAndPassword(email:String, password:String, listener:SignInCallback)
}