package ows.kotlinstudy.deliveryapplicaiton.widget.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ows.kotlinstudy.deliveryapplicaiton.data.entity.LocationLatLngEntity
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.RestaurantListFragment

/**
 * TODO : ViewPager Adapter별 특징, RecyclerView 기반 Apater과의 차이
 */
class RestaurantDetailListFragmentPagerAdapater(
    val activity: FragmentActivity,
    val fragmentList: List<Fragment>
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}