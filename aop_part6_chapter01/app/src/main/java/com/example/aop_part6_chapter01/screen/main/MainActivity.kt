package com.example.aop_part6_chapter01.screen.main

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.aop_part6_chapter01.R
import com.example.aop_part6_chapter01.databinding.ActivityMainBinding
import com.example.aop_part6_chapter01.screen.base.BaseActivity
import com.example.aop_part6_chapter01.screen.main.home.HomeFragment
import com.example.aop_part6_chapter01.screen.main.like.RestaurantLikeListFragment
import com.example.aop_part6_chapter01.screen.main.my.MyFragment
import com.example.aop_part6_chapter01.util.event.MenuChangeEventBus
import com.google.android.material.navigation.NavigationBarView
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(), NavigationBarView.OnItemSelectedListener {

    override val viewModel by viewModel<MainViewModel>()

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    private val menuChangeEventBus by inject<MenuChangeEventBus>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
    }

    override fun initViews() = with(binding) {
        bottomNav.setOnItemSelectedListener(this@MainActivity)
        showFragment(HomeFragment.newInstance(), HomeFragment.TAG)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_home -> showFragment(HomeFragment.newInstance(), HomeFragment.TAG)

            R.id.menu_like -> showFragment(RestaurantLikeListFragment.newInstance(), RestaurantLikeListFragment.TAG)

            R.id.menu_my -> showFragment(MyFragment.newInstance(), MyFragment.TAG)
        }
        return true
    }

    fun goToTab(mainTabMenu: MainTabMenu) {
        binding.bottomNav.selectedItemId = mainTabMenu.menuId
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        val findFragment = supportFragmentManager.findFragmentByTag(tag)

        supportFragmentManager.fragments.forEach { fm ->
            supportFragmentManager.beginTransaction().hide(fm).commitAllowingStateLoss()
        }

        findFragment?.let{
            supportFragmentManager.beginTransaction().show(it).commitAllowingStateLoss()
        } ?: kotlin.run {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, fragment, tag)
                .commitAllowingStateLoss()
        }
    }

    override fun observeData() {
        lifecycleScope.launch {
            menuChangeEventBus.mainTabMenuFlow.collect {
                goToTab(it)
            }
        }
    }

}

enum class MainTabMenu(@IdRes val menuId: Int) {
    HOME(R.id.menu_home), MY(R.id.menu_my)
}