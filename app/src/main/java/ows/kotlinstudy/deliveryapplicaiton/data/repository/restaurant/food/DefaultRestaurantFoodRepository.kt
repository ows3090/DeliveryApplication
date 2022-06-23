package ows.kotlinstudy.deliveryapplicaiton.data.repository.restaurant.food

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ows.kotlinstudy.deliveryapplicaiton.data.entity.RestaurantFoodEntity
import ows.kotlinstudy.deliveryapplicaiton.data.network.FoodApiService
import ows.kotlinstudy.deliveryapplicaiton.data.response.restaurant.RestaurantFoodResponse

class DefaultRestaurantFoodRepository(
    private val foodApiService: FoodApiService,
    private val ioDispatcher: CoroutineDispatcher
): RestaurantFoodRepository {

    override suspend fun getFoods(restaurantId: Long): List<RestaurantFoodEntity> = withContext(ioDispatcher){
        val response = foodApiService.getRestaurantFoods(restaurantId)
        if(response.isSuccessful){
            response.body()?.map { it.toEntity(restaurantId) } ?: listOf()
        }else{
            listOf()
        }
    }
}