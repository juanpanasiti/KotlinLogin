package com.alaisoft.loginapp.presentation.auth.passwordrecover

interface PasswordRecoverContract {
    interface PasswordRecoverView{
        fun showError(msgError:String?)
        fun showProgressBar()
        fun hideProgressBar()
        fun recoverPassword()
        fun navigateToLogin()
        fun navigateToSignUp()
    }

    interface PasswordRecoverPresenter{
        fun attachView(passwordRecoverView: PasswordRecoverView)
        fun detachView()
        fun detachJob()
        fun isViewAttached():Boolean
        fun sendPasswordRecover(email:String)
    }
}