package com.alaisoft.loginapp.presentation.login

interface LoginContract {
    interface LoginView {
        fun showError(msgError:String)
        fun showProgressBar()
        fun hideProgressBar()
        fun signIn()
    }//LoginVIew


}