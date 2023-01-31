package com.example.aop_part5_chapter07.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.aop_part5_chapter07.R
import com.example.aop_part5_chapter07.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    private val navigationController by lazy {(supportFragmentManager.findFragmentById(R.id.mainNavigationHostContainer) as NavHostFragment).navController}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.home_dest, R.id.my_page_dest))
        binding.bottomNavigationView.setupWithNavController(navigationController)
        binding.toolbar.setupWithNavController(navigationController, appBarConfiguration)
    }
}