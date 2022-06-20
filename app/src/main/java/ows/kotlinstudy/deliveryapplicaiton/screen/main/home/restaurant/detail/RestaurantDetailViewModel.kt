package ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ows.kotlinstudy.deliveryapplicaiton.data.entity.RestaurantEntity
import ows.kotlinstudy.deliveryapplicaiton.screen.base.BaseViewModel

class RestaurantDetailViewModel(
    private val restaurantEntity: RestaurantEntity
) : BaseViewModel() {

    val restaurantDetailStateLiveData =
        MutableLiveData<RestaurantDetailState>(RestaurantDetailState.Uninitialized)

    override fun fecthData(): Job = viewModelScope.launch {
        restaurantDetailStateLiveData.value = RestaurantDetailState.Success(
            restaurantEntity = restaurantEntity
        )
    }

    fun getRestaurantTelNumber(): String? {
        return when (val data = restaurantDetailStateLiveData.value) {
            is RestaurantDetailState.Success -> {
                data.restaurantEntity.restaurantTelNumber
            }
            else -> null
        }
    }

}