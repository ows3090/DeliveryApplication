package ows.kotlinstudy.deliveryapplicaiton.screen.main.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ows.kotlinstudy.deliveryapplicaiton.R
import ows.kotlinstudy.deliveryapplicaiton.data.entity.LocationLatLngEntity
import ows.kotlinstudy.deliveryapplicaiton.data.entity.MapSearchInfoEntity
import ows.kotlinstudy.deliveryapplicaiton.databinding.FragmentHomeBinding
import ows.kotlinstudy.deliveryapplicaiton.screen.base.BaseFragment
import ows.kotlinstudy.deliveryapplicaiton.screen.main.MainActivity
import ows.kotlinstudy.deliveryapplicaiton.screen.main.MainTabMenu
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.RestaurantCategory
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.RestaurantListFragment
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.RestaurantOrder
import ows.kotlinstudy.deliveryapplicaiton.screen.mylocation.MyLocationActivity
import ows.kotlinstudy.deliveryapplicaiton.screen.order.OrderMenuListActivity
import ows.kotlinstudy.deliveryapplicaiton.util.event.MenuChangeEventBus
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.RestaurantListFragmentPagerAdapater

/**
 * TODO android tint vs app tint => app : AppCompat 라이브러리에 포함, 즉 API 별 호환성으로 인해 app tint로 설정해야 함.
 * TODO style, theme, attr 차이 공부
 *
 */
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    override val viewModel by viewModel<HomeViewModel>()

    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    private lateinit var viewPagerAdapter: RestaurantListFragmentPagerAdapater

    private lateinit var locationManager: LocationManager

    private lateinit var myLocationListener: MyLocationListener

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val menuChangeEventBus by inject<MenuChangeEventBus>()

    /**
     * registerForActivityResult : startActivityForResult deprecated 되면서 대체 방법
     * startActivityForResult 호출하면서 콜백도 지정 -> 카메라와 같은 메모리를 많이 차지하는 Activity 실행 시 종료로 인해 결과 반환받지 못하는 이슈 발생
     * registerForActivityResult를 통해 Activity/Fragment 생성 시마다 콜백 재지정
     */
    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val responsePermissions = permissions.entries.filter {
                (it.key == Manifest.permission.ACCESS_FINE_LOCATION)
                        || (it.key == Manifest.permission.ACCESS_COARSE_LOCATION)
            }

            if (responsePermissions.filter { it.value }.size == locationPermissions.size) {
                setMyLocationListener()
            } else {
                with(binding.locationTitleText) {
                    setText(R.string.please_setup_your_location_permission)
                    setOnClickListener {
                        getMyLocation()
                    }
                }

                Toast.makeText(
                    requireContext(),
                    getString(R.string.can_not_assigned_permission),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private val changeLocationLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.getParcelableExtra<MapSearchInfoEntity>(HomeViewModel.MY_LOCATION_KEY)
                    ?.let { myLocationInfo ->
                        viewModel.loadReverseGeoInformation(myLocationInfo.locationLatLng)
                    }
            }
        }

    override fun initViews() = with(binding) {
        locationTitleText.setOnClickListener {
            viewModel.getMapSearchInfo()?.let { mapInfo ->
                changeLocationLauncher.launch(
                    MyLocationActivity.newIntent(requireContext(), mapInfo)
                )
            }
        }

        orderChipGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.chipDefault -> {
                    chipInitialize.isGone = true
                    changeRestaurantOrder(RestaurantOrder.DEFAULT)
                }
                R.id.chipInitialize -> {
                    chipDefault.isChecked = true
                }
                R.id.chipLowDeliveryTip -> {
                    chipInitialize.isVisible = true
                    changeRestaurantOrder(RestaurantOrder.LOW_DELIVERY_TIP)
                }
                R.id.chipFastDelivery -> {
                    chipInitialize.isVisible = true
                    changeRestaurantOrder(RestaurantOrder.FAST_DELIVERY)
                }
                R.id.chipTopRate -> {
                    chipInitialize.isVisible = true
                    changeRestaurantOrder(RestaurantOrder.TOP_RATE)
                }
            }
        }
    }

    private fun changeRestaurantOrder(order: RestaurantOrder) {
        viewPagerAdapter.fragmentList.forEach {
            it.viewModel.setRestaurantOrder(order)
        }
    }

    /**
     * TODO viewPager.offscreenPageLimit 조사, TabLayoutMediator 조사
     */
    private fun initViewPager(locationLatLngEntity: LocationLatLngEntity) = with(binding) {
        val restaurantCategories = RestaurantCategory.values()

        if (::viewPagerAdapter.isInitialized.not()) {
            orderChipGroup.isVisible = true
            val restaurantListFragmentList = restaurantCategories.map {
                RestaurantListFragment.newInstance(it, locationLatLngEntity)
            }

            viewPagerAdapter = RestaurantListFragmentPagerAdapater(
                this@HomeFragment,
                restaurantListFragmentList,
                locationLatLngEntity
            )
            viewPager.adapter = viewPagerAdapter
            viewPager.offscreenPageLimit = restaurantCategories.size
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.setText(restaurantCategories[position].categoryNameId)
            }.attach()
        }

        if (locationLatLngEntity != viewPagerAdapter.locationLatLngEntity) {
            viewPagerAdapter.locationLatLngEntity = locationLatLngEntity
            viewPagerAdapter.fragmentList.forEach {
                it.viewModel.setLocationLatLng(locationLatLngEntity)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkMyBasket()
    }

    override fun observeData() {
        viewModel.homeStaetLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is HomeState.Uninitialized -> {
                    getMyLocation()
                }
                is HomeState.Loading -> {
                    binding.locationLoading.isVisible = true
                    binding.locationTitleText.text = getString(R.string.loading)
                }
                is HomeState.Success -> {
                    binding.locationLoading.isGone = true
                    binding.locationTitleText.text = it.mapSearchInfo.fullAddress
                    binding.tabLayout.isVisible = true
                    binding.filterScrollView.isVisible = true
                    binding.viewPager.isVisible = true
                    initViewPager(it.mapSearchInfo.locationLatLng)

                    if (it.isLocationSame.not()) {
                        Toast.makeText(
                            requireContext(),
                            R.string.please_set_your_current_location,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                is HomeState.Error -> {
                    binding.locationLoading.isGone = true
                    binding.locationTitleText.setText(R.string.location_not_found)
                    binding.locationTitleText.setOnClickListener {
                        getMyLocation()
                    }
                    Toast.makeText(requireContext(), it.meesageId, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.foodMenuBasketLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.basketButtonContainer.isVisible = true
                binding.basketCountTextView.text = getString(R.string.basket_count, it.size)
                binding.basketButton.setOnClickListener {
                    if (firebaseAuth.currentUser == null) {
                        alertLoginNeed {
                            lifecycleScope.launch {
                                menuChangeEventBus.changeMenu(MainTabMenu.MY)
                            }
                        }
                    } else {
                        startActivity(
                            OrderMenuListActivity.newIntent(requireContext())
                        )
                    }
                }
            } else {
                binding.basketButtonContainer.isGone = true
                binding.basketButton.setOnClickListener(null)
            }
        }
    }

    private fun alertLoginNeed(afterAction: () -> Unit) {
        AlertDialog.Builder(requireContext())
            .setTitle("로그인이 필요합니다.")
            .setMessage("주문하려면 로그이 필요합니다. My탭으로 이동하시겠습니까?")
            .setPositiveButton("이동") { dialog, _ ->
                afterAction()
                dialog.dismiss()
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun getMyLocation() {
        if (::locationManager.isInitialized.not()) {
            locationManager =
                requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }

        val isGpsUnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (isGpsUnabled) {
            locationPermissionLauncher.launch(locationPermissions)
        }
    }

    /**
     * TODO : Location 위치 정보 불러오기 로직 확인
     */
    @SuppressLint("MissingPermission")
    private fun setMyLocationListener() {
        val minTime = 1500L
        val minDistance = 100f

        if (::myLocationListener.isInitialized.not()) {
            myLocationListener = MyLocationListener()
        }
        with(locationManager) {
            requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime,
                minDistance,
                myLocationListener
            )

            requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                minTime,
                minDistance,
                myLocationListener
            )
        }
    }

    private fun removeLocationListener() {
        if (::locationManager.isInitialized && ::myLocationListener.isInitialized) {
            locationManager.removeUpdates(myLocationListener)
        }
    }

    inner class MyLocationListener : LocationListener {
        override fun onLocationChanged(location: Location) {
            viewModel.loadReverseGeoInformation(
                LocationLatLngEntity(
                    location.latitude,
                    location.longitude
                )
            )
            removeLocationListener()
        }
    }

    companion object {

        val locationPermissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        fun newInstance() = HomeFragment()

        const val TAG = "HomeFragment"
    }

}