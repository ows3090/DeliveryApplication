package ows.kotlinstudy.deliveryapplicaiton.widget.adapter.listener.restaurant

import ows.kotlinstudy.deliveryapplicaiton.model.restaurant.food.FoodModel
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.listener.AdapterListener

interface FoodMenuListListener : AdapterListener {
    fun onClickItem(model: FoodModel)
}