package com.alaisoft.loginapp.domain.interactor.signupinteractor

interface SignUpInteractor {

    suspend fun signUp(fullname:String, email:String, password:String)
}