package com.alaisoft.loginapp.domain.interactor.completeuserdatainteractor

import com.google.firebase.auth.FirebaseUser

interface GetUserDataInteractor {
    suspend fun getCurrentUser(): FirebaseUser?
}