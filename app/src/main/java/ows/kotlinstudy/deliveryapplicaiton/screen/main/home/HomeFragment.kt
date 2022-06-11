package ows.kotlinstudy.deliveryapplicaiton.screen.main.home

import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.viewmodel.ext.android.viewModel
import ows.kotlinstudy.deliveryapplicaiton.databinding.FragmentHomeBinding
import ows.kotlinstudy.deliveryapplicaiton.screen.base.BaseFragment
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.RestaurantCategory
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.RestaurantListFragment
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.RestaurantListFragmentPagerAdapater

/**
 * android tint vs app tint => app : AppCompat 라이브러리에 포함, 즉 API 별 호환성으로 인해 app tint로 설정해야 함.
 */
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    override val viewModel by viewModel<HomeViewModel>()

    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    private lateinit var viewPagerAdapter: RestaurantListFragmentPagerAdapater

    override fun initViews() {
        super.initViews()
        initViewPager()
    }

    /**
     * TODO viewPager.offscreenPageLimit 조사, TabLayoutMediator 조사
     */
    private fun initViewPager() = with(binding) {
        val restaurantCategories = RestaurantCategory.values()

        if (::viewPagerAdapter.isInitialized.not()) {
            val restaurantListFragmentList = restaurantCategories.map {
                RestaurantListFragment.newInstance(it)
            }

            viewPagerAdapter = RestaurantListFragmentPagerAdapater(
                this@HomeFragment,
                restaurantListFragmentList
            )
            viewPager.adapter = viewPagerAdapter
        }
        viewPager.offscreenPageLimit = restaurantCategories.size
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setText(restaurantCategories[position].categoryNameId)
        }.attach()
    }

    override fun observeData() {

    }

    companion object {
        fun newInstance() = HomeFragment()

        const val TAG = "HomeFragment"
    }

}