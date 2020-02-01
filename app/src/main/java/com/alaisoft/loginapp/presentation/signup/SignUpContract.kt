package com.alaisoft.loginapp.presentation.signup

interface SignUpContract {

    interface SignUpView {
        fun navigateToMain()
        fun navigateToLogin()
        fun signUp()
        fun showProgressBar()
        fun hideProgressBar()
        fun showError(errorMsg:String?)
    }//SignUpView
    interface SignUpPresenter{
        fun attachView(view:SignUpView)
        fun isViewAttached():Boolean
        fun detachView()
        fun detachJob()
        fun checkEmptyFullname(fullname:String):Boolean//Username no vacío y de 8 caracteres mínimos
        fun checkValidEmail(email:String):Boolean//Email en formato válido y no vacío
        fun checkPasswords(password1:String,password2:String):Boolean//Contraseñas iguales, no vacías y mayores a 6 caracteres
        fun signUp(fullname:String,email:String,password:String)
    }//SignUpPresenter
}