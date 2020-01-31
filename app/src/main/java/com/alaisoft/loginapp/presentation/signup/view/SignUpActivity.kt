package com.alaisoft.loginapp.presentation.signup.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alaisoft.loginapp.R
import com.alaisoft.loginapp.base.BaseActivity
import com.alaisoft.loginapp.domain.interactor.signupinteractor.SignUpInteractorImpl
import com.alaisoft.loginapp.presentation.login.view.LoginActivity
import com.alaisoft.loginapp.presentation.main.view.HomeActivity
import com.alaisoft.loginapp.presentation.signup.SignUpContract
import com.alaisoft.loginapp.presentation.signup.presenter.SignUpPresenter
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity(), SignUpContract.SignUpView {

    lateinit var presenter:SignUpPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = SignUpPresenter(SignUpInteractorImpl())
        presenter.attachView(this)

        btn_signup.setOnClickListener {
            signUp()
        }//listener boton de registro

        txt_linkLogin.setOnClickListener {
            navigateToLogin()
        }//listener link al login
    }

    override fun getLayout(): Int {
        return R.layout.activity_sign_up
    }

    override fun navigateToMain() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun signUp() {
        val fullname:String = et_fullname.text.toString().trim()
        val email:String = et_email.text.toString().trim()
        val password1:String = et_pass1.text.toString().trim()
        val password2:String = et_pass2.text.toString().trim()
        var countErrors:Int = 0

        if(presenter.checkEmptyFullname(fullname)){
            et_fullname.error = "El nombre no puede estar vacío"
            countErrors++
        }
        if(!presenter.checkValidEmail(email)){
            et_email.error = "El E-mail es inválido"
            countErrors++
        }
        if(!presenter.checkPasswords(password1,password2)){
            et_pass1.error = "Las contraseñas no cumplen con los requisitos"
            countErrors++
        }

        if(countErrors > 0){
            lateinit var errorWord:String;
            if(countErrors > 1)
                errorWord = "errores"
            else
                errorWord = "error"
            showError("Hay $countErrors $errorWord en el formulario de registro.")
            return
        }

        presenter.signUp(fullname,email,password1)
    }

    override fun showProgressBar() {
        progress_signUp.visibility = View.VISIBLE
        btn_signup.visibility = View.GONE
    }

    override fun hideProgressBar() {
        progress_signUp.visibility = View.GONE
        btn_signup.visibility = View.VISIBLE
    }

    override fun showError(errorMsg:String) {
        toast(this,errorMsg)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.detachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}
