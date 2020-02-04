package com.alaisoft.loginapp.presentation.main.presenter

import android.net.Uri
import com.alaisoft.loginapp.domain.interactor.completeuserdatainteractor.GetUserDataInteractor
import com.alaisoft.loginapp.domain.interactor.completeuserdatainteractor.GetUserDataInteractorImpl
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
        var fullname: String
        var email: String
        var profilePhotoUrl: Uri?

        launch {

            try{
                getUserDataInteractor = GetUserDataInteractorImpl()
                currentUser = getUserDataInteractor?.getCurrentUser()
                fullname = currentUser?.displayName.toString()
                email = currentUser?.email.toString()
                profilePhotoUrl = currentUser?.photoUrl

                if(isViewAttached()){
                    view?.completeCurrentUserData(fullname,email)
                    view?.setUserProfilePhoto(profilePhotoUrl)
                }
            }catch(e: FirebaseLoginException){
                if(isViewAttached()){
                    view?.showError("No se pudo obtener los datos de usuario")
                    view?.completeCurrentUserData("User Error","error@email.com")

                }
            }
        }//launch
    }//completeUserData()

}