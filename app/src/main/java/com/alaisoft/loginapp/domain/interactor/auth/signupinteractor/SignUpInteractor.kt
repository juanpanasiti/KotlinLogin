package com.alaisoft.loginapp.domain.interactor.auth.signupinteractor

interface SignUpInteractor {

    suspend fun signUp(fullname:String, email:String, password:String)
}