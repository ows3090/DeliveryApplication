package ows.kotlinstudy.deliveryapplicaiton.data.repository

import ows.kotlinstudy.deliveryapplicaiton.data.entity.RestaurantEntity
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.RestaurantCategory

interface RestaurantRepository{

    suspend fun getList(
        restaurantCategory: RestaurantCategory
    ): List<RestaurantEntity>

}