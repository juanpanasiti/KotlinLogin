package com.alaisoft.loginapp.presentation.auth.login

interface LoginContract {
    interface LoginView {
        fun showError(msgError:String?)
        fun showProgressBar()
        fun hideProgressBar()
        fun signIn()
        fun navigateToMain()
        fun navigateToSignUp()
        fun navigateToRecoverPassword()
    }//LoginVIew

    interface LoginPresenter {
        fun attachView(view:LoginView)
        fun detachView()
        fun detachJob()
        fun isViewAttached():Boolean
        fun signInUserWithEmailAndPassword(email:String,password:String)
        fun checkEmptyFields(email:String,password:String):Boolean
    }//LoginPresenter

}//LoginContract