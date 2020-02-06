package com.alaisoft.loginapp.presentation.main.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import com.alaisoft.loginapp.R
import com.alaisoft.loginapp.base.BaseActivity
import com.alaisoft.loginapp.presentation.auth.login.view.LoginActivity
import com.alaisoft.loginapp.presentation.main.MainContract
import com.alaisoft.loginapp.presentation.main.presenter.MainPresenter
import com.bumptech.glide.Glide
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.nav_header_main.view.*

class MainActivity : BaseActivity(), MainContract.MainView {

    private lateinit var appBarConfiguration: AppBarConfiguration
    var presenter = MainPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        presenter.attachView(this)
        presenter.completeUserData()
        //signOut()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
            signOut()

        }//fab.setOnClickListener
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



    }



    override fun getLayout(): Int {
        return R.layout.activity_main
    }//getLayout()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun signOut() {
        AuthUI.getInstance().signOut(this).addOnSuccessListener {itSignOut ->
            navigateToLogin()
            toast(this,"Cerrando sesión")
            navigateToLogin()
        }.addOnFailureListener {itFailure ->
            toast(this, "Ocurrió un error: ${itFailure.message}")
        }
    }

    override fun completeCurrentUserData(fullname: String?, email: String?) {
        //Asi se llega a TextView dentro de un fragment desde el activity que lo contiene
        if(fullname == "null")
            nav_view.getHeaderView(0).tv_fullname.text = "Name Error"
        else
            nav_view.getHeaderView(0).tv_fullname.text = fullname


        if(email != "null")
            nav_view.getHeaderView(0).tv_email.text = email
        else
            nav_view.getHeaderView(0).tv_email.text = "error@email.com"
    }//completeCurrentUserData


    override fun setUserProfilePhoto(uri: Uri?) {
        if(uri.toString() != "null")
            Glide.with(this).load(uri).into(nav_view.getHeaderView(0).iv_userProfilePhoto)
    }//setUserProfilePhoto()

    override fun showError(errorMsg: String) {
        toast(this,errorMsg)
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
}
