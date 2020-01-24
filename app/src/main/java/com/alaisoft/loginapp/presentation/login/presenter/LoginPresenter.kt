package com.alaisoft.loginapp.presentation.login.presenter

import com.alaisoft.loginapp.presentation.login.LoginContract
import com.alaisoft.loginapp.presentation.login.LoginContract.LoginView

class LoginPresenter() : LoginContract.LoginPresenter{

    var view: LoginView? = null

    override fun attachView(view: LoginView) {
        this.view = view
    }//attachView()

    override fun dettachView() {
        this.view = null
    }//dettachView()

    override fun isViewAttached(): Boolean {
        return this.view != null
    }//isViewAttached()

    override fun signInUserWithEmailAndPassword(email: String, password: String) {
        view?.showProgressBar()
        view?.showError("Hola desde el presenter")
    }//signInUserWithEmailAndPassword()

    override fun checkEmptyFields(email: String, password: String):Boolean {
        return email.isEmpty() || password.isEmpty()
    }//checkEmptyFields()

}