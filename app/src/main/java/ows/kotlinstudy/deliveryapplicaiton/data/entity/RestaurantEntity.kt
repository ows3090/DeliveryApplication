package ows.kotlinstudy.deliveryapplicaiton.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.RestaurantCategory

@Parcelize
data class RestaurantEntity(
    override val id: Long,
    val restaurantInfoId: Long,
    val restaurantCategory: RestaurantCategory,
    val restaurantTitle: String,
    val restaurantImageUrl: String,
    val grade: Float,
    val reviewCount: Int,
    val deliveryTimeRange: Pair<Int, Int>,
    val deliveryTipRange: Pair<Int,Int>
) : Entity, Parcelable