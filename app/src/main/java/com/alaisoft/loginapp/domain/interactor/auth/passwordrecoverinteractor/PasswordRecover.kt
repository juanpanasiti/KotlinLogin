package com.alaisoft.loginapp.domain.interactor.auth.passwordrecoverinteractor

interface PasswordRecover {
    suspend fun sendPasswordResetEmail(email:String)

}