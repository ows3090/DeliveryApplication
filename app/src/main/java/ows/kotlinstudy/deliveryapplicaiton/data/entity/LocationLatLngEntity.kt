package ows.kotlinstudy.deliveryapplicaiton.data.entity

import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@androidx.room.Entity
@Parcelize
data class LocationLatLngEntity(
    val latitude: Double,
    val longitude: Double,
    @PrimaryKey(autoGenerate = true) override val id: Long = -1
) : Entity, Parcelable
