package ows.kotlinstudy.deliveryapplicaiton.screen.mylocation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ows.kotlinstudy.deliveryapplicaiton.R
import ows.kotlinstudy.deliveryapplicaiton.data.entity.LocationLatLngEntity
import ows.kotlinstudy.deliveryapplicaiton.data.entity.MapSearchInfoEntity
import ows.kotlinstudy.deliveryapplicaiton.data.repository.map.MapRepository
import ows.kotlinstudy.deliveryapplicaiton.data.repository.user.UserRepository
import ows.kotlinstudy.deliveryapplicaiton.screen.base.BaseViewModel
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.HomeState

class MyLocationViewModel(
    private val mapSearchInfoEntity: MapSearchInfoEntity,
    private val mapRepository: MapRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {
    val myLocationStateLiveData = MutableLiveData<MyLocationState>(MyLocationState.Uninitialized)

    override fun fetchData(): Job = viewModelScope.launch {
        myLocationStateLiveData.value = MyLocationState.Loading
        myLocationStateLiveData.value = MyLocationState.Success(mapSearchInfoEntity)
    }

    fun changeLocationInfo(
        locationLatLngEntity: LocationLatLngEntity
    ) = viewModelScope.launch {
        val addressInfo = mapRepository.getReverseGeoInformation(locationLatLngEntity)
        addressInfo?.let { info ->
            myLocationStateLiveData.value = MyLocationState.Success(
                mapSearchInfoEntity = info.toSearchInfoEntity(locationLatLngEntity)
            )
        } ?: kotlin.run {
            myLocationStateLiveData.value = MyLocationState.Error(
                R.string.can_not_load_address_info
            )
        }
    }

    fun confirmSelectLocation() = viewModelScope.launch {
        when (val data = myLocationStateLiveData.value) {
            is MyLocationState.Success -> {
                userRepository.insertUserLocation(data.mapSearchInfoEntity.locationLatLng)
                myLocationStateLiveData.value = MyLocationState.Confirm(
                    data.mapSearchInfoEntity
                )
            }
        }
    }
}