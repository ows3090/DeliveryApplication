package ows.kotlinstudy.deliveryapplicaiton.widget.adapter.listener.restaurant

import ows.kotlinstudy.deliveryapplicaiton.model.restaurant.RestaurantModel
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.listener.AdapterListener

interface RestaurantListListener : AdapterListener {
    fun onClickItem(model: RestaurantModel)
}