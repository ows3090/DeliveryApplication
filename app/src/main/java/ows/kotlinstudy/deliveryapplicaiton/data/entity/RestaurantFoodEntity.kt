package ows.kotlinstudy.deliveryapplicaiton.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class RestaurantFoodEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val prices: Int,
    val imageUrl: String,
    val restaurantId: Long
): Parcelable
