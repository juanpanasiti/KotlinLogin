package com.alaisoft.loginapp.presentation.auth.login.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alaisoft.loginapp.R
import com.alaisoft.loginapp.base.BaseActivity
import com.alaisoft.loginapp.domain.interactor.auth.logininteractor.SignInInteractorImpl
import com.alaisoft.loginapp.presentation.auth.login.LoginContract
import com.alaisoft.loginapp.presentation.auth.login.presenter.LoginPresenter
import com.alaisoft.loginapp.presentation.auth.passwordrecover.view.PasswordRecoveryActivity
import com.alaisoft.loginapp.presentation.auth.signup.view.SignUpActivity
import com.alaisoft.loginapp.presentation.main.view.MainActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), LoginContract.LoginView {

    lateinit var presenter:LoginPresenter

    companion object{
        private const val RC_SIGN_IN = 423
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = LoginPresenter(SignInInteractorImpl())
        presenter.attachView(this)

        checkIfCurrentUser()

        btn_login.setOnClickListener {
            signIn()
        }//btn listener

        signInWithProvider()

        txt_linkSignUp.setOnClickListener {
            navigateToSignUp()
        }
        txt_pass_recovery_link.setOnClickListener {
            navigateToRecoverPassword()
        }
    }//onCreate()

    private fun checkIfCurrentUser() {
        if(FirebaseAuth.getInstance().currentUser != null)
            toast(this,"Bienvenido de nuevo ${FirebaseAuth.getInstance().currentUser?.displayName}")
            navigateToMain()
    }

    override fun getLayout(): Int {
        return R.layout.activity_login
    }//getLayout()

    override fun showError(msgError: String?) {
        toast(this,msgError)
    }//showError()

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
        btn_login.visibility = View.GONE
    }//showProgressBar()

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
        btn_login.visibility = View.VISIBLE
    }//hideProgressBar()

    override fun signIn() {
        val email = et_email.text.toString().trim()
        val password = et_password.text.toString().trim()
        if(presenter.checkEmptyFields(email,password))
            toast(this,"Uno o mas campos vacíos")
        else
            presenter.signInUserWithEmailAndPassword(email,password)
    }//signIn()

    override fun signInWithProvider() {
        val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())

        btn_login_with_google.setOnClickListener {
            startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(),
                RC_SIGN_IN)
        }//listener
    }//signInWithProviders()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                navigateToMain()
            } else {
                this.showError("Error al logear con google: ${response!!.error!!.errorCode}")
            }
        }
    }//onActivityResult()

    override fun navigateToMain() {
        val intent = Intent(this,MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun navigateToSignUp() {
        val intent = Intent(this,SignUpActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
    override fun navigateToRecoverPassword() {
        val intent = Intent(this,PasswordRecoveryActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.detachView()
        presenter.detachJob()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
        presenter.detachJob()
    }

}//LoginActivity
