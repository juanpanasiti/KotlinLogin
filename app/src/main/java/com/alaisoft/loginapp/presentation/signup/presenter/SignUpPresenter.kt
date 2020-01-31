package com.alaisoft.loginapp.presentation.signup.presenter

import androidx.core.util.PatternsCompat
import com.alaisoft.loginapp.domain.interactor.signupinteractor.SignUpInteractor
import com.alaisoft.loginapp.presentation.signup.SignUpContract

class SignUpPresenter(signUpInteractor: SignUpInteractor) : SignUpContract.SignUpPresenter{

    var view:SignUpContract.SignUpView? = null
    var signUpInteractor:SignUpInteractor? = null

    init {
        this.signUpInteractor = signUpInteractor
    }

    override fun attachView(view: SignUpContract.SignUpView) {
        this.view = view
    }

    override fun isViewAttached(): Boolean {
        return view != null
    }

    override fun detachView() {
        this.view = null
    }

    override fun checkEmptyFullname(fullname: String): Boolean {
        return fullname.isEmpty()
    }

    override fun checkValidEmail(email: String): Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun checkPasswords(password1:String,password2:String): Boolean {
        return (password1.length >= 6) && (password1 == password2)
    }

    override fun signUp(fullname: String, email: String, password: String) {
        view?.showProgressBar()
        signUpInteractor?.signUp(fullname,email,password,object: SignUpInteractor.SignUpCallback{
            override fun onSignUpSuccess() {
                view?.hideProgressBar()
                view?.navigateToMain()
            }

            override fun onSignUpFailure(errorMsg: String) {
                view?.hideProgressBar()
                view?.showError(errorMsg)
            }
        })
    }
}