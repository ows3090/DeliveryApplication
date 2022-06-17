package ows.kotlinstudy.deliveryapplicaiton.data.repository.user

import ows.kotlinstudy.deliveryapplicaiton.data.entity.LocationLatLngEntity

interface UserRepository {

    suspend fun getUserLocation(): LocationLatLngEntity?

    suspend fun insertUserLocation(locationLatLngEntity: LocationLatLngEntity)
}