package com.example.aop_part3_chapter05

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.aop_part3_chapter05.DBKey.Companion.USERS
import com.example.aop_part3_chapter05.DBKey.Companion.USER_ID
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var callbackManager: CallbackManager

    private val emailEditText by lazy {
        findViewById<EditText>(R.id.emailEditText)
    }
    private val passwordEditText by lazy {
        findViewById<EditText>(R.id.passwordEditText)
    }
    private val loginButton by lazy {
        findViewById<Button>(R.id.loginButton)
    }
    private val signupButton by lazy {
        findViewById<Button>(R.id.signupButton)
    }
    private val facebookLoginButton by lazy {
        findViewById<LoginButton>(R.id.facebookLoginButton)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth
        callbackManager = CallbackManager.Factory.create()

        initLoginButton()
        initSignUpButton()
        initEditText()
        initFacebookLoginButton()
    }

    private fun initLoginButton() {
        loginButton.setOnClickListener {
            val email = getInputEmail()
            val password = getInputPassword()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if(task.isSuccessful){
                        handleSuccessLogin()
                    } else{
                        Toast.makeText(this, "???????????? ?????????????????????. ????????? ?????? ??????????????? ??????????????????.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
    private fun initSignUpButton() {
        signupButton.setOnClickListener {
            val email  = getInputEmail()
            val password = getInputPassword()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task->
                    if(task.isSuccessful){
                        Toast.makeText(this, "??????????????? ??????????????????. ????????? ????????? ?????? ?????????????????????.", Toast.LENGTH_SHORT).show()
                    } else{
                        Toast.makeText(this, "??????????????? ?????????????????????. ?????? ????????? ????????? ??? ????????????.", Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }

    private fun initEditText(){
        emailEditText.addTextChangedListener {
            val enable = emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
            loginButton.isEnabled = enable
            signupButton.isEnabled = enable
        }
        passwordEditText.addTextChangedListener {
            val enable = emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
            loginButton.isEnabled = enable
            signupButton.isEnabled = enable
        }
    }
    private fun initFacebookLoginButton(){
        facebookLoginButton.setPermissions("email", "public_profile")

        facebookLoginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult) {
                val credential = FacebookAuthProvider.getCredential(result.accessToken.token)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener(this@LoginActivity) { task->
                        if(task.isSuccessful) {
                            //val user = auth.currentUser
                            Log.d(TAG, "signInWithCredential:success")
                            handleSuccessLogin()
                        } else {
                            Toast.makeText(this@LoginActivity, "???????????? ?????????????????????.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }

            override fun onCancel() {}

            override fun onError(error: FacebookException) {
                Toast.makeText(this@LoginActivity, "???????????? ?????????????????????.", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getInputEmail(): String {
        return emailEditText.text.toString()
    }
    private fun getInputPassword(): String {
        return passwordEditText.text.toString()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
    private fun handleSuccessLogin() {
        if(auth.currentUser==null){
            Toast.makeText(this, "???????????? ?????????????????????.", Toast.LENGTH_SHORT).show()
            return
        }
        val userId = auth.currentUser?.uid.orEmpty()
        val currentUserDB = Firebase.database.reference.child(USERS).child(userId)
        val user = mutableMapOf<String, Any>()
        user[USER_ID] = userId
        currentUserDB.updateChildren(user)
        finish()
    }
}