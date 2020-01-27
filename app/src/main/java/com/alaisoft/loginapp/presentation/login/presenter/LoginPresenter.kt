package com.alaisoft.loginapp.presentation.login.presenter

import com.alaisoft.loginapp.domain.interactor.logininteractor.SignInInteractor
import com.alaisoft.loginapp.presentation.login.LoginContract
import com.alaisoft.loginapp.presentation.login.LoginContract.LoginView

class LoginPresenter(signInInteractor:SignInInteractor) : LoginContract.LoginPresenter{

    var view: LoginView? = null
    var signInInteractor : SignInInteractor? = null
    init {
        this.signInInteractor = signInInteractor
    }

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
        signInInteractor?.signInWithEmailAndPassword(email,password,object: SignInInteractor.SignInCallback{
            override fun onSignInSuccess() {
                if(isViewAttached()){
                    view?.hideProgressBar()
                    view?.navigateToMain()
                }
            }//onSignInSuccess()

            override fun onSignInFailure(errorMsg: String) {
                if(isViewAttached()){
                    view?.hideProgressBar()
                    view?.showError(errorMsg)
                }
            }
        })//onSignInFailure()
    }//signInUserWithEmailAndPassword()

    override fun checkEmptyFields(email: String, password: String):Boolean {
        return email.isEmpty() || password.isEmpty()
    }//checkEmptyFields()

}