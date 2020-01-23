package com.alaisoft.loginapp.presentation.login.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.alaisoft.loginapp.R
import com.alaisoft.loginapp.base.BaseActivity
import com.alaisoft.loginapp.presentation.login.LoginContract
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), LoginContract.LoginView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        toast(this,"Prueba de login")
    }//signIn()

}//MainActivity
