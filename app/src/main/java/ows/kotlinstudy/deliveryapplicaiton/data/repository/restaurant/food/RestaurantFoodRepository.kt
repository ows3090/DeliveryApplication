package ows.kotlinstudy.deliveryapplicaiton.data.repository.restaurant.food

import ows.kotlinstudy.deliveryapplicaiton.data.entity.RestaurantFoodEntity

interface RestaurantFoodRepository {

    suspend fun getFoods(restaurantId: Long): List<RestaurantFoodEntity>
}