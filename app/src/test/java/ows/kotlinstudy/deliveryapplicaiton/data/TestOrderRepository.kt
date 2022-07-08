package ows.kotlinstudy.deliveryapplicaiton.data

import ows.kotlinstudy.deliveryapplicaiton.data.entity.OrderEntity
import ows.kotlinstudy.deliveryapplicaiton.data.entity.RestaurantFoodEntity
import ows.kotlinstudy.deliveryapplicaiton.data.repository.order.DefaultOrderRepository
import ows.kotlinstudy.deliveryapplicaiton.data.repository.order.OrderRepository

class TestOrderRepository : OrderRepository {

    private var orderEntities = mutableListOf<OrderEntity>()

    override suspend fun orderMenu(
        userId: String,
        restaurantId: Long,
        foodMenuList: List<RestaurantFoodEntity>,
        restaurantTitle: String
    ): DefaultOrderRepository.Result {
        orderEntities.add(
            OrderEntity(
                id = orderEntities.size.toString(),
                userId = userId,
                foodMenuList = foodMenuList.map { it.copy() },
                restaurantId = restaurantId,
                restaurantTitle = restaurantTitle
            )
        )
        return DefaultOrderRepository.Result.Success<Any>()
    }

    override suspend fun getAllOrderMenus(userId: String): DefaultOrderRepository.Result {
        return DefaultOrderRepository.Result.Success<Any>(orderEntities)
    }
}