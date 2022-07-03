package ows.kotlinstudy.deliveryapplicaiton.screen.main

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.navigation.NavigationBarView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import ows.kotlinstudy.deliveryapplicaiton.R
import ows.kotlinstudy.deliveryapplicaiton.databinding.ActivityMainBinding
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.HomeFragment
import ows.kotlinstudy.deliveryapplicaiton.screen.main.like.RestaurantLikeListFragment
import ows.kotlinstudy.deliveryapplicaiton.screen.main.my.MyFragment
import ows.kotlinstudy.deliveryapplicaiton.util.event.MenuChangeEventBus

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    private val menuChangeEventBus by inject<MenuChangeEventBus>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeData()
        initViews()
    }

    private fun observeData() = lifecycleScope.launch {
        menuChangeEventBus.mainTabMenuFlow.collect {
            goToTab(it)
        }
    }

    private fun initViews() = with(binding) {
        bottomNav.setOnItemSelectedListener(this@MainActivity)
        showFragment(HomeFragment.newInstance(), HomeFragment.TAG)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_home -> {
                showFragment(HomeFragment.newInstance(), HomeFragment.TAG)
                true
            }
            R.id.menu_my -> {
                showFragment(MyFragment.newInstance(), MyFragment.TAG)
                true
            }
            R.id.menu_like -> {
                showFragment(
                    RestaurantLikeListFragment.newInstance(),
                    RestaurantLikeListFragment.TAG
                )
                true
            }
            else -> false
        }
    }

    // TODO : FragmentManager 역할, Activity가 종료되어도 Fragment가 유지되는 이유 Study
    private fun showFragment(fragment: Fragment, tag: String) {
        val findFragment = supportFragmentManager.findFragmentByTag(tag)

        supportFragmentManager.fragments.forEach { fm ->
            supportFragmentManager.beginTransaction().hide(fm).commitAllowingStateLoss()
        }

        findFragment?.let {
            supportFragmentManager.beginTransaction().show(it).commitAllowingStateLoss()
        } ?: kotlin.run {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, fragment, tag)
                .commitAllowingStateLoss()
        }
    }

    fun goToTab(mainTabMenu: MainTabMenu){
        binding.bottomNav.selectedItemId = mainTabMenu.menuId
    }
}

enum class MainTabMenu(@IdRes val menuId: Int){
    HOME(R.id.menu_home), LIKE(R.id.menu_like), MY(R.id.menu_my)
}