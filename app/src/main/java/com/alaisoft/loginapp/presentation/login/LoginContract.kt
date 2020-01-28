package com.alaisoft.loginapp.presentation.login

interface LoginContract {
    interface LoginView {
        fun showError(msgError:String)
        fun showProgressBar()
        fun hideProgressBar()
        fun signIn()
        fun navigateToMain()
        fun navigateToSignUp()
    }//LoginVIew

    interface LoginPresenter {
        fun attachView(view:LoginView)
        fun detachView()
        fun isViewAttached():Boolean
        fun signInUserWithEmailAndPassword(email:String,password:String)
        fun checkEmptyFields(email:String,password:String):Boolean
    }//LoginPresenter

}//LoginContract