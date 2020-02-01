package com.alaisoft.loginapp.presentation.auth.passwordrecover.view


import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alaisoft.loginapp.R
import com.alaisoft.loginapp.base.BaseActivity
import com.alaisoft.loginapp.domain.interactor.auth.passwordrecoverinteractor.PasswordRecoverImpl
import com.alaisoft.loginapp.presentation.auth.login.view.LoginActivity
import com.alaisoft.loginapp.presentation.auth.passwordrecover.PasswordRecoverContract
import com.alaisoft.loginapp.presentation.auth.passwordrecover.presenter.PasswordRecoverPresenter
import com.alaisoft.loginapp.presentation.auth.signup.view.SignUpActivity
import kotlinx.android.synthetic.main.activity_password_recovery.*

class PasswordRecoveryActivity : BaseActivity(), PasswordRecoverContract.PasswordRecoverView {

    lateinit var presenter: PasswordRecoverPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = PasswordRecoverPresenter(PasswordRecoverImpl())
        presenter.attachView(this)
        btn_recover_pass.setOnClickListener {
            recoverPassword()
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_password_recovery
    }

    override fun showError(msgError: String?) {
        toast(this,msgError)
    }

    override fun showProgressBar() {
        pb_passRecovery.visibility = View.VISIBLE
        btn_recover_pass.visibility = View.GONE
    }

    override fun hideProgressBar() {
        pb_passRecovery.visibility = View.GONE
        btn_recover_pass.visibility = View.VISIBLE
    }

    override fun recoverPassword() {
        val email = et_recoveryEmail.text.toString().trim()
        if(!email.isEmpty())
            presenter.sendPasswordRecover(email)
        else
            toast(this,"Ingrese un email válido")
    }

    override fun navigateToLogin() {
        val email = et_recoveryEmail.text.toString().trim()
        var intent = Intent(this,LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        toast(this,"Se envió un mail a $email")
    }

    override fun navigateToSignUp() {
        var intent = Intent(this,SignUpActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
        presenter.detachJob()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.detachView()
        presenter.detachJob()
    }
}
