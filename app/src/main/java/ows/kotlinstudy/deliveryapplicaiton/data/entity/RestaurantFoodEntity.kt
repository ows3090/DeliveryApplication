package ows.kotlinstudy.deliveryapplicaiton.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RestaurantFoodEntity(
    val id: String,
    val title: String,
    val description: String,
    val prices: Int,
    val imageUrl: String,
    val restaurantId: Long
): Parcelable
