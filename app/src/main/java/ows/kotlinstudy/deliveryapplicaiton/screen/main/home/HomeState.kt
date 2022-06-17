package ows.kotlinstudy.deliveryapplicaiton.screen.main.home

import androidx.annotation.StringRes
import ows.kotlinstudy.deliveryapplicaiton.data.entity.MapSearchInfoEntity

sealed class HomeState {
    object Uninitialized : HomeState()

    object Loading : HomeState()

    data class Success(
        val mapSearchInfo: MapSearchInfoEntity,
        val isLocationSame: Boolean
    ) : HomeState()

    data class Error(
        @StringRes val meesageId: Int
    ) : HomeState()
}
