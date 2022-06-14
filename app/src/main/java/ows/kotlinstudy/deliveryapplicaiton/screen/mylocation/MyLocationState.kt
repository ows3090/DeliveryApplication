package ows.kotlinstudy.deliveryapplicaiton.screen.mylocation

import androidx.annotation.StringRes
import ows.kotlinstudy.deliveryapplicaiton.data.entity.MapSearchInfoEntity

sealed class MyLocationState {

    object Uninitialized : MyLocationState()

    object Loading : MyLocationState()

    data class Success(
        val mapSearchInfoEntity: MapSearchInfoEntity
    ) : MyLocationState()

    data class Confirm(
        val mapSearchInfoEntity: MapSearchInfoEntity
    ) : MyLocationState()

    data class Error(
        @StringRes val messageId: Int
    ) : MyLocationState()
}