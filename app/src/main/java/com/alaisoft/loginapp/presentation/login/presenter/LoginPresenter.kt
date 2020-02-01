package com.alaisoft.loginapp.presentation.login.presenter

import com.alaisoft.loginapp.domain.interactor.logininteractor.SignInInteractor
import com.alaisoft.loginapp.presentation.login.LoginContract
import com.alaisoft.loginapp.presentation.login.LoginContract.LoginView
import com.alaisoft.loginapp.presentation.login.exceptions.FirebaseLoginException
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LoginPresenter(signInInteractor:SignInInteractor) : LoginContract.LoginPresenter, CoroutineScope {

    var view: LoginView? = null
    var signInInteractor : SignInInteractor? = null

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    init {
        this.signInInteractor = signInInteractor
    }

    override fun attachView(view: LoginView) {
        this.view = view
    }//attachView()

    override fun detachView() {
        this.view = null
    }//dettachView()

    override fun detachJob() {
        coroutineContext.cancel()
    }//detachJob()

    override fun isViewAttached(): Boolean {
        return this.view != null
    }//isViewAttached()

    override fun signInUserWithEmailAndPassword(email: String, password: String) {

        launch {
            view?.showProgressBar()

            try{
                signInInteractor?.signInWithEmailAndPassword(email,password)

                if(isViewAttached()){
                    view?.hideProgressBar()
                    view?.navigateToMain()
                }
            }catch(e:FirebaseLoginException){
                if(isViewAttached()){
                    view?.showError(e.message)
                    view?.hideProgressBar()
                }
            }

        }//launch

    }//signInUserWithEmailAndPassword()

    override fun checkEmptyFields(email: String, password: String):Boolean {
        return email.isEmpty() || password.isEmpty()
    }//checkEmptyFields()



}