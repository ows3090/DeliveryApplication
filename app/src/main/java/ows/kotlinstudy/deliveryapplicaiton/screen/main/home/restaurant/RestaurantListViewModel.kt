package ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ows.kotlinstudy.deliveryapplicaiton.data.repository.RestaurantRepository
import ows.kotlinstudy.deliveryapplicaiton.model.restaurant.RestaurantModel
import ows.kotlinstudy.deliveryapplicaiton.screen.base.BaseViewModel

class RestaurantListViewModel(
    private val restaurantCategory: RestaurantCategory,
    private val restaurantRepository: RestaurantRepository
) : BaseViewModel() {

    val restaurantListLiveData = MutableLiveData<List<RestaurantModel>>()

    override fun fecthData(): Job = viewModelScope.launch {
        val restaurantList = restaurantRepository.getList(restaurantCategory)
        restaurantListLiveData.value = restaurantList.map {
            RestaurantModel(
                it.id,
                restaurantInfoId = it.restaurantInfoId,
                restaurantCategory = it.restaurantCategory,
                restaurantTitle = it.restaurantTitle,
                restaurantImageUrl = it.restaurantImageUrl,
                grade = it.grade,
                reviewCount = it.reviewCount,
                deliveryTimeRange = it.deliveryTimeRange,
                deliveryTipRange = it.deliveryTipRange
            )
        }
    }
}