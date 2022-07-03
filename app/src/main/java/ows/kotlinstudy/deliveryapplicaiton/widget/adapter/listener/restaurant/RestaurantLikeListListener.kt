package ows.kotlinstudy.deliveryapplicaiton.widget.adapter.listener.restaurant

import ows.kotlinstudy.deliveryapplicaiton.model.restaurant.RestaurantModel

interface RestaurantLikeListListener: RestaurantListListener {
    fun onDislikeItem(model: RestaurantModel)
}