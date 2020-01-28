package com.alaisoft.loginapp.presentation.signup.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alaisoft.loginapp.R
import com.alaisoft.loginapp.base.BaseActivity
import com.alaisoft.loginapp.presentation.login.view.LoginActivity
import com.alaisoft.loginapp.presentation.main.view.HomeActivity
import com.alaisoft.loginapp.presentation.signup.SignUpContract
import com.alaisoft.loginapp.presentation.signup.presenter.SignUpPresenter
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity(), SignUpContract.SignUpView {

    lateinit var presenter:SignUpPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = SignUpPresenter()
        presenter.attachView(this)

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
