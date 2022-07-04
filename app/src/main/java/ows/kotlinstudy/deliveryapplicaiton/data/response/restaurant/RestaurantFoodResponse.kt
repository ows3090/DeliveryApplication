package ows.kotlinstudy.deliveryapplicaiton.data.response.restaurant

import ows.kotlinstudy.deliveryapplicaiton.data.entity.RestaurantFoodEntity

data class RestaurantFoodResponse(
    val id: String,
    val title: String,
    val description: String,
    val prices: String?,
    val imageUrl: String
) {

    fun toEntity(restaurantId: Long, restaurantTitle: String) = RestaurantFoodEntity(
        id,
        title,
        description,
        prices?.toDouble()?.toInt() ?: 0,
        imageUrl,
        restaurantId,
        restaurantTitle
    )
}
