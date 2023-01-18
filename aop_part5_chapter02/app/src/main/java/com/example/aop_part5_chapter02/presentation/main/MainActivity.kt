package com.example.aop_part5_chapter02.presentation.main

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.aop_part5_chapter02.R
import com.example.aop_part5_chapter02.databinding.ActivityMainBinding
import com.example.aop_part5_chapter02.presentation.BaseActivity
import com.example.aop_part5_chapter02.presentation.BaseFragment
import com.example.aop_part5_chapter02.presentation.list.ProductListFragment
import com.example.aop_part5_chapter02.presentation.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject

internal class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val viewModel by inject<MainViewModel>()

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() = with(binding) {
        bottomNav.run {
            setOnItemSelectedListener { item ->
                when(item.itemId){
                    R.id.menu_products -> {
                        changeFragment(ProductListFragment())
                    }
                    R.id.menu_profile -> {
                        changeFragment(ProfileFragment())
                    }
                }
                true
            }
        }
        changeFragment(ProductListFragment())
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun observeData() = viewModel.mainStateLiveData.observe(this) {
        when (it) {
            is MainState.RefreshOrderList -> {
                binding.bottomNav.selectedItemId = R.id.menu_profile
                val fragment = supportFragmentManager.findFragmentByTag(ProfileFragment.TAG)
                (fragment as? BaseFragment<*, *>)?.viewModel?.fetchData()
            }
        }
    }
}