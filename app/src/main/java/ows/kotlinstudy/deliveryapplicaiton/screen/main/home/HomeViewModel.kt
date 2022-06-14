package ows.kotlinstudy.deliveryapplicaiton.screen.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ows.kotlinstudy.deliveryapplicaiton.R
import ows.kotlinstudy.deliveryapplicaiton.data.entity.LocationLatLngEntity
import ows.kotlinstudy.deliveryapplicaiton.data.entity.MapSearchInfoEntity
import ows.kotlinstudy.deliveryapplicaiton.data.repository.map.MapRepository
import ows.kotlinstudy.deliveryapplicaiton.screen.base.BaseViewModel

class HomeViewModel(
    private val mapRepository: MapRepository
) : BaseViewModel() {

    val homeStaetLiveData = MutableLiveData<HomeState>(HomeState.Uninitialized)

    fun loadReverseGeoInformation(
        locationLatLngEntity: LocationLatLngEntity
    ) = viewModelScope.launch {
        homeStaetLiveData.value = HomeState.Loading
        val addressInfo = mapRepository.getReverseGeoInformation(locationLatLngEntity)
        addressInfo?.let { info ->
            homeStaetLiveData.value = HomeState.Success(
                info.toSearchInfoEntity(locationLatLngEntity)
            )
        } ?: kotlin.run {
            homeStaetLiveData.value = HomeState.Error(
                R.string.can_not_load_address_info
            )
        }
    }

    fun getMapSearchInfo(): MapSearchInfoEntity? {
        when(val data = homeStaetLiveData.value){
            is HomeState.Success -> {
                return data.mapSearchInfo
            }
        }
        return null
    }

    companion object{
        const val MY_LOCATION_KEY = "MyLocation"
    }
}