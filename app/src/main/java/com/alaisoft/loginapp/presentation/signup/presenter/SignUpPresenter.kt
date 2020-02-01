package com.alaisoft.loginapp.presentation.signup.presenter

import androidx.core.util.PatternsCompat
import com.alaisoft.loginapp.domain.interactor.signupinteractor.SignUpInteractor
import com.alaisoft.loginapp.presentation.signup.SignUpContract
import com.alaisoft.loginapp.presentation.signup.exceptions.FirebaseSignUpException
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SignUpPresenter(signUpInteractor: SignUpInteractor) : SignUpContract.SignUpPresenter,
    CoroutineScope {

    var view:SignUpContract.SignUpView? = null
    var signUpInteractor:SignUpInteractor? = null

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

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

    override fun detachJob() {
        coroutineContext.cancel()
    }//detachJob()

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

        launch {
            view?.showProgressBar()

            try{
                signUpInteractor?.signUp(fullname,email,password)

                if(isViewAttached()){
                    view?.hideProgressBar()
                    view?.navigateToMain()
                }
            }catch(e:FirebaseSignUpException){
                if(isViewAttached()){
                    view?.showError(e.message)
                    view?.hideProgressBar()
                }
            }

        }//launch

    }
}