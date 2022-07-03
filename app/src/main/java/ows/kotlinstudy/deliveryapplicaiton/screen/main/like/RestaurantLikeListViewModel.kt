package ows.kotlinstudy.deliveryapplicaiton.screen.main.like

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ows.kotlinstudy.deliveryapplicaiton.data.entity.RestaurantEntity
import ows.kotlinstudy.deliveryapplicaiton.data.repository.user.UserRepository
import ows.kotlinstudy.deliveryapplicaiton.model.CellType
import ows.kotlinstudy.deliveryapplicaiton.model.restaurant.RestaurantModel
import ows.kotlinstudy.deliveryapplicaiton.screen.base.BaseViewModel

class RestaurantLikeListViewModel(
    private val userRepository: UserRepository
) : BaseViewModel() {

    val restaurantListLiveData = MutableLiveData<List<RestaurantModel>>()

    override fun fetchData(): Job = viewModelScope.launch {
        restaurantListLiveData.value = userRepository.getAllUserLikedRestaurantList().map {
            RestaurantModel(
                it.id,
                type = CellType.LIKE_RESTAURANT_CELL,
                restaurantInfoId = it.restaurantInfoId,
                restaurantCategory = it.restaurantCategory,
                restaurantTitle = it.restaurantTitle,
                restaurantImageUrl = it.restaurantImageUrl,
                grade = it.grade,
                reviewCount = it.reviewCount,
                deliveryTimeRange = it.deliveryTimeRange,
                deliveryTipRange = it.deliveryTipRange,
                restaurantTelNumber = it.restaurantTelNumber
            )
        }
    }

    fun dislikeRestaurant(restaurantEntity: RestaurantEntity) = viewModelScope.launch{
        userRepository.deletedUserLikedRestaurant(restaurantEntity.restaurantTitle)
        fetchData()
    }
}