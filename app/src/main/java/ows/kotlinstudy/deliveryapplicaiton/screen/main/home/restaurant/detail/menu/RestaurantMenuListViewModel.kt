package ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.detail.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ows.kotlinstudy.deliveryapplicaiton.data.entity.RestaurantFoodEntity
import ows.kotlinstudy.deliveryapplicaiton.data.repository.restaurant.food.RestaurantFoodRepository
import ows.kotlinstudy.deliveryapplicaiton.model.restaurant.food.FoodModel
import ows.kotlinstudy.deliveryapplicaiton.screen.base.BaseViewModel

class RestaurantMenuListViewModel(
    private val restaurantId: Long,
    private val foodEntityList: List<RestaurantFoodEntity>,
    private val restaurantFoodRepository: RestaurantFoodRepository
) : BaseViewModel() {

    val restaurantFoodListLiveData = MutableLiveData<List<FoodModel>>()

    val menuBasketLiveData = MutableLiveData<RestaurantFoodEntity>()

    val isClearNeedInBasketLiveData = MutableLiveData<Pair<Boolean, () -> Unit>>()

    override fun fecthData(): Job = viewModelScope.launch {
        restaurantFoodListLiveData.value = foodEntityList.map {
            FoodModel(
                id = it.hashCode().toLong(),
                title = it.title,
                description = it.description,
                prices = it.prices,
                imageUrl = it.imageUrl,
                restaurantId = restaurantId,
                foodId = it.id
            )
        }
    }

    fun insertMenuInBasket(foodModel: FoodModel) = viewModelScope.launch {
        val restaurantMenuListInBasket =
            restaurantFoodRepository.getFoodMenuListInBasket(restaurantId)
        val foodMenuEntity = foodModel.toEntity(restaurantMenuListInBasket.size)

        val anotherRestaurantMenuListInBasket =
            restaurantFoodRepository.getAllFoodMenuListInBasket()
                .filter { it.restaurantId != restaurantId }

        if(anotherRestaurantMenuListInBasket.isNotEmpty()){
            isClearNeedInBasketLiveData.value = Pair(true, {
                clearMenuAndInsertNewMenuInBasket(foodMenuEntity)
            })
        }else{
            restaurantFoodRepository.insertFoodMenuInBasket(foodMenuEntity)
            menuBasketLiveData.value = foodMenuEntity
        }
    }

    private fun clearMenuAndInsertNewMenuInBasket(foodMenuEntity:  RestaurantFoodEntity) = viewModelScope.launch {
        restaurantFoodRepository.clearFoodMenuListInBasket()
        restaurantFoodRepository.insertFoodMenuInBasket(foodMenuEntity)
        menuBasketLiveData.value = foodMenuEntity
    }
}