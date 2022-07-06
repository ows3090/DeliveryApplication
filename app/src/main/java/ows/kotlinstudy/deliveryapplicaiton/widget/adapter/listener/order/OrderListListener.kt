package ows.kotlinstudy.deliveryapplicaiton.widget.adapter.listener.order

import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.listener.AdapterListener

interface OrderListListener: AdapterListener {

    fun writeRestaurantReview(orderId: String, restaurantTitle: String)
}