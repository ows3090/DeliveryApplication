package ows.kotlinstudy.deliveryapplicaiton.screen.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ows.kotlinstudy.deliveryapplicaiton.R
import ows.kotlinstudy.deliveryapplicaiton.data.entity.LocationLatLngEntity
import ows.kotlinstudy.deliveryapplicaiton.data.entity.MapSearchInfoEntity
import ows.kotlinstudy.deliveryapplicaiton.data.entity.RestaurantFoodEntity
import ows.kotlinstudy.deliveryapplicaiton.data.repository.map.MapRepository
import ows.kotlinstudy.deliveryapplicaiton.data.repository.restaurant.food.RestaurantFoodRepository
import ows.kotlinstudy.deliveryapplicaiton.data.repository.user.DefaultUserRepository
import ows.kotlinstudy.deliveryapplicaiton.data.repository.user.UserRepository
import ows.kotlinstudy.deliveryapplicaiton.screen.base.BaseViewModel

class HomeViewModel(
    private val mapRepository: MapRepository,
    private val userRepository: UserRepository,
    private val restaurantFoodRepository: RestaurantFoodRepository
) : BaseViewModel() {

    val homeStaetLiveData = MutableLiveData<HomeState>(HomeState.Uninitialized)

    val foodMenuBasketLiveData = MutableLiveData<List<RestaurantFoodEntity>>()

    fun loadReverseGeoInformation(
        locationLatLngEntity: LocationLatLngEntity
    ) = viewModelScope.launch {
        homeStaetLiveData.value = HomeState.Loading

        val userLocation = userRepository.getUserLocation()
        val currentLocation = userLocation ?: locationLatLngEntity

        val addressInfo = mapRepository.getReverseGeoInformation(currentLocation)
        addressInfo?.let { info ->
            homeStaetLiveData.value = HomeState.Success(
                info.toSearchInfoEntity(locationLatLngEntity),
                isLocationSame = currentLocation == locationLatLngEntity
            )
        } ?: kotlin.run {
            homeStaetLiveData.value = HomeState.Error(
                R.string.can_not_load_address_info
            )
        }
    }

    fun getMapSearchInfo(): MapSearchInfoEntity? {
        when (val data = homeStaetLiveData.value) {
            is HomeState.Success -> {
                return data.mapSearchInfo
            }
        }
        return null
    }

    fun checkMyBasket() = viewModelScope.launch{
        foodMenuBasketLiveData.value = restaurantFoodRepository.getAllFoodMenuListInBasket()
    }

    companion object {
        const val MY_LOCATION_KEY = "MyLocation"
    }
}