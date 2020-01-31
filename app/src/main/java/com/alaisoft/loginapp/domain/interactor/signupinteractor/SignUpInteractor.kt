package com.alaisoft.loginapp.domain.interactor.signupinteractor

interface SignUpInteractor {
    interface SignUpCallback {
        fun onSignUpSuccess()
        fun onSignUpFailure(errorMsg:String)
    }

    fun signUp(fullname:String, email:String, password:String, listener:SignUpInteractor.SignUpCallback)
}