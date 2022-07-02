package ows.kotlinstudy.deliveryapplicaiton.data.repository.restaurant.food

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ows.kotlinstudy.deliveryapplicaiton.data.db.dao.FoodMenuBasketDao
import ows.kotlinstudy.deliveryapplicaiton.data.entity.RestaurantFoodEntity
import ows.kotlinstudy.deliveryapplicaiton.data.network.FoodApiService
import ows.kotlinstudy.deliveryapplicaiton.data.response.restaurant.RestaurantFoodResponse

class DefaultRestaurantFoodRepository(
    private val foodApiService: FoodApiService,
    private val foodMenuBasketDao: FoodMenuBasketDao,
    private val ioDispatcher: CoroutineDispatcher
) : RestaurantFoodRepository {

    override suspend fun getFoods(restaurantId: Long): List<RestaurantFoodEntity> =
        withContext(ioDispatcher) {
            val response = foodApiService.getRestaurantFoods(restaurantId)
            if (response.isSuccessful) {
                response.body()?.map { it.toEntity(restaurantId) } ?: listOf()
            } else {
                listOf()
            }
        }

    override suspend fun getAllFoodMenuListInBasket(): List<RestaurantFoodEntity> =
        withContext(ioDispatcher) {
            foodMenuBasketDao.getAll()
        }

    override suspend fun getFoodMenuListInBasket(restaurantId: Long): List<RestaurantFoodEntity> =
        withContext(ioDispatcher) {
            foodMenuBasketDao.getAllByRestaurantId(restaurantId)
        }

    override suspend fun insertFoodMenuInBasket(restaurantFoodEntity: RestaurantFoodEntity) =
        withContext(ioDispatcher) {
            foodMenuBasketDao.insert(restaurantFoodEntity)
        }

    override suspend fun removeFoodMenuListInBasket(foodId: String) = withContext(ioDispatcher) {
        foodMenuBasketDao.delete(foodId)
    }

    override suspend fun clearFoodMenuListInBasket() = withContext(ioDispatcher) {
        foodMenuBasketDao.deleteAll()
    }
}