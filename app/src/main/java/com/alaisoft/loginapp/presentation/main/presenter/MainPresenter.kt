package com.alaisoft.loginapp.presentation.main.presenter

import com.alaisoft.loginapp.domain.interactor.completeuserdatainteractor.GetUserDataInteractor
import com.alaisoft.loginapp.presentation.auth.login.exceptions.FirebaseLoginException
import com.alaisoft.loginapp.presentation.main.MainContract
import com.alaisoft.loginapp.presentation.main.MainContract.MainView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainPresenter: MainContract.MainPresenter, CoroutineScope {
    var view: MainView? = null
    var getUserDataInteractor: GetUserDataInteractor? = null
    var currentUser : FirebaseUser? = null

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job



    override fun attachView(view: MainContract.MainView) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun detachJob() {
        coroutineContext.cancel()
    }//detachJob()

    override fun isViewAttached(): Boolean {
        return this.view != null
    }

    override fun signOutSession() {
        //Método para cerrar la sesion de usuario
    }

    override fun completeUserData() {
        view?.showError("Logueando")
        var fullname = ""
        var email = ""

        launch {

            try{
                //currentUser = getUserDataInteractor?.getCurrentUser()
                //view?.showError("Current User: " + currentUser.toString())
                //view?.showError("UID " + currentUser?.uid.toString())
                val user = FirebaseAuth.getInstance().currentUser
                fullname = user?.displayName.toString()
                email = user?.email.toString()

                if(isViewAttached()){
                    view?.completeCurrentUserData(fullname,email)
                }
            }catch(e: FirebaseLoginException){
                if(isViewAttached()){
                    view?.showError("No se pudo obtener los datos de usuario")
                    view?.completeCurrentUserData("User Error","error@email.com")

                }
            }
        }//launch
        view?.showError("Logueado como $fullname")
    }//completeUserData()

}