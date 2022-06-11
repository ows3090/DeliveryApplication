package ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant

import androidx.annotation.StringRes
import ows.kotlinstudy.deliveryapplicaiton.R

/**
 * Enum class는 Serializable 기본 내장되어 있음.
 */
enum class RestaurantCategory(
    @StringRes val categoryNameId: Int,
    @StringRes val categoryTypeId: Int
) {
    ALL(R.string.all, R.string.all_type)
}