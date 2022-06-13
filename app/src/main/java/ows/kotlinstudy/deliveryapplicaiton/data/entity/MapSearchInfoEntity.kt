package ows.kotlinstudy.deliveryapplicaiton.data.entity

data class MapSearchInfoEntity(
    val fullAddress: String,
    val name: String,
    val locationLatLng: LocationLatLngEntity
)
