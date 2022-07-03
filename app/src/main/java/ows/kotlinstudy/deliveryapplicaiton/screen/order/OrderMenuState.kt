package ows.kotlinstudy.deliveryapplicaiton.screen.order

import androidx.annotation.StringRes
import ows.kotlinstudy.deliveryapplicaiton.model.restaurant.food.FoodModel

sealed class OrderMenuState{

    object Uninitialized: OrderMenuState()

    object Loading: OrderMenuState()

    data class Success(
        val restaurantFoodModelList: List<FoodModel>? = null
    ): OrderMenuState()

    object Order: OrderMenuState()

    data class Error(
        @StringRes val messageId: Int,
        val e: Throwable
    ): OrderMenuState()
}
