package com.alaisoft.loginapp.presentation.auth.passwordrecover.presenter

import com.alaisoft.loginapp.domain.interactor.auth.passwordrecoverinteractor.PasswordRecover
import com.alaisoft.loginapp.presentation.auth.passwordrecover.PasswordRecoverContract
import com.alaisoft.loginapp.presentation.auth.passwordrecover.exceptions.PasswordRecoverException
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PasswordRecoverPresenter(passwordRecoverInteractor:PasswordRecover):PasswordRecoverContract.PasswordRecoverPresenter, CoroutineScope {

    var view:PasswordRecoverContract.PasswordRecoverView? = null
    var passwordRecoverInteractor: PasswordRecover? = null
    var job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    init{
        this.passwordRecoverInteractor = passwordRecoverInteractor
    }

    override fun attachView(passwordRecoverView: PasswordRecoverContract.PasswordRecoverView) {
        this.view = passwordRecoverView
    }

    override fun detachView() {
        this.view = null
    }

    override fun detachJob() {
        coroutineContext.cancel()
    }

    override fun isViewAttached(): Boolean {
        return this.view != null
    }

    override fun sendPasswordRecover(email:String) {

        launch {
            try {
                view?.showProgressBar()
                passwordRecoverInteractor?.sendPasswordResetEmail(email)
                view?.hideProgressBar()
                view?.navigateToLogin()
            }catch (e:PasswordRecoverException){
                view?.hideProgressBar()
                view?.showError(e.message)
            }

        }
    }
}