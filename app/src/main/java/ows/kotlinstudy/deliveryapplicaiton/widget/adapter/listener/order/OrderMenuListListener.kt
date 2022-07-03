package ows.kotlinstudy.deliveryapplicaiton.widget.adapter.listener.order

import ows.kotlinstudy.deliveryapplicaiton.model.restaurant.food.FoodModel
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.listener.AdapterListener

interface OrderMenuListListener : AdapterListener {
    fun onRemoveItem(model: FoodModel)
}