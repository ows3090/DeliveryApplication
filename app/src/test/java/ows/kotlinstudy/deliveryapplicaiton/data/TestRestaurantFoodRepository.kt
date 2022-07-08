package ows.kotlinstudy.deliveryapplicaiton.data

import ows.kotlinstudy.deliveryapplicaiton.data.entity.RestaurantFoodEntity
import ows.kotlinstudy.deliveryapplicaiton.data.repository.restaurant.food.RestaurantFoodRepository

class TestRestaurantFoodRepository: RestaurantFoodRepository {

    private val foodMenuListInBasket = mutableListOf<RestaurantFoodEntity>()

    override suspend fun getFoods(
        restaurantId: Long,
        restaurantTitle: String
    ): List<RestaurantFoodEntity> {
        return foodMenuListInBasket
    }

    override suspend fun getAllFoodMenuListInBasket(): List<RestaurantFoodEntity> {
        return foodMenuListInBasket
    }

    override suspend fun getFoodMenuListInBasket(restaurantId: Long): List<RestaurantFoodEntity> {
        return foodMenuListInBasket
    }

    override suspend fun insertFoodMenuInBasket(restaurantFoodEntity: RestaurantFoodEntity) {
        foodMenuListInBasket.add(restaurantFoodEntity)
    }

    override suspend fun removeFoodMenuListInBasket(foodId: String) {

    }

    override suspend fun clearFoodMenuListInBasket() {
        foodMenuListInBasket.clear()
    }
}