package com.alaisoft.loginapp.domain.interactor.logininteractor

interface SignInInteractor {

    suspend fun signInWithEmailAndPassword(email:String, password:String)
}