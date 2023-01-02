package com.example.aop_part4_chapter05

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.isGone
import com.example.aop_part4_chapter05.databinding.ActivitySignInBinding
import com.example.aop_part4_chapter05.utility.AuthTokenProvider
import com.example.aop_part4_chapter05.utility.RetrofitUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SignInActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var binding: ActivitySignInBinding

    private val authTokenProvider by lazy {AuthTokenProvider(this)}

    val job: Job =  Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(checkAuthCodeExist()){
            launchMainActivity()
        } else{
            initViews()
        }
    }

    private fun initViews() = with(binding) {
        loginButton.setOnClickListener {
            loginGithub()
        }
    }

    private fun launchMainActivity(){
        startActivity(Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }

    private fun checkAuthCodeExist(): Boolean = authTokenProvider.token.isNullOrEmpty().not()

    private fun loginGithub() {
        val loginUri = Uri.Builder().scheme("https").authority("github.com")
            .appendPath("login")
            .appendPath("oauth")
            .appendPath("authorize")
            .appendQueryParameter("client_id", BuildConfig.GITHUB_CLIENT_ID)
            .build()

        CustomTabsIntent.Builder().build().also {
            it.launchUrl(this, loginUri)
            launchMainActivity()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.data?.getQueryParameter("code")?.let{
            launch(coroutineContext){
                showProgress()
                getAccessToken(it)
                dismissProgress()
            }
        }
    }

    private suspend fun showProgress() = withContext(coroutineContext){
        with(binding){
            loginButton.isGone = true
            progressBar.isGone = false
            progressTextView.isGone = false
        }
    }

    private suspend fun dismissProgress() = withContext(coroutineContext){
        with(binding){
            loginButton.isGone = false
            progressBar.isGone = true
            progressTextView.isGone = true
        }
    }

    private suspend fun getAccessToken(code: String) = withContext(Dispatchers.IO){
        val response = RetrofitUtil.authApiService.getAccessToken(
            clientId = BuildConfig.GITHUB_CLIENT_ID,
            clientSecret = BuildConfig.GITHUB_CLIENT_SECRET,
            code = code
        )
        if(response.isSuccessful){
            val accessToken = response.body()?.accessToken?:""
            if (accessToken.isNotEmpty()) {
                authTokenProvider.updateToken(accessToken)
            } else{
                Toast.makeText(this@SignInActivity, "accessToken이 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }


}