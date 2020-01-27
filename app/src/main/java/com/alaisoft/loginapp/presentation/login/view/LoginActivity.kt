package com.alaisoft.loginapp.presentation.login.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.alaisoft.loginapp.R
import com.alaisoft.loginapp.base.BaseActivity
import com.alaisoft.loginapp.domain.interactor.logininteractor.SignInInteractorImpl
import com.alaisoft.loginapp.presentation.login.LoginContract
import com.alaisoft.loginapp.presentation.login.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : BaseActivity(), LoginContract.LoginView {

    lateinit var presenter:LoginPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = LoginPresenter(SignInInteractorImpl())
        presenter.attachView(this)
        btn_login.setOnClickListener {
            signIn()
        }
    }//onCreate()

    override fun getLayout(): Int {
        return R.layout.activity_main
    }//getLayout()

    override fun showError(msgError: String) {
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
        //startActivity(Intent(...)
    }

    override fun navigateToRegister() {
        //startActivity(Intent(...)
    }

}//LoginActivity
