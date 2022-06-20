package ows.kotlinstudy.deliveryapplicaiton.data.entity

import android.os.Parcelable
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.RestaurantCategory
import ows.kotlinstudy.deliveryapplicaiton.util.convertor.RoomTypeConverters

@Parcelize
@androidx.room.Entity
@TypeConverters(RoomTypeConverters::class)
data class RestaurantEntity(
    override val id: Long,
    val restaurantInfoId: Long,
    val restaurantCategory: RestaurantCategory,
    @PrimaryKey val restaurantTitle: String,
    val restaurantImageUrl: String,
    val grade: Float,
    val reviewCount: Int,
    val deliveryTimeRange: Pair<Int, Int>,
    val deliveryTipRange: Pair<Int, Int>,
    val restaurantTelNumber: String?
) : Entity, Parcelable