package com.alaisoft.loginapp.presentation.main

import android.net.Uri
import com.google.firebase.auth.FirebaseUser

interface MainContract {
    interface MainView {
        fun navigateToLogin()
        fun signOut()
        fun completeCurrentUserData(fullname:String?, email:String?)
        fun setUserProfilePhoto(uri: Uri?)
        fun showError(errorMsg:String)
    }

    interface MainPresenter {
        fun attachView(view:MainView)
        fun detachView()
        fun detachJob()
        fun isViewAttached():Boolean
        fun signOutSession()
        fun completeUserData()
    }
}