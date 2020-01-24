package com.alaisoft.loginapp.presentation.login

interface LoginContract {
    interface LoginView {
        fun showError(msgError:String)
        fun showProgressBar()
        fun hideProgressBar()
        fun signIn()
        fun navigateToMain()
        fun navigateToRegister()
    }//LoginVIew

    interface LoginPresenter {
        fun attachView(view:LoginView)
        fun dettachView()
        fun isViewAttached():Boolean
        fun signInUserWithEmailAndPassword(email:String,password:String)
        fun checkEmptyFields(email:String,password:String):Boolean
    }//LoginPresenter

}//LoginContract