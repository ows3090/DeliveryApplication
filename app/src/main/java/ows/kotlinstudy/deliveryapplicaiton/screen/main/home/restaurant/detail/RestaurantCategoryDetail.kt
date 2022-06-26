package ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.detail

import androidx.annotation.StringRes
import ows.kotlinstudy.deliveryapplicaiton.R

enum class RestaurantCategoryDetail(
    @StringRes val categoryNameId: Int
){
    MENU(R.string.menu), REVIEW(R.string.review),
}