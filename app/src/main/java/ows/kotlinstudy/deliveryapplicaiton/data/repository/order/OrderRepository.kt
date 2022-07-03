package ows.kotlinstudy.deliveryapplicaiton.data.repository.order

import ows.kotlinstudy.deliveryapplicaiton.data.entity.RestaurantFoodEntity

interface OrderRepository {

    suspend fun orderMenu(
        userId: String,
        restaurantId: Long,
        foodMenuList: List<RestaurantFoodEntity>
    ): DefaultOrderRepository.Result
}