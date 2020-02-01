package com.alaisoft.loginapp.presentation.login.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alaisoft.loginapp.R
import com.alaisoft.loginapp.base.BaseActivity
import com.alaisoft.loginapp.domain.interactor.logininteractor.SignInInteractorImpl
import com.alaisoft.loginapp.presentation.login.LoginContract
import com.alaisoft.loginapp.presentation.login.presenter.LoginPresenter
import com.alaisoft.loginapp.presentation.main.view.HomeActivity
import com.alaisoft.loginapp.presentation.signup.view.SignUpActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), LoginContract.LoginView {

    lateinit var presenter:LoginPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = LoginPresenter(SignInInteractorImpl())
        presenter.attachView(this)

        btn_login.setOnClickListener {
            signIn()
        }//btn listener

        txt_linkSignUp.setOnClickListener {
            navigateToSignUp()
        }
    }//onCreate()

    override fun getLayout(): Int {
        return R.layout.activity_login
    }//getLayout()

    override fun showError(msgError: String?) {
        toast(this,msgError)
    }//showError()

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
        btn_login.isEnabled = false
    }//showProgressBar()

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
        btn_login.isEnabled = true
    }//hideProgressBar()

    override fun signIn() {
        val email = et_email.text.toString().trim()
        val password = et_password.text.toString().trim()
        if(presenter.checkEmptyFields(email,password))
            toast(this,"Uno o mas campos vacíos")
        else
            presenter.signInUserWithEmailAndPassword(email,password)
    }//signIn()

    override fun navigateToMain() {
        val intent = Intent(this,HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun navigateToSignUp() {
        val intent = Intent(this,SignUpActivity::class.java)
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
